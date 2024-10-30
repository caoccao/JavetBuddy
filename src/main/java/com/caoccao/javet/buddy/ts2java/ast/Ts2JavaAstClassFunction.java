/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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

import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.List;
import java.util.Objects;

public final class Ts2JavaAstClassFunction implements ITs2JavaAstTranspile<Swc4jAstFunction> {
    private final boolean _static;
    private final Swc4jAstAccessibility accessibility;
    private final String name;

    public Ts2JavaAstClassFunction(String name, boolean _static, Swc4jAstAccessibility accessibility) {
        this._static = _static;
        this.name = Objects.requireNonNull(name);
        this.accessibility = Objects.requireNonNull(accessibility);
    }

    public Swc4jAstAccessibility getAccessibility() {
        return accessibility;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return _static;
    }

    @Override
    public DynamicType.Builder<?> transpile(
            DynamicType.Builder<?> builder,
            Swc4jAstFunction ast) {
        final Visibility visibility = Ts2JavaAstAccessibility.getVisibility(accessibility);
        final TypeDescription returnType = ast.getReturnType()
                .map(Ts2JavaAstTsTypeAnn::getTypeDescription)
                .orElse(TypeDescription.ForLoadedType.of(Object.class));
        final JavaFunctionContext functionContext = new JavaFunctionContext(_static, returnType);
        ast.getParams().stream()
                .map(Ts2JavaAstParam::getLocalVariable)
                .forEach(functionContext::addLocalVariable);
        final List<TypeDescription> parameters = functionContext.getParameters();
        final int initialOffset = functionContext.getMaxOffset();
        functionContext.pushStackFrame();
        ast.getBody().ifPresent(blockStmt -> new Ts2JavaAstBlockStmt().manipulate(functionContext, blockStmt));
        final StackManipulation[] stackManipulations =
                functionContext.getStackManipulations().toArray(new StackManipulation[0]);
        builder = builder.defineMethod(name, returnType, visibility)
                .withParameters(parameters.toArray(new TypeDescription[0]))
                .intercept(Implementation.Simple.of(
                        (implementationTarget, instrumentedMethod) -> new StackManipulation.Compound(stackManipulations),
                        functionContext.getMaxOffset() - initialOffset));
        return builder;
    }
}
