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
import com.caoccao.javet.buddy.ts2java.ast.enums.Ts2JavaAstAccessibility;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.ast.stmt.Ts2JavaAstBlockStmt;
import com.caoccao.javet.buddy.ts2java.ast.ts.Ts2JavaAstTsTypeAnn;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ts2JavaAstFunction
        extends BaseTs2JavaAst<Swc4jAstFunction, Ts2JavaMemoDynamicType> {
    protected final Swc4jAstAccessibility accessibility;
    protected final Optional<Ts2JavaAstBlockStmt> body;
    protected final Ts2JavaMemoFunction memoFunction;
    protected final String name;
    protected final List<Ts2JavaAstParam> params;
    protected final Optional<Ts2JavaAstTsTypeAnn> returnType;

    public Ts2JavaAstFunction(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstFunction ast,
            Ts2JavaMemoDynamicType memo,
            String name,
            boolean _static,
            Swc4jAstAccessibility accessibility) {
        super(parent, ast, memo);
        this.accessibility = accessibility;
        memoFunction = new Ts2JavaMemoFunction().setStatic(_static);
        returnType = ast.getReturnType().map(r -> Ts2JavaAstTsTypeAnn.create(this, r, memoFunction));
        type = returnType
                .map(Ts2JavaAstTsTypeAnn::getType)
                .orElse(TypeDescription.ForLoadedType.of(void.class));
        memoFunction.setReturnType(type);
        this.name = name;
        body = ast.getBody().map(stmt -> Ts2JavaAstBlockStmt.create(this, stmt, memoFunction));
        params = ast.getParams().stream()
                .map(param -> Ts2JavaAstParam.create(this, param, memoFunction))
                .collect(Collectors.toList());
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
        params.stream()
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

    public Optional<Ts2JavaAstTsTypeAnn> getReturnType() {
        return returnType;
    }

    @Override
    public void syncLabels() {
        body.ifPresent(Ts2JavaAstBlockStmt::syncLabels);
        params.forEach(Ts2JavaAstParam::syncLabels);
        returnType.ifPresent(Ts2JavaAstTsTypeAnn::syncLabels);
    }
}
