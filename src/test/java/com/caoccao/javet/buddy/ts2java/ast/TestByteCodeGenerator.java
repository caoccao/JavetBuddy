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

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestByteCodeGenerator {
    @Test
    public void test() throws Exception {
        /*
         * public long add(int a, int b) {
         *     return a + b;
         * }
  public add(II)J
   L0
    ILOAD 1
    ILOAD 2
    IADD
    I2L
    LRETURN
         */
        try (DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
                .subclass(Object.class, ConstructorStrategy.Default.DEFAULT_CONSTRUCTOR)
                .defineMethod("test", long.class, Visibility.PUBLIC)
                .withParameters(int.class, int.class)
                .intercept(new Implementation.Simple((
                        methodVisitor,
                        implementationContext,
                        instrumentedMethod) -> {
                    StackManipulation.Size size = new StackManipulation.Compound(
                            new StackManipulation.Simple((
                                    MethodVisitor simpleMethodVisitor,
                                    Implementation.Context simpleImplementationContext) -> {
                                simpleMethodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                                simpleMethodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
                                simpleMethodVisitor.visitInsn(Opcodes.IADD);
                                simpleMethodVisitor.visitInsn(Opcodes.I2L);
                                return new StackManipulation.Size(2, 0);
                            }),
                            MethodReturn.LONG
                    ).apply(methodVisitor, implementationContext);
                    return new ByteCodeAppender.Size(
                            size.getMaximalSize(),
                            instrumentedMethod.getStackSize());
                }))
                .make()) {
            Class<?> clazz = unloadedType.load(getClass().getClassLoader()).getLoaded();
            Object object = clazz.getConstructor().newInstance();
            Random random = new Random();
            Method method = clazz.getMethod("test", int.class, int.class);
            for (int i = 0; i < 10; i++) {
                int a = random.nextInt() / 1_000_000;
                int b = random.nextInt() / 1_000_000;
                assertEquals((long) (a + b), method.invoke(object, a, b));
            }
        }
    }
}
