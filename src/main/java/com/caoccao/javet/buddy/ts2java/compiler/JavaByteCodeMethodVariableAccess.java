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

package com.caoccao.javet.buddy.ts2java.compiler;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

public final class JavaByteCodeMethodVariableAccess {
    private JavaByteCodeMethodVariableAccess() {
    }

    public static StackManipulation load(JavaStackObject stackObject) {
        return MethodVariableAccess.of(TypeDescription.ForLoadedType.of(stackObject.getType()))
                .loadFrom(stackObject.getIndex());
    }

    public static StackManipulation store(JavaStackObject stackObject) {
        return MethodVariableAccess.of(TypeDescription.ForLoadedType.of(stackObject.getType()))
                .storeAt(stackObject.getIndex());
    }
}
