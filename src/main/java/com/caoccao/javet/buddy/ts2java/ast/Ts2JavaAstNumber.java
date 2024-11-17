/*
 * Copyright (c) 2024. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import com.caoccao.javet.utils.StringUtils;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.DoubleConstant;
import net.bytebuddy.implementation.bytecode.constant.FloatConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;

public final class Ts2JavaAstNumber implements ITs2JavaAstStackManipulation<Swc4jAstNumber> {
    private TypeDescription valueType;

    public Ts2JavaAstNumber() {
        valueType = null;
    }

    private static int getMinusCount(ISwc4jAst ast) {
        switch (ast.getType()) {
            case ParenExpr:
                return getMinusCount(ast.getParent());
            case UnaryExpr:
                if (ast.as(Swc4jAstUnaryExpr.class).getOp() == Swc4jAstUnaryOp.Minus) {
                    return getMinusCount(ast.getParent()) + 1;
                }
                return 0;
            default:
                return 0;
        }
    }

    public TypeDescription getValueType() {
        return valueType;
    }

    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstNumber ast) {
        Ts2JavaAst.manipulateLineNumber(functionContext, ast);
        StackManipulation stackManipulation;
        TypeDescription type = valueType;
        if (type == null) {
            type = ast.getRaw()
                    .map(raw -> {
                        if (StringUtils.isNotEmpty(raw)) {
                            if (raw.contains(".")) {
                                return TypeDescription.ForLoadedType.of(double.class);
                            } else {
                                long value = Long.parseLong(raw);
                                if (value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE) {
                                    return TypeDescription.ForLoadedType.of(int.class);
                                } else {
                                    return TypeDescription.ForLoadedType.of(long.class);
                                }
                            }
                        } else {
                            return null;
                        }
                    })
                    .orElse(TypeDescription.ForLoadedType.of(int.class));
        }
        final boolean isNegative = getMinusCount(ast.getParent()) % 2 == 1;
        if (type.represents(int.class) || type.represents(short.class) || type.represents(byte.class)) {
            stackManipulation = IntegerConstant.forValue(isNegative ? -ast.asInt() : ast.asInt());
        } else if (type.represents(long.class)) {
            stackManipulation = LongConstant.forValue(isNegative ? -ast.asLong() : ast.asLong());
        } else if (type.represents(float.class)) {
            stackManipulation = FloatConstant.forValue(isNegative ? -ast.asFloat() : ast.asFloat());
        } else if (type.represents(double.class)) {
            stackManipulation = DoubleConstant.forValue(isNegative ? -ast.asDouble() : ast.asDouble());
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Number type ${type} is not supported.",
                            SimpleMap.of("type", type.getName())));
        }
        functionContext.getStackManipulations().add(stackManipulation);
        return type;
    }

    public Ts2JavaAstNumber setValueType(TypeDescription valueType) {
        this.valueType = valueType;
        return this;
    }
}
