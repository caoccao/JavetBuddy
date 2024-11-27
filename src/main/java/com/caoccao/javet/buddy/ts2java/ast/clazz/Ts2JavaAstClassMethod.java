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

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAstWithBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.Ts2JavaDynamicTypeBuilderStore;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstClassMember;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClassMethod;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstMethodKind;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdentName;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

public class Ts2JavaAstClassMethod
        extends BaseTs2JavaAstWithBuilderStore<Swc4jAstClassMethod>
        implements ITs2JavaAstClassMember<Swc4jAstClassMethod> {
    protected final Ts2JavaAstFunction function;

    public Ts2JavaAstClassMethod(Ts2JavaDynamicTypeBuilderStore builderStore, Swc4jAstClassMethod ast) {
        super(builderStore, ast);
        if (ast.isStatic()) {
            throw new Ts2JavaAstException(
                    ast,
                    "Static method is not implemented");
        }
        if (ast.getKind() != Swc4jAstMethodKind.Method) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("ClassMethod kind ${kind} is not supported.",
                            SimpleMap.of("kind", ast.getKind().name())));
        }
        if (!(ast.getKey() instanceof Swc4jAstIdentName)) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("ClassMethod key type ${keyType} is not supported.",
                            SimpleMap.of("keyType", ast.getKey().getClass().getSimpleName())));

        }
        String name = ast.getKey().as(Swc4jAstIdentName.class).getSym();
        boolean _static = ast.isStatic();
        Swc4jAstAccessibility accessibility = ast.getAccessibility().orElse(Swc4jAstAccessibility.Public);
        function = new Ts2JavaAstFunction(builderStore, ast.getFunction(), name, _static, accessibility);
    }

    @Override
    public void compile() {
        function.compile();
    }

    public Ts2JavaAstFunction getFunction() {
        return function;
    }
}