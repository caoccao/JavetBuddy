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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstPat;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDeclarator;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstVarDeclarator
        extends BaseTs2JavaAst<Swc4jAstVarDeclarator, Ts2JavaMemoFunction>
        implements ITs2JavaAstDecl<Swc4jAstVarDeclarator, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstExpr<?, ?>> init;
    protected final ITs2JavaAstPat<?, ?> name;

    public Ts2JavaAstVarDeclarator(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstVarDeclarator ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        name = ITs2JavaAstPat.cast(this, ast.getName(), memo);
        type = name.getType();
        init = ast.getInit().map(expr -> ITs2JavaAstExpr.cast(this, expr, type, memo));
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    public Optional<ITs2JavaAstExpr<?, ?>> getInit() {
        return init;
    }

    public ITs2JavaAstPat<?, ?> getName() {
        return name;
    }
}
