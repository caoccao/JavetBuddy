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

import com.caoccao.javet.buddy.ts2java.compiler.JavaByteCodeMethodVariableAccess;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaStackObject;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleList;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.List;
import java.util.stream.Collectors;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    @Override
    public void manipulate(JavaFunctionContext functionContext, Swc4jAstBinExpr ast) {
        List<ISwc4jAstExpr> expressions = SimpleList.of(ast.getLeft(), ast.getRight());
        List<JavaStackObject> stackObjects = expressions.stream()
                .map(expression -> {
                    switch (expression.getType()) {
                        case Ident:
                            String name = expression.as(Swc4jAstIdent.class).getSym();
                            return functionContext.getStackObject(name);
                        default:
                            throw new Ts2JavaAstException(
                                    expression,
                                    SimpleFreeMarkerFormat.format("BinExpr expr type ${exprType} is not supported",
                                            SimpleMap.of("exprType", expression.getType().name())));
                    }
                })
                .collect(Collectors.toList());
        Class<?> upCastClass = JavaClassCast.getUpCastClassForMathOp(
                stackObjects.stream().map(JavaStackObject::getType).toArray(Class[]::new));
        stackObjects.forEach(stackObject -> {
            functionContext.getStackManipulations().add(JavaByteCodeMethodVariableAccess.load(stackObject));
            JavaClassCast.getUpCastStackManipulation(stackObject.getType(), upCastClass)
                    .ifPresent(functionContext.getStackManipulations()::add);
        });
        StackManipulation stackManipulation;
        switch (ast.getOp()) {
            case Add:
                stackManipulation = JavaClassCast.getAddition(upCastClass);
                break;
            case Div:
                stackManipulation = JavaClassCast.getDivision(upCastClass);
                break;
            case Mul:
                stackManipulation = JavaClassCast.getMultiplication(upCastClass);
                break;
            case Sub:
                stackManipulation = JavaClassCast.getSubtraction(upCastClass);
                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("BinExpr op ${op} is not supported",
                                SimpleMap.of("op", ast.getOp().name())));
        }
        functionContext.getStackManipulations().add(stackManipulation);
    }
}
