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

import net.bytebuddy.jar.asm.Label;

import java.util.Objects;

public abstract class BaseJavaInstructionLogical implements IJavaInstructionLogical {
    protected boolean flipped;
    protected Label label;

    public BaseJavaInstructionLogical(Label label) {
        setLabel(label);
        flipped = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BaseJavaInstructionLogical flip() {
        flipped = !flipped;
        return this;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    @Override
    public boolean isFlipped() {
        return flipped;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BaseJavaInstructionLogical setLabel(Label label) {
        this.label = Objects.requireNonNull(label);
        return this;
    }
}
