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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAstWithBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaDynamicTypeBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.stmt.Ts2JavaAstBlockStmt;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;

import java.util.Optional;

public class Ts2JavaAstFunction
        extends BaseTs2JavaAstWithBuilderStore<Swc4jAstFunction> {
    protected final boolean _static;
    protected final Swc4jAstAccessibility accessibility;
    protected final Optional<Ts2JavaAstBlockStmt> body;
    protected final String name;

    public Ts2JavaAstFunction(
            Ts2JavaDynamicTypeBuilderStore builderStore,
            Swc4jAstFunction ast,
            String name,
            boolean _static,
            Swc4jAstAccessibility accessibility) {
        super(builderStore, ast);
        this._static = _static;
        this.accessibility = accessibility;
        this.name = name;
        body = ast.getBody().map(Ts2JavaAstBlockStmt::new);
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

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return _static;
    }
}
