/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.compiler;

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveWideningDelegate;

import java.util.Optional;

public final class JavaClassCast {
    private JavaClassCast() {
    }

    public static Optional<StackManipulation> getUpCastStackManipulation(
            TypeDescription fromType,
            TypeDescription toType) {
        if (fromType != null && toType != null
                && fromType.isPrimitive() && toType.isPrimitive()
                && !fromType.represents(void.class) && !toType.represents(void.class)) {
            StackManipulation stackManipulation = PrimitiveWideningDelegate.forPrimitive(fromType).widenTo(toType);
            if (stackManipulation.isValid() && stackManipulation != StackManipulation.Trivial.INSTANCE) {
                return Optional.of(stackManipulation);
            }
        }
        return Optional.empty();
    }

    public static TypeDescription getUpCastTypeForMathOp(TypeDescription... types) {
        final int length = types.length;
        if (length <= 1) {
            throw new Ts2JavaException("Cannot get up cast class for less than 2 classes");
        }
        for (TypeDescription type : types) {
            if (!type.isPrimitive()) {
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format(
                                "Cannot get up cast class for ${class}",
                                SimpleMap.of("class", type.getName())));
            }
        }
        TypeDescription upCaseType = types[0];
        for (int i = 1; i < length; i++) {
            TypeDescription type = types[i];
            StackManipulation stackManipulation = PrimitiveWideningDelegate.forPrimitive(upCaseType).widenTo(type);
            if (stackManipulation.isValid() && stackManipulation != StackManipulation.Trivial.INSTANCE) {
                upCaseType = type;
            }
        }
        return upCaseType;
    }
}
