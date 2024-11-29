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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstClassMember;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClass;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class Ts2JavaAstClass
        extends BaseTs2JavaAst<Swc4jAstClass, Ts2JavaMemoDynamicType> {
    protected final List<ITs2JavaAstClassMember<?, ?>> body;

    public Ts2JavaAstClass(ITs2JavaAst<?, ?> parent, Swc4jAstClass ast, Ts2JavaMemoDynamicType memo) {
        super(parent, ast, memo);
        body = ast.getBody().stream()
                .map(classMember -> ITs2JavaAstClassMember.cast(this, classMember, memo))
                .collect(Collectors.toList());
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        return body.stream()
                .map((classMember) -> classMember.apply(methodVisitor, context))
                .reduce(BaseTs2JavaAst::aggregateSize)
                .orElse(Size.ZERO);
    }

    @Override
    public void compile() {
        body.forEach(ITs2JavaAstClassMember::compile);
    }

    public List<ITs2JavaAstClassMember<?, ?>> getBody() {
        return body;
    }
}
