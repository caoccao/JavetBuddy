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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.*;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstIdent
        extends BaseTs2JavaAst<Swc4jAstIdent, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstProp<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsModuleRef<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstModuleExportName<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsEntityName<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsModuleName<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstJsxObject<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstJsxElementName<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsThisTypeOrIdent<Swc4jAstIdent, Ts2JavaMemoFunction>,
        ITs2JavaAstTsEnumMemberId<Swc4jAstIdent, Ts2JavaMemoFunction> {
    protected final boolean optional;
    protected final String sym;
    protected JavaLocalVariable localVariable;

    public Ts2JavaAstIdent(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstIdent ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        localVariable = null;
        optional = ast.isOptional();
        sym = ast.getSym();
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(localVariable.getType());
        StackManipulation stackManipulation = methodVariableAccess.loadFrom(localVariable.getOffset());
        Size size = stackManipulation.apply(methodVisitor, context);
        Size sizeCast = JavaClassCast.getUpCastStackManipulation(localVariable.getType(), type)
                .map(s -> s.apply(methodVisitor, context))
                .orElse(Size.ZERO);
        return aggregateSize(size, sizeCast);
    }

    @Override
    public void compile() {
        localVariable = memo.getLocalVariable(sym);
        type = localVariable.getType();
    }

    public String getSym() {
        return sym;
    }

    @Override
    public String getTypeName() {
        return sym;
    }

    public boolean isOptional() {
        return optional;
    }
}
