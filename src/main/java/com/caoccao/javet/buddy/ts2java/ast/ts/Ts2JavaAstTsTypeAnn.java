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
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeAnn;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstTsTypeAnn
        extends BaseTs2JavaAst<Swc4jAstTsTypeAnn, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstTsType<?, ?> typeAnn;

    public Ts2JavaAstTsTypeAnn(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstTsTypeAnn ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        typeAnn = ITs2JavaAstTsType.cast(this, ast.getTypeAnn(), memo);
        type = typeAnn.getType();
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    public ITs2JavaAstTsType<?, ?> getTypeAnn() {
        return typeAnn;
    }
}
