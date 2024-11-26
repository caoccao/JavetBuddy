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

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaBuilderStore;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;

import java.util.Objects;

public abstract class BaseTs2JavaAstWithBuilderStore<AST extends ISwc4jAst>
        extends BaseTs2JavaAst<AST>
        implements ITs2JavaBuilderStore {
    protected final Ts2JavaDynamicTypeBuilderStore builderStore;

    public BaseTs2JavaAstWithBuilderStore(Ts2JavaDynamicTypeBuilderStore builderStore, AST ast) {
        super(ast);
        this.builderStore = Objects.requireNonNull(builderStore);
    }

    @Override
    public Ts2JavaDynamicTypeBuilderStore getBuilderStore() {
        return builderStore;
    }
}
