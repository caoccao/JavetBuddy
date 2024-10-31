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
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

import java.util.Optional;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    @Override
    public Optional<TypeDescription> manipulate(JavaFunctionContext functionContext, Swc4jAstBinExpr ast) {
        final TypeDescription leftType = manipulateExpression(functionContext, ast.getLeft());
        final int stackManipulationSize = functionContext.getStackManipulations().size();
        final TypeDescription rightType = manipulateExpression(functionContext, ast.getRight());
        TypeDescription upCaseType = JavaClassCast.getUpCastTypeForMathOp(leftType, rightType);
        // Insert the type cast.
        JavaClassCast.getUpCastStackManipulation(leftType, upCaseType)
                .ifPresent(stackManipulation ->
                        functionContext.getStackManipulations().add(stackManipulationSize, stackManipulation));
        JavaClassCast.getUpCastStackManipulation(rightType, upCaseType)
                .ifPresent(functionContext.getStackManipulations()::add);
        StackManipulation stackManipulation;
        switch (ast.getOp()) {
            case Add:
                stackManipulation = JavaClassCast.getAddition(upCaseType);
                break;
            case Div:
                stackManipulation = JavaClassCast.getDivision(upCaseType);
                break;
            case LShift:
                stackManipulation = JavaClassCast.getShiftLeft(upCaseType);
                break;
            case Mod:
                stackManipulation = JavaClassCast.getRemainder(upCaseType);
                break;
            case Mul:
                stackManipulation = JavaClassCast.getMultiplication(upCaseType);
                break;
            case RShift:
                stackManipulation = JavaClassCast.getShiftRight(upCaseType);
                break;
            case Sub:
                stackManipulation = JavaClassCast.getSubtraction(upCaseType);
                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("BinExpr op ${op} is not supported.",
                                SimpleMap.of("op", ast.getOp().name())));
        }
        functionContext.getStackManipulations().add(stackManipulation);
        return Optional.of(upCaseType);
    }

    private TypeDescription manipulateExpression(JavaFunctionContext functionContext, ISwc4jAstExpr expression) {
        switch (expression.getType()) {
            case BinExpr:
                Optional<TypeDescription> optionalType =
                        new Ts2JavaAstBinExpr().manipulate(functionContext, expression.as(Swc4jAstBinExpr.class));
                if (optionalType.isPresent()) {
                    return optionalType.get();
                } else {
                    throw new Ts2JavaAstException(
                            expression,
                            SimpleFreeMarkerFormat.format("BinExpr left expr type ${exprType} is invalid.",
                                    SimpleMap.of("exprType", expression.getType().name())));
                }
            case Ident: {
                String name = expression.as(Swc4jAstIdent.class).getSym();
                JavaLocalVariable localVariable = functionContext.getLocalVariable(name);
                functionContext.getStackManipulations().add(
                        MethodVariableAccess.of(localVariable.getType()).loadFrom(localVariable.getOffset()));
                return localVariable.getType();
            }
            default:
                throw new Ts2JavaAstException(
                        expression,
                        SimpleFreeMarkerFormat.format("BinExpr left expr type ${exprType} is not supported.",
                                SimpleMap.of("exprType", expression.getType().name())));
        }
    }
}
