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

package com.caoccao.javet.buddy.ts2java.compiler.visitors;

import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class JavaByteCodeMethodVisitor extends MethodVisitor {
    protected final List<Integer> instList;
    protected final List<OpcodeAndLabel> jumpInstList;

    public JavaByteCodeMethodVisitor(int api) {
        super(api);
        instList = new ArrayList<>();
        jumpInstList = new ArrayList<>();
    }

    public List<Integer> getInstList() {
        return instList;
    }

    public List<OpcodeAndLabel> getJumpInstList() {
        return jumpInstList;
    }

    @Override
    public void visitInsn(int opcode) {
        instList.add(opcode);
        super.visitInsn(opcode);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        jumpInstList.add(new OpcodeAndLabel(opcode, label));
        super.visitJumpInsn(opcode, label);
    }

    public static class OpcodeAndLabel {
        private final Label label;
        private final int opcode;

        public OpcodeAndLabel(int opcode, Label label) {
            this.opcode = opcode;
            this.label = label;
        }

        public Label getLabel() {
            return label;
        }

        public int getOpcode() {
            return opcode;
        }

    }
}
