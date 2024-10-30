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

package com.caoccao.javet.buddy.ts2java.exceptions;

import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;

import java.util.Objects;

public class Ts2JavaAstException extends Ts2JavaException {
    protected ISwc4jAst ast;

    public Ts2JavaAstException(ISwc4jAst ast, String message) {
        super(message);
        this.ast = Objects.requireNonNull(ast);
    }

    public Ts2JavaAstException(ISwc4jAst ast, String message, Throwable cause) {
        super(message, cause);
        this.ast = Objects.requireNonNull(ast);
    }

    public ISwc4jAst getAst() {
        return ast;
    }
}