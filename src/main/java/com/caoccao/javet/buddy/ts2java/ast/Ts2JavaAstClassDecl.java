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

import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstClassDecl;
import com.caoccao.javet.utils.StringUtils;
import net.bytebuddy.dynamic.DynamicType;

public final class Ts2JavaAstClassDecl implements ITs2JavaAstTranspile<Swc4jAstClassDecl> {
    private final String packageName;

    public Ts2JavaAstClassDecl(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public DynamicType.Builder<?> transpile(
            DynamicType.Builder<?> builder,
            Swc4jAstClassDecl ast) {
        String className = StringUtils.isEmpty(packageName)
                ? ast.getIdent().getSym()
                : packageName + "." + ast.getIdent().getSym();
        builder = builder.name(className);
        builder = new Ts2JavaAstClass().transpile(builder, ast.getClazz());
        return builder;
    }
}
