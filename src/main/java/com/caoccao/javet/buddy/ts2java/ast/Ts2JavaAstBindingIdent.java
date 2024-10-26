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

import com.caoccao.javet.swc4j.ast.pat.Swc4jAstBindingIdent;

public final class Ts2JavaAstBindingIdent {
    private Ts2JavaAstBindingIdent() {
    }

    public static Class<?> getClass(Swc4jAstBindingIdent ast) {
        return ast.getTypeAnn()
                .map(Ts2JavaAstTsTypeAnn::getClass)
                .orElse((Class) Object.class);
    }

    public static String getIdent(Swc4jAstBindingIdent ast) {
        return Ts2JavaAstIdent.getSym(ast.getId());
    }
}
