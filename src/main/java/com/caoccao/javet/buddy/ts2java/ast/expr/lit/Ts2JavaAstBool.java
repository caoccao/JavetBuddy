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

package com.caoccao.javet.buddy.ts2java.ast.expr.lit;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstLit;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsLit;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBangFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstBool;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstBool
        extends BaseTs2JavaAst<Swc4jAstBool, Ts2JavaMemoFunction>
        implements ITs2JavaBangFlippable,
        ITs2JavaAstLit<Swc4jAstBool, Ts2JavaMemoFunction>, ITs2JavaAstTsLit<Swc4jAstBool, Ts2JavaMemoFunction> {
    protected boolean value;

    public Ts2JavaAstBool(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBool ast,
            TypeDescription type,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        if (!type.represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Bool type ${type} is not supported.",
                            SimpleMap.of("type", type.getName())));
        }
        value = ast.isValue();
        this.type = TypeDescription.ForLoadedType.of(boolean.class);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        StackManipulation stackManipulation = IntegerConstant.forValue(value ? 1 : 0);
        return stackManipulation.apply(methodVisitor, context);
    }

    @Override
    public void compile() {
    }

    @Override
    public void flipBang() {
        value = !value;
    }

    public boolean isValue() {
        return value;
    }
}
