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

import com.caoccao.javet.buddy.ts2java.compiler.JavaByteCodeHint;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;

public final class Ts2JavaAstReturnStmt implements ITs2JavaAstStackManipulation<Swc4jAstReturnStmt> {
    @Override
    public JavaByteCodeHint manipulate(JavaFunctionContext functionContext, Swc4jAstReturnStmt ast) {
        Ts2JavaAst.manipulateLineNumber(functionContext, ast);
        JavaByteCodeHint hint = new JavaByteCodeHint();
        if (ast.getArg().isPresent()) {
            JavaByteCodeHint fromHint;
            ISwc4jAstExpr arg = ast.getArg().get().unParenExpr();
            switch (arg.getType()) {
                case BinExpr:
                    fromHint = new Ts2JavaAstBinExpr().manipulate(functionContext, arg.as(Swc4jAstBinExpr.class));
                    break;
                case Ident:
                    fromHint = new Ts2JavaAstIdent().manipulate(functionContext, arg.as(Swc4jAstIdent.class));
                    break;
                case UnaryExpr:
                    fromHint = new Ts2JavaAstUnaryExpr().manipulate(functionContext, arg.as(Swc4jAstUnaryExpr.class));
                    break;
                default:
                    throw new Ts2JavaAstException(
                            arg,
                            SimpleFreeMarkerFormat.format("ReturnStmt arg type ${argType} is not supported.",
                                    SimpleMap.of("argType", arg.getType().name())));
            }
            hint.setType(functionContext.getReturnType());
            JavaClassCast.getUpCastStackManipulation(fromHint.getType(), hint.getType())
                    .ifPresent(functionContext.getStackManipulations()::add);
            functionContext.getStackManipulations().add(MethodReturn.of(hint.getType()));
        }
        return hint;
    }
}
