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

package com.caoccao.javet.buddy.ts2java.ast.interfaces;

import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.ts.Ts2JavaAstTsKeywordType;
import com.caoccao.javet.buddy.ts2java.ast.ts.Ts2JavaAstTsTypeRef;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsType;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsKeywordType;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeRef;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public interface ITs2JavaAstTsType<AST extends ISwc4jAstTsType, Memo extends Ts2JavaMemo>
        extends ITs2JavaAst<AST, Memo> {
    static ITs2JavaAstTsType<?, ?> cast(
            ITs2JavaAst<?, ?> parent,
            ISwc4jAstTsType ast,
            Ts2JavaMemoFunction memo) {
        switch (ast.getType()) {
            case TsKeywordType:
                return new Ts2JavaAstTsKeywordType(parent, ast.as(Swc4jAstTsKeywordType.class), memo);
            case TsTypeRef:
                return new Ts2JavaAstTsTypeRef(parent, ast.as(Swc4jAstTsTypeRef.class), memo);
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Ts type ${type} is not supported.",
                                SimpleMap.of("type", ast.getType().name())));
        }
    }
}
