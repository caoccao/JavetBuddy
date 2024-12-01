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
import com.caoccao.javet.buddy.ts2java.ast.enums.Ts2JavaAstBinaryOp;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstBinExpr
        extends BaseTs2JavaAst<Swc4jAstBinExpr, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstBinExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> left;
    protected final ITs2JavaAstExpr<?, ?> right;
    protected Swc4jAstBinaryOp op;

    public Ts2JavaAstBinExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        op = ast.getOp();
        if (op.isArithmeticOperator()) {
            if (op == Swc4jAstBinaryOp.Exp) {
                type = TypeDescription.ForLoadedType.of(double.class);
            }
        } else if (op.isLogicalOperator()) {
            type = TypeDescription.ForLoadedType.of(boolean.class);
        }
        left = ITs2JavaAstExpr.cast(parent, ast.getLeft(), memo);
        right = ITs2JavaAstExpr.cast(parent, ast.getRight(), memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        Size sizeLoadLeft = left.apply(methodVisitor, context);
        TypeDescription newType = left.getType();
        Size sizeCastLeft = Size.ZERO;
        TypeDescription rightTargetType = op == Swc4jAstBinaryOp.Exp ? type : right.getType();
        Optional<StackManipulation> optionalStackManipulation =
                JavaClassCast.getUpCastStackManipulation(left.getType(), rightTargetType);
        if (optionalStackManipulation.isPresent()) {
            sizeCastLeft = optionalStackManipulation.get().apply(methodVisitor, context);
            newType = rightTargetType;
        }
        Size sizeLoadRight = right.apply(methodVisitor, context);
        Size sizeCastRight = JavaClassCast.getUpCastStackManipulation(right.getType(), newType)
                .map(s -> s.apply(methodVisitor, context))
                .orElse(Size.ZERO);
        Size sizeOp;
        if (op.isArithmeticOperator()) {
            sizeOp = Ts2JavaAstBinaryOp.getArithmeticStackManipulation(op, newType)
                    .apply(methodVisitor, context);
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Bin expr op ${op} is not supported.",
                            SimpleMap.of("op", op.name())));
        }
        Size sizeCastResult = JavaClassCast.getUpCastStackManipulation(newType, type)
                .map(s -> s.apply(methodVisitor, context))
                .orElse(Size.ZERO);
        return aggregateSize(sizeLoadLeft, sizeCastLeft, sizeLoadRight, sizeCastRight, sizeOp, sizeCastResult);
    }

    @Override
    public void compile() {
        left.compile();
        right.compile();
        if (type == null && (left.getType() != null || right.getType() != null)) {
            if (left.getType() != null && right.getType() == null) {
                type = left.getType();
            } else if (left.getType() == null && right.getType() != null) {
                type = right.getType();
            } else if (JavaClassCast.getUpCastStackManipulation(left.getType(), right.getType()).isPresent()) {
                type = right.getType();
            } else if (JavaClassCast.getUpCastStackManipulation(right.getType(), left.getType()).isPresent()) {
                type = left.getType();
            } else {
                type = left.getType();
            }
        }
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
}
