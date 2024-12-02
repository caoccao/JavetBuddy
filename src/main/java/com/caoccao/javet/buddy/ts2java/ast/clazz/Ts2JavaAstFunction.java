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

package com.caoccao.javet.buddy.ts2java.ast.clazz;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaAstAccessibility;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaAstParam;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaAstTsTypeAnn;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.stmt.Ts2JavaAstBlockStmt;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstFunction
        extends BaseTs2JavaAst<Swc4jAstFunction, Ts2JavaMemoDynamicType> {
    protected final Swc4jAstAccessibility accessibility;
    protected final Optional<Ts2JavaAstBlockStmt> body;
    protected final Ts2JavaMemoFunction memoFunction;
    protected final String name;

    public Ts2JavaAstFunction(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstFunction ast,
            Ts2JavaMemoDynamicType memo,
            String name,
            boolean _static,
            Swc4jAstAccessibility accessibility) {
        super(parent, ast, memo);
        this.accessibility = accessibility;
        type = ast.getReturnType()
                .map(Ts2JavaAstTsTypeAnn::getTypeDescription)
                .orElse(TypeDescription.ForLoadedType.of(void.class));
        memoFunction = new Ts2JavaMemoFunction()
                .setStatic(_static)
                .setReturnType(type);
        this.name = name;
        body = ast.getBody().map(stmt -> new Ts2JavaAstBlockStmt(this, stmt, memoFunction));
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        if (body.isPresent()) {
            return body.get().apply(methodVisitor, context);
        }
        return Size.ZERO;
    }

    @Override
    public void compile() {
        final Visibility visibility = Ts2JavaAstAccessibility.getVisibility(accessibility);
        ast.getParams().stream()
                .map(Ts2JavaAstParam::getLocalVariable)
                .forEach(memoFunction::addLocalVariable);
        final TypeList parameters = memoFunction.getParameters();
        final int initialOffset = memoFunction.getMaxOffset();
        memoFunction.pushLexicalScope();
        body.ifPresent(Ts2JavaAstBlockStmt::compile);
        if (type.represents(void.class) && body.isPresent()) {
            TypeDescription bodyType = body.get().getType();
            if (bodyType != null) {
                type = bodyType;
            }
        }
        memo.setBuilder(memo.getBuilder().defineMethod(name, type, visibility)
                .withParameters(parameters.toArray(new TypeDescription[0]))
                .intercept(Implementation.Simple.of(
                        (implementationTarget, instrumentedMethod) -> new StackManipulation.Simple(this::apply),
                        memoFunction.getMaxOffset() - initialOffset)));
    }

    public Swc4jAstAccessibility getAccessibility() {
        return accessibility;
    }

    public Optional<Ts2JavaAstBlockStmt> getBody() {
        return body;
    }

    public Ts2JavaMemoFunction getMemoFunction() {
        return memoFunction;
    }

    public String getName() {
        return name;
    }
}
