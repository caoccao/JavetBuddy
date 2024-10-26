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

import com.caoccao.javet.buddy.ts2java.Ts2JavaException;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.Map;
import java.util.Optional;

public final class JavaByteCodeOpLoad {
    private final static Map<Class<?>, Integer> OP_CODE_MAP = SimpleMap.of(
            boolean.class, Opcodes.ILOAD,
            byte.class, Opcodes.ILOAD,
            short.class, Opcodes.ILOAD,
            char.class, Opcodes.ILOAD,
            int.class, Opcodes.ILOAD,
            long.class, Opcodes.LLOAD,
            float.class, Opcodes.FLOAD,
            double.class, Opcodes.DLOAD
    );

    private final static int OP_CODE_REFERENCE = Opcodes.ALOAD;

    private JavaByteCodeOpLoad() {
    }

    public static void generateByteCode(JavaFunctionContext functionContext, String name, MethodVisitor methodVisitor) {
        final int size = functionContext.getStackFrames().size();
        JavaStackObject stackObject = null;
        int stackFrameIndex = 0;
        for (stackFrameIndex = size - 1; stackFrameIndex >= 0; stackFrameIndex--) {
            JavaStackFrame stackFrame = functionContext.getStackFrames().get(stackFrameIndex);
            stackObject = stackFrame.getObjectMap().get(name);
            if (stackObject != null) {
                break;
            }
        }
        if (stackObject == null) {
            throw new Ts2JavaException(
                    SimpleFreeMarkerFormat.format("The variable ${name} is not defined",
                            SimpleMap.of("name", name)));
        }
        int opCode = Optional.ofNullable(OP_CODE_MAP.get(stackObject.getType())).orElse(OP_CODE_REFERENCE);
        int stackDepth = functionContext.getStackDepth(stackFrameIndex - 1);
        int index = stackDepth + stackObject.getIndex() + 1;
        methodVisitor.visitVarInsn(opCode, index);
    }
}
