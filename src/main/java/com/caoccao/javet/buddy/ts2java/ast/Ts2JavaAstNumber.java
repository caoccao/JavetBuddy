/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.DoubleConstant;
import net.bytebuddy.implementation.bytecode.constant.FloatConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;

import java.util.Objects;

public final class Ts2JavaAstNumber implements ITs2JavaAstStackManipulation<Swc4jAstNumber> {
    private final TypeDescription valueType;

    public Ts2JavaAstNumber(TypeDescription valueType) {
        this.valueType = Objects.requireNonNull(valueType);
    }

    public TypeDescription getValueType() {
        return valueType;
    }

    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstNumber ast) {
        StackManipulation stackManipulation;
        if (valueType.represents(int.class) || valueType.represents(short.class) || valueType.represents(byte.class)) {
            stackManipulation = IntegerConstant.forValue(ast.asInt());
        } else if (valueType.represents(long.class)) {
            stackManipulation = LongConstant.forValue(ast.asLong());
        } else if (valueType.represents(float.class)) {
            stackManipulation = FloatConstant.forValue(ast.asFloat());
        } else if (valueType.represents(double.class)) {
            stackManipulation = DoubleConstant.forValue(ast.asDouble());
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Number type ${type} is not supported.",
                            SimpleMap.of("type", valueType.getName())));
        }
        functionContext.getStackManipulations().add(stackManipulation);
        return valueType;
    }
}
