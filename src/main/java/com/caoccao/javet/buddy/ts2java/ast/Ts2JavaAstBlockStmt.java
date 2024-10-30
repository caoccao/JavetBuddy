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
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstStmt;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstBlockStmt;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDecl;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;

import java.util.Optional;

public final class Ts2JavaAstBlockStmt implements ITs2JavaAstStackManipulation<Swc4jAstBlockStmt> {
    @Override
    public Optional<TypeDescription> manipulate(JavaFunctionContext functionContext, Swc4jAstBlockStmt ast) {
        Optional<TypeDescription> returnType = Optional.empty();
        for (ISwc4jAstStmt stmt : ast.getStmts()) {
            switch (stmt.getType()) {
                case VarDecl:
                    returnType = new Ts2JavaAstVarDecl().manipulate(functionContext, stmt.as(Swc4jAstVarDecl.class));
                    break;
                case ReturnStmt:
                    returnType = new Ts2JavaAstReturnStmt().manipulate(functionContext, stmt.as(Swc4jAstReturnStmt.class));
                    break;
                default:
                    throw new Ts2JavaAstException(
                            stmt,
                            SimpleFreeMarkerFormat.format("BlockStmt type ${type} is not supported.",
                                    SimpleMap.of("type", stmt.getType().name())));
            }
        }
        return returnType;
    }
}
