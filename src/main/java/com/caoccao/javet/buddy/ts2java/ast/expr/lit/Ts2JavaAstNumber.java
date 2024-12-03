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

package com.caoccao.javet.buddy.ts2java.ast.expr.lit;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstLit;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstPropName;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsLit;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaMinusFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import com.caoccao.javet.utils.StringUtils;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.DoubleConstant;
import net.bytebuddy.implementation.bytecode.constant.FloatConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstNumber
        extends BaseTs2JavaAst<Swc4jAstNumber, Ts2JavaMemoFunction>
        implements ITs2JavaMinusFlippable,
        ITs2JavaAstLit<Swc4jAstNumber, Ts2JavaMemoFunction>,
        ITs2JavaAstPropName<Swc4jAstNumber, Ts2JavaMemoFunction>,
        ITs2JavaAstTsLit<Swc4jAstNumber, Ts2JavaMemoFunction> {
    protected boolean negative;

    protected Ts2JavaAstNumber(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstNumber ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        negative = false;
    }

    public static Ts2JavaAstNumber create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstNumber ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstNumber(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        StackManipulation stackManipulation;
        if (type.represents(int.class) || type.represents(short.class) || type.represents(byte.class)) {
            stackManipulation = IntegerConstant.forValue(negative ? -ast.asInt() : ast.asInt());
        } else if (type.represents(long.class)) {
            stackManipulation = LongConstant.forValue(negative ? -ast.asLong() : ast.asLong());
        } else if (type.represents(float.class)) {
            stackManipulation = FloatConstant.forValue(negative ? -ast.asFloat() : ast.asFloat());
        } else if (type.represents(double.class)) {
            stackManipulation = DoubleConstant.forValue(negative ? -ast.asDouble() : ast.asDouble());
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Number type ${type} is not supported.",
                            SimpleMap.of("type", type.getName())));
        }
        return stackManipulation.apply(methodVisitor, context);
    }

    @Override
    public void compile() {
        if (type == null) {
            type = ast.getRaw()
                    .map(raw -> {
                        if (StringUtils.isNotEmpty(raw)) {
                            if (raw.contains(".")) {
                                return TypeDescription.ForLoadedType.of(float.class);
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
    }

    @Override
    public void flipMinus() {
        negative = !negative;
    }

    @Override
    public boolean isMinusFlippable() {
        return true;
    }

    public boolean isNegative() {
        return negative;
    }
}
