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

package com.caoccao.javet.buddy.ts2java.ast.stmt;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

import java.util.Optional;

public class Ts2JavaAstReturnStmt
        extends BaseTs2JavaAst<Swc4jAstReturnStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstReturnStmt, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstExpr<?, ?>> arg;

    public Ts2JavaAstReturnStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstReturnStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        if (ast.getArg().isPresent()) {
            ITs2JavaAstExpr<?, ?> arg = null;
            ISwc4jAstExpr expr = ast.getArg().get().unParenExpr();
            switch (expr.getType()) {
                case BinExpr:
                    // TODO
                    break;
                case Number:
                    // TODO
                    break;
                case Ident:
                    // TODO
                    break;
                case UnaryExpr:
                    // TODO
                    break;
                default:
                    throw new Ts2JavaAstException(
                            expr,
                            SimpleFreeMarkerFormat.format("ReturnStmt arg type ${argType} is not supported.",
                                    SimpleMap.of("argType", expr.getType().name())));
            }
            this.arg = Optional.ofNullable(arg);
        } else {
            arg = Optional.empty();
        }
    }

    @Override
    public void compile() {
    }

    public Optional<ITs2JavaAstExpr<?, ?>> getArg() {
        return arg;
    }
}
