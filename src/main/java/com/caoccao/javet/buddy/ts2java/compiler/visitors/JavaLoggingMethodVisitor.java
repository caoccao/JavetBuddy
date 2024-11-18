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

package com.caoccao.javet.buddy.ts2java.compiler.visitors;

import com.caoccao.javet.buddy.ts2java.compiler.JavaOpcodeUtils;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;

public class JavaLoggingMethodVisitor extends MethodVisitor {
    public JavaLoggingMethodVisitor(int api) {
        super(api);
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        System.out.println(" FRAME " + JavaOpcodeUtils.getFrameTypeName(type) + " " + numLocal + " " + local + " " + numStack + " " + stack);
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println(" " + JavaOpcodeUtils.getOpcodeName(opcode));
        super.visitInsn(opcode);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        System.out.println(" " + JavaOpcodeUtils.getOpcodeName(opcode) + " " + label);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println(label + ":");
        super.visitLabel(label);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        System.out.println(" LINENUMBER " + line + " " + start);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        System.out.println(" " + JavaOpcodeUtils.getOpcodeName(opcode) + " " + varIndex);
        super.visitVarInsn(opcode, varIndex);
    }
}
