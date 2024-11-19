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

import java.util.Objects;

public class JavaInstructionLogicalAnd implements IJavaInstructionLogical {
    protected Label label;

    public JavaInstructionLogicalAnd(Label label) {
        setLabel(label);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, label);
        return new Size(-1, 0);
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public JavaInstructionLogicalAnd setLabel(Label label) {
        this.label = Objects.requireNonNull(label);
        return this;
    }
}
