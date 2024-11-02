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

import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;

public final class Ts2JavaAstUnaryExpr implements ITs2JavaAstStackManipulation<Swc4jAstUnaryExpr> {
    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstUnaryExpr ast) {
        ISwc4jAstExpr arg = ast.getArg().unParenExpr();
        switch (ast.getOp()) {
            case Bang:
                switch (arg.getType()) {
                    case BinExpr:
                        return new Ts2JavaAstBinExpr().setLogicalNot(true).manipulate(
                                functionContext, arg.as(Swc4jAstBinExpr.class));
                    default:
                        throw new Ts2JavaAstException(
                                arg,
                                SimpleFreeMarkerFormat.format("UnaryExpr arg type ${argType} is not supported.",
                                        SimpleMap.of("argType", arg.getType().name())));
                }
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("UnaryExpr op ${op} is not supported.",
                                SimpleMap.of("op", ast.getOp().name())));
        }
    }
}
