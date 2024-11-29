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
import com.caoccao.javet.buddy.ts2java.ast.expr.lit.Ts2JavaAstNumber;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstSimpleAssignTarget;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstParenExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstParenExpr
        extends BaseTs2JavaAst<Swc4jAstParenExpr, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstParenExpr, Ts2JavaMemoFunction>,
        ITs2JavaAstSimpleAssignTarget<Swc4jAstParenExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> expr;

    public Ts2JavaAstParenExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstParenExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        ISwc4jAstExpr astExpr = ast.getExpr();
        switch (astExpr.getType()) {
            case Number:
                expr = new Ts2JavaAstNumber(this, astExpr.as(Swc4jAstNumber.class), null, memo);
                break;
            case ParenExpr:
                expr = new Ts2JavaAstParenExpr(this, astExpr.as(Swc4jAstParenExpr.class), memo);
                break;
            default:
                throw new Ts2JavaAstException(
                        astExpr,
                        SimpleFreeMarkerFormat.format("ParentExpr expr type ${exprType} is not supported.",
                                SimpleMap.of("exprType", astExpr.getType().name())));
        }
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return expr.apply(methodVisitor, context);
    }

    @Override
    public void compile() {
        expr.compile();
    }

    public ITs2JavaAstExpr<?, ?> getExpr() {
        return expr;
    }

    @Override
    public TypeDescription getType() {
        return expr.getType();
    }
}
