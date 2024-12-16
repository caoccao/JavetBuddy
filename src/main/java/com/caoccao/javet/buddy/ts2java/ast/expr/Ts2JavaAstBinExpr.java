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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public abstract class Ts2JavaAstBinExpr
        extends BaseTs2JavaAst<Swc4jAstBinExpr, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstBinExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> left;
    protected final Swc4jAstBinaryOp op;
    protected final ITs2JavaAstExpr<?, ?> right;

    protected Ts2JavaAstBinExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        left = ITs2JavaAstExpr.create(this, ast.getLeft(), memo);
        op = ast.getOp();
        right = ITs2JavaAstExpr.create(this, ast.getRight(), memo);
    }

    public static Ts2JavaAstBinExpr create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        Swc4jAstBinaryOp op = ast.getOp();
        if (op.isArithmeticOperator()) {
            return new Ts2JavaAstBinExprArithmetic(parent, ast, memo);
        } else if (op.isLogicalCompareOperator()) {
            return new Ts2JavaAstBinExprLogicalCompare(parent, ast, memo);
        } else if (op.isLogicalConditionOperator()) {
            return new Ts2JavaAstBinExprLogicalCondition(parent, ast, memo);
        }
        throw new Ts2JavaAstException(
                ast,
                SimpleFreeMarkerFormat.format("Bin expr op ${op} is not supported.",
                        SimpleMap.of("op", ast.getOp().name())));
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
        left.compile();
        right.compile();
    }

    public ITs2JavaAstExpr<?, ?> getLeft() {
        return left;
    }

    public Swc4jAstBinaryOp getOp() {
        return op;
    }

    public ITs2JavaAstExpr<?, ?> getRight() {
        return right;
    }

    public boolean isTopBinExpr() {
        return parent == null || !(parent instanceof Ts2JavaAstBinExpr);
    }

    @Override
    public void syncLabels() {
        left.syncLabels();
        right.syncLabels();
    }
}
