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
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClass;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClassMethod;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstClassMember;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class Ts2JavaAstClass
        extends BaseTs2JavaAstWithBuilderStore<Swc4jAstClass> {
    protected final List<ITs2JavaAstClassMember<?>> body;

    public Ts2JavaAstClass(Ts2JavaDynamicTypeBuilderStore builderStore, Swc4jAstClass ast) {
        super(builderStore, ast);
        body = new ArrayList<>();
        for (ISwc4jAstClassMember classMember : ast.getBody()) {
            switch (classMember.getType()) {
                case ClassMethod:
                    body.add(new Ts2JavaAstClassMethod(builderStore, classMember.as(Swc4jAstClassMethod.class)));
                    break;
                default:
                    throw new Ts2JavaAstException(
                            classMember,
                            SimpleFreeMarkerFormat.format("Class body type ${type} is not supported.",
                                    SimpleMap.of("type", classMember.getType().name())));
            }
        }
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        return null;
    }

    @Override
    public void compile() {
        body.forEach(ITs2JavaAstClassMember::compile);
    }

    public List<ITs2JavaAstClassMember<?>> getBody() {
        return body;
    }
}
