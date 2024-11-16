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

import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstParam;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstPat;
import com.caoccao.javet.swc4j.ast.pat.Swc4jAstBindingIdent;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;

public final class Ts2JavaAstParam {
    private Ts2JavaAstParam() {
    }

    public static JavaLocalVariable getLocalVariable(Swc4jAstParam ast) {
        ISwc4jAstPat pat = ast.getPat();
        switch (pat.getType()) {
            case BindingIdent:
                String ident = pat.as(Swc4jAstBindingIdent.class).getId().getSym();
                TypeDescription typeDescription =
                        Ts2JavaAstBindingIdent.getTypeDescription(pat.as(Swc4jAstBindingIdent.class));
                return new JavaLocalVariable(ident, typeDescription);
            default:
                throw new Ts2JavaAstException(
                        pat,
                        SimpleFreeMarkerFormat.format("Param pat type ${patType} is not supported.",
                                SimpleMap.of("patType", pat.getType().name())));
        }
    }
}
