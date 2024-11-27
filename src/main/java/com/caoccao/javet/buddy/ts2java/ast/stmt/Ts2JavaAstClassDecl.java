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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAstWithBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaDynamicTypeBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.clazz.Ts2JavaAstClass;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstDecl;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstClassDecl;
import com.caoccao.javet.utils.StringUtils;

public class Ts2JavaAstClassDecl
        extends BaseTs2JavaAstWithBuilderStore<Swc4jAstClassDecl>
        implements ITs2JavaAstDecl<Swc4jAstClassDecl> {
    protected final Ts2JavaAstClass clazz;
    protected final String packageName;

    public Ts2JavaAstClassDecl(
            Ts2JavaDynamicTypeBuilderStore builderStore,
            Swc4jAstClassDecl ast,
            String packageName) {
        super(builderStore, ast);
        clazz = new Ts2JavaAstClass(builderStore, ast.getClazz());
        this.packageName = packageName;
    }

    @Override
    public void compile() {
        String className = StringUtils.isEmpty(packageName)
                ? ast.getIdent().getSym()
                : packageName + "." + ast.getIdent().getSym();
        builderStore.setBuilder(builderStore.getBuilder().name(className));
        clazz.compile();
    }

    public Ts2JavaAstClass getClazz() {
        return clazz;
    }

    public String getPackageName() {
        return packageName;
    }
}