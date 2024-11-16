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

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLogicalLabels;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.List;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    private static StackManipulation getLogicalClose(JavaLogicalLabels logicalLabels) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            if (logicalLabels.size() > 2) {
                Label labelTrue = logicalLabels.get(2);
                methodVisitor.visitLabel(labelTrue);
                methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
            Label labelFalse = logicalLabels.get(1);
            Label labelClose = logicalLabels.get(0);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelClose);
            methodVisitor.visitLabel(labelFalse);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelClose);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            return StackManipulation.Size.ZERO;
        });
    }

    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstBinExpr ast) {
        Swc4jAstBinaryOp binaryOp = ast.getOp();
        if (binaryOp.isLogicalOperator()) {
            functionContext.increaseLogicalDepth();
        }
        final List<StackManipulation> stackManipulations = functionContext.getStackManipulations();
        final ISwc4jAstExpr leftExpression = ast.getLeft().unParenExpr();
        final ISwc4jAstExpr rightExpression = ast.getRight().unParenExpr();
        final TypeDescription leftType = manipulateExpression(functionContext, leftExpression);
        final int stackManipulationSize = stackManipulations.size();
        final TypeDescription rightType = manipulateExpression(functionContext, rightExpression);
        TypeDescription upCaseType;
        switch (binaryOp) {
            case Exp:
                upCaseType = TypeDescription.ForLoadedType.of(double.class);
                break;
            default:
                upCaseType = JavaClassCast.getUpCastTypeForMathOp(leftType, rightType);
                break;
        }
        // Insert the type cast for left expression if possible.
        JavaClassCast.getUpCastStackManipulation(leftType, upCaseType)
                .ifPresent(stackManipulation -> stackManipulations.add(stackManipulationSize, stackManipulation));
        // Add the type cast for right expression if possible.
        JavaClassCast.getUpCastStackManipulation(rightType, upCaseType).ifPresent(stackManipulations::add);
        StackManipulation stackManipulation;
        if (binaryOp.isArithmeticOperator()) {
            stackManipulation = Ts2JavaAstBinaryOp.getArithmetic(binaryOp, upCaseType);
        } else if (binaryOp.isLogicalOperator()) {
            stackManipulation = Ts2JavaAstBinaryOp.getLogical(functionContext, ast, binaryOp, upCaseType);
//        } else if (binaryOp.isBitOperator()) {
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("BinExpr op ${op} is not supported.",
                            SimpleMap.of("op", binaryOp.name())));
        }
        stackManipulations.add(stackManipulation);
        if (binaryOp.isLogicalOperator()) {
            if (functionContext.getLogicalDepth() == 1) {
                stackManipulations.add(getLogicalClose(functionContext.getLogicalLabels()));
            }
            functionContext.decreaseLogicalDepth();
        }
        return upCaseType;
    }

    private TypeDescription manipulateExpression(JavaFunctionContext functionContext, ISwc4jAstExpr expression) {
        switch (expression.getType()) {
            case BinExpr:
                return new Ts2JavaAstBinExpr().manipulate(functionContext, expression.as(Swc4jAstBinExpr.class));
            case Ident:
                return new Ts2JavaAstIdent().manipulate(functionContext, expression.as(Swc4jAstIdent.class));
            case Number:
                return new Ts2JavaAstNumber().manipulate(functionContext, expression.as(Swc4jAstNumber.class));
            case UnaryExpr:
                return new Ts2JavaAstUnaryExpr().manipulate(functionContext, expression.as(Swc4jAstUnaryExpr.class));
            default:
                throw new Ts2JavaAstException(
                        expression,
                        SimpleFreeMarkerFormat.format("BinExpr left expr type ${exprType} is not supported.",
                                SimpleMap.of("exprType", expression.getType().name())));
        }
    }
}
