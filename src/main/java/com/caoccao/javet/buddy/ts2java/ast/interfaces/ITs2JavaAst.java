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

package com.caoccao.javet.buddy.ts2java.ast.interfaces;

import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;

public interface ITs2JavaAst<AST extends ISwc4jAst, Memo extends Ts2JavaMemo>
        extends StackManipulation {
    @Override
    default Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("${type} is not supported.",
                        SimpleMap.of("type", getClass().getSimpleName())));
    }

    void compile();

    AST getAst();

    Memo getMemo();

    TypeDescription getType();

    @Override
    default boolean isValid() {
        return true;
    }
}
