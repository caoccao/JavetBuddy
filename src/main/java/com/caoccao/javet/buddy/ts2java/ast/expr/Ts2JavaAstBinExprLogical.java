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
import net.bytebuddy.jar.asm.Label;

public abstract class Ts2JavaAstBinExprLogical extends Ts2JavaAstBinExpr
        implements ITs2JavaBangFlippable {
    protected Label labelFalse;
    protected Label labelTrue;

    protected Ts2JavaAstBinExprLogical(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        labelFalse = null;
        labelTrue = null;
        type = TypeDescription.ForLoadedType.of(boolean.class);
    }

    @Override
    public void compile() {
        super.compile();
        if (labelFalse == null) {
            labelFalse = new Label();
        }
    }

    @Override
    public void flipBang() {
        op = op.getOppositeOperator();
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

    public Ts2JavaAstBinExprLogical setLabelFalse(Label labelFalse) {
        this.labelFalse = labelFalse;
        return this;
    }

    public Ts2JavaAstBinExprLogical setLabelTrue(Label labelTrue) {
        this.labelTrue = labelTrue;
        return null;
    }
}
