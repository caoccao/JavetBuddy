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

package com.caoccao.javet.buddy.ts2java.ast.pat;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstIdent;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.*;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.ts.Ts2JavaAstTsTypeAnn;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.swc4j.ast.pat.Swc4jAstBindingIdent;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstBindingIdent
        extends BaseTs2JavaAst<Swc4jAstBindingIdent, Ts2JavaMemoFunction>
        implements ITs2JavaAstPat<Swc4jAstBindingIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsFnParam<Swc4jAstBindingIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsParamPropParam<Swc4jAstBindingIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstSimpleAssignTarget<Swc4jAstBindingIdent, Ts2JavaMemoFunction> {
    protected final Ts2JavaAstIdent id;
    protected final Optional<Ts2JavaAstTsTypeAnn> typeAnn;

    protected Ts2JavaAstBindingIdent(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBindingIdent ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        typeAnn = ast.getTypeAnn().map(t -> Ts2JavaAstTsTypeAnn.create(this, t, memo));
        typeAnn.ifPresent(t -> type = t.getType());
        id = Ts2JavaAstIdent.create(this, ast.getId(), memo);
    }

    public static Ts2JavaAstBindingIdent create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBindingIdent ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstBindingIdent(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
        id.compile();
        typeAnn.ifPresent(Ts2JavaAstTsTypeAnn::compile);
    }

    public Ts2JavaAstIdent getId() {
        return id;
    }

    public JavaLocalVariable getLocalVariable() {
        return new JavaLocalVariable(id.getSym(), type);
    }

    public Optional<Ts2JavaAstTsTypeAnn> getTypeAnn() {
        return typeAnn;
    }
}
