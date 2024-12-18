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

package com.caoccao.javet.buddy.ts2java.ast.interfaces;

import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.stmt.*;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstStmt;
import com.caoccao.javet.swc4j.ast.stmt.*;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public interface ITs2JavaAstStmt<AST extends ISwc4jAstStmt, Memo extends Ts2JavaMemo>
        extends ITs2JavaAstModuleItem<AST, Memo> {
    static ITs2JavaAstStmt<?, ?> create(
            ITs2JavaAst<?, ?> parent,
            ISwc4jAstStmt ast,
            Ts2JavaMemoFunction memo) {
        switch (ast.getType()) {
            case BlockStmt:
                return Ts2JavaAstBlockStmt.create(parent, ast.as(Swc4jAstBlockStmt.class), memo);
            case EmptyStmt:
                return Ts2JavaAstEmptyStmt.create(parent, ast.as(Swc4jAstEmptyStmt.class), memo);
            case IfStmt:
                return Ts2JavaAstIfStmt.create(parent, ast.as(Swc4jAstIfStmt.class), memo);
            case ReturnStmt:
                return Ts2JavaAstReturnStmt.create(parent, ast.as(Swc4jAstReturnStmt.class), memo);
            case VarDecl:
                return Ts2JavaAstVarDecl.create(parent, ast.as(Swc4jAstVarDecl.class), memo);
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Stmt type ${type} is not supported.",
                                SimpleMap.of("type", ast.getType().name())));
        }
    }
}
