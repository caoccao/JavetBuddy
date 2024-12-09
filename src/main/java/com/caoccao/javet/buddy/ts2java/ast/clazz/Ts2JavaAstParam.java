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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstPat;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.pat.Ts2JavaAstBindingIdent;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstParam;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public class Ts2JavaAstParam
        extends BaseTs2JavaAst<Swc4jAstParam, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstPat<?, ?> pat;

    public Ts2JavaAstParam(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstParam ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        pat = ITs2JavaAstPat.create(this, ast.getPat(), memo);
    }

    public static Ts2JavaAstParam create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstParam ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstParam(parent, ast, memo);
    }

    @Override
    public void compile() {
        pat.compile();
    }

    public JavaLocalVariable getLocalVariable() {
        if (pat instanceof Ts2JavaAstBindingIdent) {
            return pat.as(Ts2JavaAstBindingIdent.class).getLocalVariable();
        }
        throw new Ts2JavaAstException(
                ast.getPat(),
                SimpleFreeMarkerFormat.format("Param pat type ${patType} is not supported.",
                        SimpleMap.of("patType", ast.getPat().getType().name())));
    }

    public ITs2JavaAstPat<?, ?> getPat() {
        return pat;
    }
}
