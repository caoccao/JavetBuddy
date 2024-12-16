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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstDecl;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstForHead;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstVarDeclOrExpr;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDecl;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class Ts2JavaAstVarDecl
        extends BaseTs2JavaAst<Swc4jAstVarDecl, Ts2JavaMemoFunction>
        implements ITs2JavaAstDecl<Swc4jAstVarDecl, Ts2JavaMemoFunction>,
        ITs2JavaAstVarDeclOrExpr<Swc4jAstVarDecl, Ts2JavaMemoFunction>,
        ITs2JavaAstForHead<Swc4jAstVarDecl, Ts2JavaMemoFunction> {
    protected final List<Ts2JavaAstVarDeclarator> decls;

    protected Ts2JavaAstVarDecl(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstVarDecl ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        decls = ast.getDecls().stream()
                .map(decl -> Ts2JavaAstVarDeclarator.create(this, decl, memo))
                .collect(Collectors.toList());
    }

    public static Ts2JavaAstVarDecl create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstVarDecl ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstVarDecl(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return decls.stream()
                .map(decl -> decl.apply(methodVisitor, context))
                .reduce(BaseTs2JavaAst::aggregateSize)
                .orElse(Size.ZERO);
    }

    @Override
    public void compile() {
        decls.forEach(Ts2JavaAstVarDeclarator::compile);
    }

    public List<Ts2JavaAstVarDeclarator> getDecls() {
        return decls;
    }

    @Override
    public void syncLabels() {
        decls.forEach(Ts2JavaAstVarDeclarator::syncLabels);
    }
}
