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

import com.caoccao.javet.buddy.ts2java.ast.enums.Ts2JavaAstBinaryOp;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ts2JavaAstBinExprLogicalCompare extends Ts2JavaAstBinExprLogical {

    protected Ts2JavaAstBinExprLogicalCompare(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        super.apply(methodVisitor, context);
        final List<Size> sizes = new ArrayList<>();
        sizes.add(left.apply(methodVisitor, context));
        TypeDescription upCastType = left.getType();
        TypeDescription rightTargetType = op == Swc4jAstBinaryOp.Exp ? type : right.getType();
        Optional<StackManipulation> optionalStackManipulation =
                JavaClassCast.getUpCastStackManipulation(left.getType(), rightTargetType);
        if (optionalStackManipulation.isPresent()) {
            sizes.add(optionalStackManipulation.get().apply(methodVisitor, context));
            upCastType = rightTargetType;
        }
        sizes.add(right.apply(methodVisitor, context));
        sizes.add(JavaClassCast.getUpCastStackManipulation(right.getType(), upCastType)
                .map(s -> s.apply(methodVisitor, context))
                .orElse(Size.ZERO));
        final Swc4jAstBinaryOp finalOp = bangFlipped ? op.getOppositeOperator() : op;
        sizes.add(Ts2JavaAstBinaryOp.getLogicalCompareStackManipulation(ast, finalOp, upCastType, labelFalse)
                .apply(methodVisitor, context));
        sizes.add(logicalClose(methodVisitor));
        return aggregateSize(sizes);
    }
}
