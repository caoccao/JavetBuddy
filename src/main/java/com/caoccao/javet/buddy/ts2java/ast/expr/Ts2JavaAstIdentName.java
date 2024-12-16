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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.*;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdentName;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstIdentName
        extends BaseTs2JavaAst<Swc4jAstIdentName, Ts2JavaMemoFunction>
        implements ITs2JavaAstSuperProp<Swc4jAstIdentName, Ts2JavaMemoFunction>,
        ITs2JavaAstPropName<Swc4jAstIdentName, Ts2JavaMemoFunction>,
        ITs2JavaAstMemberProp<Swc4jAstIdentName, Ts2JavaMemoFunction>,
        ITs2JavaAstJsxAttrName<Swc4jAstIdentName, Ts2JavaMemoFunction> {
    protected final String sym;

    protected Ts2JavaAstIdentName(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstIdentName ast,
            TypeDescription type,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        sym = ast.getSym();
        this.type = type;
    }

    public static Ts2JavaAstIdentName create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstIdentName ast,
            TypeDescription type,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstIdentName(parent, ast, type, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    public String getSym() {
        return sym;
    }

    @Override
    public void syncLabels() {
    }
}
