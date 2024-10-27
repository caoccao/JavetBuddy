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

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

public final class JavaByteCodeMethodVariableAccess {
    private JavaByteCodeMethodVariableAccess() {
    }

    private static JavaStackObject getStackObject(JavaFunctionContext functionContext, String name) {
        final int size = functionContext.getStackFrames().size();
        JavaStackObject stackObject;
        int stackFrameIndex;
        for (stackFrameIndex = size - 1; stackFrameIndex >= 0; stackFrameIndex--) {
            JavaStackFrame stackFrame = functionContext.getStackFrames().get(stackFrameIndex);
            stackObject = stackFrame.getObjectMap().get(name);
            if (stackObject != null) {
                return stackObject;
            }
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("The variable ${name} is not defined",
                        SimpleMap.of("name", name)));
    }

    public static void load(JavaFunctionContext functionContext, String name) {
        JavaStackObject stackObject = getStackObject(functionContext, name);
        MethodVariableAccess methodVariableAccess =
                MethodVariableAccess.of(TypeDescription.ForLoadedType.of(stackObject.getType()));
        functionContext.getStackManipulations().add(methodVariableAccess.loadFrom(stackObject.getIndex()));
    }

    public static void store(JavaFunctionContext functionContext, String name) {
        JavaStackObject stackObject = getStackObject(functionContext, name);
        MethodVariableAccess methodVariableAccess =
                MethodVariableAccess.of(TypeDescription.ForLoadedType.of(stackObject.getType()));
        functionContext.getStackManipulations().add(methodVariableAccess.storeAt(stackObject.getIndex()));
    }
}
