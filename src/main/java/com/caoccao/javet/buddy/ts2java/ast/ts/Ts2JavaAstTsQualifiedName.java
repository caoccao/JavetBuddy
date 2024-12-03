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
import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstIdentName;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsEntityName;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsQualifiedName;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstTsQualifiedName
        extends BaseTs2JavaAst<Swc4jAstTsQualifiedName, Ts2JavaMemoFunction>
        implements ITs2JavaAstTsEntityName<Swc4jAstTsQualifiedName, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstTsEntityName<?, ?> left;
    protected final Ts2JavaAstIdentName right;

    public Ts2JavaAstTsQualifiedName(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstTsQualifiedName ast,
            TypeDescription type,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        left = ITs2JavaAstTsEntityName.create(parent, ast.getLeft(), memo);
        right = Ts2JavaAstIdentName.create(parent, ast.getRight(), type, memo);
        this.type = type;
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    public ITs2JavaAstTsEntityName<?, ?> getLeft() {
        return left;
    }

    public Ts2JavaAstIdentName getRight() {
        return right;
    }

    @Override
    public String getTypeName() {
        return left.getTypeName() + "." + right.getSym();
    }
}
