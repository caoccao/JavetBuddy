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

import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstIdent;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.ts.Ts2JavaAstTsQualifiedName;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsEntityName;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsQualifiedName;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public interface ITs2JavaAstTsEntityName<AST extends ISwc4jAstTsEntityName, Memo extends Ts2JavaMemo>
        extends ITs2JavaAstTsModuleRef<AST, Memo>, ITs2JavaAstTsTypeQueryExpr<AST, Memo> {
    static ITs2JavaAstTsEntityName<?, ?> cast(
            ITs2JavaAst<?, ?> parent,
            ISwc4jAstTsEntityName ast,
            Ts2JavaMemoFunction memo) {
        switch (ast.getType()) {
            case Ident:
                return new Ts2JavaAstIdent(parent, ast.as(Swc4jAstIdent.class), null, memo);
            case TsQualifiedName:
                return new Ts2JavaAstTsQualifiedName(parent, ast.as(Swc4jAstTsQualifiedName.class), null, memo);
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Ts entity name ${type} is not supported.",
                                SimpleMap.of("type", ast.getType().name())));
        }
    }

    String getTypeName();
}
