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

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;

import java.util.Objects;

public abstract class BaseTs2JavaAst<AST extends ISwc4jAst, Memo extends Ts2JavaMemo>
        implements ITs2JavaAst<AST, Memo> {
    protected AST ast;
    protected Memo memo;
    protected ITs2JavaAst<?, ?> parent;

    public BaseTs2JavaAst(ITs2JavaAst<?, ?> parent, AST ast, Memo memo) {
        this.ast = Objects.requireNonNull(ast);
        this.memo = Objects.requireNonNull(memo);
        this.parent = parent;
    }

    @Override
    public AST getAst() {
        return ast;
    }

    @Override
    public Memo getMemo() {
        return memo;
    }

    public ITs2JavaAst<?, ?> getParent() {
        return parent;
    }
}
