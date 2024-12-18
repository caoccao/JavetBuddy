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

package com.caoccao.javet.buddy.ts2java.ast.stmt;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstIfStmt;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ts2JavaAstIfStmt
        extends BaseTs2JavaAst<Swc4jAstIfStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstIfStmt, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstStmt<?, ?>> alt;
    protected final ITs2JavaAstStmt<?, ?> cons;
    protected final ITs2JavaAstExpr<?, ?> test;
    protected Label labelFalse;

    protected Ts2JavaAstIfStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstIfStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        alt = ast.getAlt().map(alt -> ITs2JavaAstStmt.create(this, alt, memo));
        cons = ITs2JavaAstStmt.create(this, ast.getCons(), memo);
        labelFalse = new Label();
        test = ITs2JavaAstExpr.create(this, ast.getTest(), memo);
        type = cons.getType();
    }

    public static Ts2JavaAstIfStmt create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstIfStmt ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstIfStmt(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        // TODO To optimize the jumps for bin expr. This implementation is slow.
        final List<Size> sizes = new ArrayList<>();
        sizes.add(test.apply(methodVisitor, context));
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, labelFalse);
        sizes.add(cons.apply(methodVisitor, context));
        methodVisitor.visitLabel(labelFalse);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        alt.ifPresent(stmt -> {
            sizes.add(stmt.apply(methodVisitor, context));
        });
        return aggregateSize(sizes);
    }

    @Override
    public void compile() {
        cons.compile();
        test.compile();
        alt.ifPresent(ITs2JavaAstStmt::compile);
        type = cons.getType();
    }

    public Optional<ITs2JavaAstStmt<?, ?>> getAlt() {
        return alt;
    }

    public ITs2JavaAstStmt<?, ?> getCons() {
        return cons;
    }

    public Label getLabelFalse() {
        return labelFalse;
    }

    public ITs2JavaAstExpr<?, ?> getTest() {
        return test;
    }

    public void setLabelFalse(Label labelFalse) {
        this.labelFalse = labelFalse;
    }

    @Override
    public void syncLabels() {
        cons.syncLabels();
        test.syncLabels();
        alt.ifPresent(ITs2JavaAstStmt::syncLabels);
    }
}
