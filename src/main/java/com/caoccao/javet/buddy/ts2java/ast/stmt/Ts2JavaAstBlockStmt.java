/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstBlockStmtOrExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstBlockStmt;
import com.caoccao.javet.utils.ListUtils;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class Ts2JavaAstBlockStmt
        extends BaseTs2JavaAst<Swc4jAstBlockStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstBlockStmt, Ts2JavaMemoFunction>,
        ITs2JavaAstBlockStmtOrExpr<Swc4jAstBlockStmt, Ts2JavaMemoFunction> {
    protected final List<ITs2JavaAstStmt<?, ?>> stmts;

    protected Ts2JavaAstBlockStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBlockStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        stmts = ast.getStmts().stream()
                .map(stmt -> ITs2JavaAstStmt.create(this, stmt, memo))
                .collect(Collectors.toList());
    }

    public static Ts2JavaAstBlockStmt create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBlockStmt ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstBlockStmt(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        memo.pushLexicalScope();
        Size size = stmts.stream()
                .map((stmt) -> stmt.apply(methodVisitor, context))
                .reduce(BaseTs2JavaAst::aggregateSize)
                .orElse(Size.ZERO);
        memo.popLexicalScope();
        return size;
    }

    @Override
    public void compile() {
        stmts.forEach(ITs2JavaAstStmt::compile);
        if (type == null && ListUtils.isNotEmpty(stmts)) {
            type = stmts.get(stmts.size() - 1).getType();
        }
    }

    @Override
    public void syncLabels() {
        stmts.forEach(ITs2JavaAstStmt::syncLabels);
    }
}
