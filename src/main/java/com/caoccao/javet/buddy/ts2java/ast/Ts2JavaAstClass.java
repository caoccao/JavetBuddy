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

import com.caoccao.javet.buddy.ts2java.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClass;
import com.caoccao.javet.swc4j.ast.clazz.Swc4jAstClassMethod;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstClassMember;
import net.bytebuddy.dynamic.DynamicType;

public final class Ts2JavaAstClass extends BaseTs2JavaAst<Swc4jAstClass> {
    @Override
    public DynamicType.Builder<?> transpile(
            DynamicType.Builder<?> builder,
            Swc4jAstClass ast)
            throws Ts2JavaException {
        for (ISwc4jAstClassMember classMember : ast.getBody()) {
            switch (classMember.getType()) {
                case ClassMethod:
                    builder = new Ts2JavaAstClassMethod().transpile(builder, classMember.as(Swc4jAstClassMethod.class));
                    break;
                default:
                    // TODO
                    break;
            }
        }
        return builder;
    }
}
