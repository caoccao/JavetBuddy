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

package com.caoccao.javet.buddy.ts2java.compiler;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.Addition;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveWideningDelegate;

import java.util.Optional;

public final class JavaClassCast {
    private JavaClassCast() {
    }

    public static Addition getAddition(Class<?> clazz) {
        if (clazz == long.class) {
            return Addition.LONG;
        } else if (clazz == float.class) {
            return Addition.FLOAT;
        } else if (clazz == double.class) {
            return Addition.DOUBLE;
        }
        return Addition.INTEGER;
    }

    public static Class<?> getUpCastClassForMathOp(Class<?>... classes) {
        Class<?> clazz = int.class;
        for (Class<?> c : classes) {
            if (clazz == int.class) {
                if (c == long.class) {
                    clazz = long.class;
                } else if (c == float.class) {
                    clazz = float.class;
                } else if (c == double.class) {
                    clazz = double.class;
                }
            } else if (clazz == long.class) {
                if (c == float.class) {
                    clazz = float.class;
                } else if (c == double.class) {
                    clazz = double.class;
                }
            } else if (clazz == float.class) {
                if (c == double.class) {
                    clazz = double.class;
                }
            }
        }
        return clazz;
    }

    public static Optional<StackManipulation> getUpCastStackManipulation(Class<?> clazz, Class<?> upCastClass) {
        if (clazz.isPrimitive() && upCastClass.isPrimitive()) {
            return Optional.of(
                            PrimitiveWideningDelegate.forPrimitive(TypeDescription.ForLoadedType.of(clazz))
                                    .widenTo(TypeDescription.ForLoadedType.of(upCastClass)))
                    .filter(StackManipulation::isValid);
        }
        return Optional.empty();
    }
}
