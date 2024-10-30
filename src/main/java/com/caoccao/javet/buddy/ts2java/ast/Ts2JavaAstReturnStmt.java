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
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;

import java.util.Optional;

public final class Ts2JavaAstReturnStmt implements ITs2JavaAstStackManipulation<Swc4jAstReturnStmt> {
    @Override
    public Optional<TypeDescription> manipulate(JavaFunctionContext functionContext, Swc4jAstReturnStmt ast) {
        Optional<TypeDescription> optionalFromType = Optional.empty();
        if (ast.getArg().isPresent()) {
            ISwc4jAstExpr arg = ast.getArg().get();
            switch (arg.getType()) {
                case BinExpr:
                    optionalFromType = new Ts2JavaAstBinExpr().manipulate(functionContext, arg.as(Swc4jAstBinExpr.class));
                    break;
                default:
                    throw new Ts2JavaAstException(
                            arg,
                            SimpleFreeMarkerFormat.format("ReturnStmt arg type ${argType} is not supported.",
                                    SimpleMap.of("argType", arg.getType().name())));
            }
        }
        if (!optionalFromType.isPresent()) {
            throw new Ts2JavaAstException(ast, "ReturnStmt type is unknown");
        }
        TypeDescription returnType = functionContext.getReturnType();
        optionalFromType.ifPresent((fromType) ->
                JavaClassCast.upCast(fromType, returnType, functionContext::addStackManipulation));
        functionContext.addStackManipulation(MethodReturn.of(returnType));
        return Optional.of(returnType);
    }
}
