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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstClassMember;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstEmptyStmt;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstEmptyStmt
        extends BaseTs2JavaAst<Swc4jAstEmptyStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstEmptyStmt, Ts2JavaMemoFunction>,
        ITs2JavaAstClassMember<Swc4jAstEmptyStmt, Ts2JavaMemoFunction> {
    protected Ts2JavaAstEmptyStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstEmptyStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
    }

    public static Ts2JavaAstEmptyStmt create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstEmptyStmt ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstEmptyStmt(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    @Override
    public void syncLabels() {
    }
}
