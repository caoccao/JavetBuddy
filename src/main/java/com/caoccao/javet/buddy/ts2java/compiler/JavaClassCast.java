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

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.*;
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

    public static Division getDivision(Class<?> clazz) {
        if (clazz == long.class) {
            return Division.LONG;
        } else if (clazz == float.class) {
            return Division.FLOAT;
        } else if (clazz == double.class) {
            return Division.DOUBLE;
        }
        return Division.INTEGER;
    }

    public static Multiplication getMultiplication(Class<?> clazz) {
        if (clazz == long.class) {
            return Multiplication.LONG;
        } else if (clazz == float.class) {
            return Multiplication.FLOAT;
        } else if (clazz == double.class) {
            return Multiplication.DOUBLE;
        }
        return Multiplication.INTEGER;
    }

    public static Subtraction getSubtraction(Class<?> clazz) {
        if (clazz == long.class) {
            return Subtraction.LONG;
        } else if (clazz == float.class) {
            return Subtraction.FLOAT;
        } else if (clazz == double.class) {
            return Subtraction.DOUBLE;
        }
        return Subtraction.INTEGER;
    }

    public static Class<?> getUpCastClassForMathOp(Class<?>... classes) {
        final int length = classes.length;
        if (length <= 1) {
            throw new Ts2JavaException("Cannot get up cast class for less than 2 classes");
        }
        for (Class<?> c : classes) {
            if (!c.isPrimitive()) {
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format(
                                "Cannot get up cast class for ${class}",
                                SimpleMap.of("class", c.getName())));
            }
        }
        Class<?> clazz = classes[0];
        for (int i = 1; i < length; i++) {
            Class<?> c = classes[i];
            if (PrimitiveWideningDelegate.forPrimitive(TypeDescription.ForLoadedType.of(clazz))
                    .widenTo(TypeDescription.ForLoadedType.of(c)).isValid()) {
                clazz = c;
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
