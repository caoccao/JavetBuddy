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
import com.caoccao.javet.buddy.ts2java.ast.pat.Ts2JavaAstBindingIdent;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDeclarator;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstVarDeclarator
        extends BaseTs2JavaAst<Swc4jAstVarDeclarator, Ts2JavaMemoFunction>
        implements ITs2JavaAstDecl<Swc4jAstVarDeclarator, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstExpr<?, ?>> init;
    protected final ITs2JavaAstPat<?, ?> name;
    protected JavaLocalVariable localVariable;
    protected int offset;

    public Ts2JavaAstVarDeclarator(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstVarDeclarator ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        name = ITs2JavaAstPat.cast(this, ast.getName(), memo);
        type = name.getType();
        init = ast.getInit().map(expr -> ITs2JavaAstExpr.cast(this, expr, memo));
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        Size sizeInit = Size.ZERO;
        if (init.isPresent()) {
            ITs2JavaAstExpr<?, ?> initExpr = init.get();
            sizeInit = initExpr.apply(methodVisitor, context);
            Size sizeCast = JavaClassCast.getUpCastStackManipulation(initExpr.getType(), type)
                    .map(s -> s.apply(methodVisitor, context))
                    .orElse(Size.ZERO);
            sizeInit = aggregateSize(sizeInit, sizeCast);
        }
        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(type);
        StackManipulation stackManipulation = methodVariableAccess.storeAt(offset);
        Size sizeStore = stackManipulation.apply(methodVisitor, context);
        return aggregateSize(sizeInit, sizeStore);
    }

    @Override
    public void compile() {
        init.ifPresent(ITs2JavaAstExpr::compile);
        String variableName;
        switch (name.getAst().getType()) {
            case BindingIdent:
                variableName = name.as(Ts2JavaAstBindingIdent.class).getId().getSym();
                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Var declarator name type ${type} is not supported.",
                                SimpleMap.of("type", name.getAst().getType().name())));
        }
        // Type can be deduced from init.
        if (type == null && init.isPresent()) {
            type = init.get().getType();
        }
        // Type is missing.
        if (type == null) {
            throw new Ts2JavaAstException(ast, "Var declarator type is missing.");
        }
        offset = memo.getNextOffset();
        localVariable = new JavaLocalVariable(variableName, type);
        memo.addLocalVariable(localVariable);
        type = localVariable.getType();
    }

    public Optional<ITs2JavaAstExpr<?, ?>> getInit() {
        return init;
    }

    public ITs2JavaAstPat<?, ?> getName() {
        return name;
    }
}
