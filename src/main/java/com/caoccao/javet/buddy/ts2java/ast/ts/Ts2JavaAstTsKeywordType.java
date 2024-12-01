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

package com.caoccao.javet.buddy.ts2java.ast.ts;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsType;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstTsKeywordTypeKind;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsKeywordType;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Map;

public class Ts2JavaAstTsKeywordType
        extends BaseTs2JavaAst<Swc4jAstTsKeywordType, Ts2JavaMemoFunction>
        implements ITs2JavaAstTsType<Swc4jAstTsKeywordType, Ts2JavaMemoFunction> {
    protected static final Map<String, TypeDescription> TS_KEYWORD_TYPE_MAP = SimpleMap.of(
            "boolean", TypeDescription.ForLoadedType.of(boolean.class),
            "string", TypeDescription.ForLoadedType.of(String.class));

    protected final Swc4jAstTsKeywordTypeKind kind;

    public Ts2JavaAstTsKeywordType(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstTsKeywordType ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        kind = ast.getKind();
        type = TS_KEYWORD_TYPE_MAP.get(ast.getKind().getName());
        if (type == null) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Ts keyword type kind ${kind} is not supported.",
                            SimpleMap.of("kind", ast.getKind().getName())));
        }
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }
}
