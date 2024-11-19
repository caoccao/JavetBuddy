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

package com.caoccao.javet.buddy.ts2java.compiler.instructions;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class JavaInstructionLogicalCondition extends BaseJavaInstructionLogical {
    protected int opcodeCompare;
    protected int opcodeCompareAndJump;
    protected int sizeImpact;

    public JavaInstructionLogicalCondition(
            int opcodeCompareAndJump,
            Label label) {
        this(opcodeCompareAndJump, -1, label);
    }

    public JavaInstructionLogicalCondition(
            int opcodeCompareAndJump,
            int sizeImpact,
            Label label) {
        this(Opcodes.NOP, opcodeCompareAndJump, sizeImpact, label);
    }

    public JavaInstructionLogicalCondition(
            int opcodeCompare,
            int opcodeCompareAndJump,
            int sizeImpact,
            Label label) {
        super(label);
        setOpcodeCompare(opcodeCompare);
        setOpcodeCompareAndJump(opcodeCompareAndJump);
        setSizeImpact(sizeImpact);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
        if (opcodeCompare > Opcodes.NOP) {
            methodVisitor.visitInsn(opcodeCompare);
        }
        methodVisitor.visitJumpInsn(opcodeCompareAndJump, label);
        return new Size(sizeImpact, 0);
    }

    @Override
    public JavaInstructionLogicalCondition flip() {
        throw new UnsupportedOperationException();
    }

    public int getOpcodeCompare() {
        return opcodeCompare;
    }

    public int getOpcodeCompareAndJump() {
        return opcodeCompareAndJump;
    }

    public int getSizeImpact() {
        return sizeImpact;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public JavaInstructionLogicalCondition setLabel(Label label) {
        super.setLabel(label);
        return this;
    }

    public JavaInstructionLogicalCondition setOpcodeCompare(int opcodeCompare) {
        this.opcodeCompare = opcodeCompare;
        return this;
    }

    public JavaInstructionLogicalCondition setOpcodeCompareAndJump(int opcodeCompareAndJump) {
        this.opcodeCompareAndJump = opcodeCompareAndJump;
        return this;
    }

    public JavaInstructionLogicalCondition setSizeImpact(int sizeImpact) {
        this.sizeImpact = sizeImpact;
        return this;
    }
}
