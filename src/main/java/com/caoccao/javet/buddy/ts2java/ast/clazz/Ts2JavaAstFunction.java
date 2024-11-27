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

package com.caoccao.javet.buddy.ts2java.ast.clazz;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.stmt.Ts2JavaAstBlockStmt;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;

import java.util.Optional;

public class Ts2JavaAstFunction
        extends BaseTs2JavaAst<Swc4jAstFunction, Ts2JavaMemoDynamicType> {
    protected final boolean _static;
    protected final Swc4jAstAccessibility accessibility;
    protected final Optional<Ts2JavaAstBlockStmt> body;
    protected final Ts2JavaMemoFunction memoFunction;
    protected final String name;

    public Ts2JavaAstFunction(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstFunction ast,
            Ts2JavaMemoDynamicType memo,
            String name,
            boolean _static,
            Swc4jAstAccessibility accessibility) {
        super(parent, ast, memo);
        this._static = _static;
        this.accessibility = accessibility;
        memoFunction = new Ts2JavaMemoFunction();
        this.name = name;
        body = ast.getBody().map(stmt -> new Ts2JavaAstBlockStmt(this, stmt, memoFunction));
    }

    @Override
    public void compile() {

    }

    public Swc4jAstAccessibility getAccessibility() {
        return accessibility;
    }

    public Optional<Ts2JavaAstBlockStmt> getBody() {
        return body;
    }

    public Ts2JavaMemoFunction getMemoFunction() {
        return memoFunction;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return _static;
    }
}
