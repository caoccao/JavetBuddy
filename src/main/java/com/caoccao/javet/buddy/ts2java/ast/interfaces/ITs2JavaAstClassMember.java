/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.ast.interfaces;

import com.caoccao.javet.buddy.ts2java.ast.clazz.Ts2JavaAstClassMethod;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClassMethod;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstClassMember;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public interface ITs2JavaAstClassMember<AST extends ISwc4jAstClassMember, Memo extends Ts2JavaMemoDynamicType>
        extends ITs2JavaAst<AST, Memo> {
    static ITs2JavaAstClassMember<?, ?> create(
            ITs2JavaAst<?, ?> parent,
            ISwc4jAstClassMember ast,
            Ts2JavaMemoDynamicType memo) {
        switch (ast.getType()) {
            case ClassMethod:
                return Ts2JavaAstClassMethod.create(parent, ast.as(Swc4jAstClassMethod.class), memo);
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("ClassMember type ${type} is not supported.",
                                SimpleMap.of("type", ast.getType().name())));
        }
    }
}
