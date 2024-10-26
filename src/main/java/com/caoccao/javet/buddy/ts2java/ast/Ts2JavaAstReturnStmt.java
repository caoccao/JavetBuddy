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

import com.caoccao.javet.buddy.ts2java.Ts2JavaException;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;

import java.util.List;

public final class Ts2JavaAstReturnStmt implements ITs2JavaAstStackManipulation<Swc4jAstReturnStmt> {
    @Override
    public void manipulate(JavaFunctionContext functionContext, List<StackManipulation> stackManipulations, Swc4jAstReturnStmt ast) {
        ast.getArg().ifPresent(arg -> {
            switch (arg.getType()) {
                case BinExpr:
                    new Ts2JavaAstBinExpr().manipulate(functionContext, stackManipulations, arg.as(Swc4jAstBinExpr.class));
                    break;
                default:
                    throw new Ts2JavaException(arg.getType().name() + " is not supported");
            }
        });
        TypeDescription typeDescription = TypeDescription.ForLoadedType.of(functionContext.getReturnType());
        StackManipulation stackManipulation = MethodReturn.of(typeDescription);
        stackManipulations.add(stackManipulation);
    }
}
