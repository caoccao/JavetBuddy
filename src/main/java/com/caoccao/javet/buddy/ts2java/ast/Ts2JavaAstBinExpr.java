/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.List;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    private boolean logicalNot;

    public Ts2JavaAstBinExpr() {
        logicalNot = false;
    }

    public boolean isLogicalNot() {
        return logicalNot;
    }

    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstBinExpr ast) {
        final List<StackManipulation> stackManipulations = functionContext.getStackManipulations();
        final ISwc4jAstExpr leftExpression = ast.getLeft().unParenExpr();
        final ISwc4jAstExpr rightExpression = ast.getRight().unParenExpr();
        final TypeDescription leftType = manipulateExpression(functionContext, leftExpression);
        final int stackManipulationSize = stackManipulations.size();
        final TypeDescription rightType = manipulateExpression(functionContext, rightExpression);
        TypeDescription upCaseType = JavaClassCast.getUpCastTypeForMathOp(leftType, rightType);
        // Insert the type cast for left expression if possible.
        JavaClassCast.getUpCastStackManipulation(leftType, upCaseType)
                .ifPresent(stackManipulation -> stackManipulations.add(stackManipulationSize, stackManipulation));
        // Add the type cast for right expression if possible.
        JavaClassCast.getUpCastStackManipulation(rightType, upCaseType).ifPresent(stackManipulations::add);
        StackManipulation stackManipulation;
        switch (ast.getOp()) {
            case Add:
                stackManipulation = Ts2JavaAstBinaryOp.getAddition(upCaseType);
                break;
            case Div:
                stackManipulation = Ts2JavaAstBinaryOp.getDivision(upCaseType);
                break;
            case LShift:
                stackManipulation = Ts2JavaAstBinaryOp.getShiftLeft(upCaseType);
                break;
            case Mod:
                stackManipulation = Ts2JavaAstBinaryOp.getRemainder(upCaseType);
                break;
            case Mul:
                stackManipulation = Ts2JavaAstBinaryOp.getMultiplication(upCaseType);
                break;
            case RShift:
                stackManipulation = Ts2JavaAstBinaryOp.getShiftRight(upCaseType);
                break;
            case Sub:
                stackManipulation = Ts2JavaAstBinaryOp.getSubtraction(upCaseType);
                break;
            case Gt:
            case GtEq:
            case Lt:
            case LtEq:
            case EqEq:
            case EqEqEq:
            case NotEq:
            case NotEqEq:
                stackManipulation = Ts2JavaAstBinaryOp.getLogical(
                        functionContext, ast.getOp(), upCaseType, logicalNot);
                break;
//            case BitAnd:
//                stackManipulation = Ts2JavaAstBinaryOp.getBitAndStackManipulation(functionContext);
//                break;
//            case BitOr:
//                stackManipulation = Ts2JavaAstBinaryOp.getBitOrStackManipulation(functionContext);
//                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("BinExpr op ${op} is not supported.",
                                SimpleMap.of("op", ast.getOp().name())));
        }
        stackManipulations.add(stackManipulation);
        return upCaseType;
    }

    private TypeDescription manipulateExpression(JavaFunctionContext functionContext, ISwc4jAstExpr expression) {
        switch (expression.getType()) {
            case BinExpr:
                return new Ts2JavaAstBinExpr().manipulate(functionContext, expression.as(Swc4jAstBinExpr.class));
            case Ident:
                return new Ts2JavaAstIdent().manipulate(functionContext, expression.as(Swc4jAstIdent.class));
            case UnaryExpr:
                return new Ts2JavaAstUnaryExpr().manipulate(functionContext, expression.as(Swc4jAstUnaryExpr.class));
            default:
                throw new Ts2JavaAstException(
                        expression,
                        SimpleFreeMarkerFormat.format("BinExpr left expr type ${exprType} is not supported.",
                                SimpleMap.of("exprType", expression.getType().name())));
        }
    }

    public Ts2JavaAstBinExpr setLogicalNot(boolean logicalNot) {
        this.logicalNot = logicalNot;
        return this;
    }
}
