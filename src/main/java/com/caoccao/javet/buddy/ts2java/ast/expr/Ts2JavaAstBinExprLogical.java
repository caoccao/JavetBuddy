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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBangFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public abstract class Ts2JavaAstBinExprLogical extends Ts2JavaAstBinExpr
        implements ITs2JavaBangFlippable {
    protected boolean bangFlipped;
    protected Label labelFalse;
    protected boolean labelSwitched;
    protected Label labelTrue;

    protected Ts2JavaAstBinExprLogical(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        bangFlipped = false;
        labelFalse = new Label();
        labelTrue = new Label();
        labelSwitched = false;
        type = TypeDescription.ForLoadedType.of(boolean.class);
    }

    @Override
    public void flipBang() {
        bangFlipped = !bangFlipped;
    }

    public Label getLabelFalse() {
        return labelFalse;
    }

    public Label getLabelTrue() {
        return labelTrue;
    }

    @Override
    public boolean isBangFlippable() {
        return true;
    }

    public boolean isBangFlipped() {
        return bangFlipped;
    }

    public boolean isLabelSwitched() {
        return labelSwitched;
    }

    public abstract boolean isLabelTrueRequired();

    protected Size logicalClose(MethodVisitor methodVisitor) {
        if (!(parent instanceof Ts2JavaAstBinExpr)) {
            // This is the top bin expr. Let's close it.
            Label labelClose = new Label();
            if (isLabelTrueRequired()) {
                methodVisitor.visitLabel(labelTrue);
                methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelClose);
            methodVisitor.visitLabel(labelFalse);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelClose);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
        }
        return StackManipulation.Size.ZERO;
    }

    public Ts2JavaAstBinExprLogical setLabelFalse(Label labelFalse) {
        this.labelFalse = labelFalse;
        return this;
    }

    public Ts2JavaAstBinExprLogical setLabelTrue(Label labelTrue) {
        this.labelTrue = labelTrue;
        return this;
    }

    public void switchLabel() {
        labelSwitched = !labelSwitched;
    }
}
