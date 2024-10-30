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
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public final class JavaFunctionContext {
    private final boolean _static;
    private final TypeDescription returnType;
    private final List<JavaStackFrame> stackFrames;
    private final List<StackManipulation> stackManipulations;

    public JavaFunctionContext(boolean _static, List<JavaStackFrame> stackFrames, TypeDescription returnType) {
        this._static = _static;
        this.stackFrames = Objects.requireNonNull(stackFrames);
        this.returnType = Objects.requireNonNull(returnType);
        this.stackManipulations = new ArrayList<>();
    }

    public TypeDescription[] getParameters() {
        return stackFrames.get(0).getObjects().stream()
                .map(JavaStackObject::getType)
                .toArray(TypeDescription[]::new);
    }

    public TypeDescription getReturnType() {
        return returnType;
    }

    public int getStackDepth(int stackFrameIndex) {
        if (stackFrameIndex < 0) {
            return 0;
        }
        return IntStream.range(0, stackFrameIndex)
                .map(i -> stackFrames.get(i).getObjects().size())
                .sum();
    }

    public List<JavaStackFrame> getStackFrames() {
        return stackFrames;
    }

    public List<StackManipulation> getStackManipulations() {
        return stackManipulations;
    }

    public JavaStackObject getStackObject(String name) {
        for (int stackFrameIndex = stackFrames.size() - 1; stackFrameIndex >= 0; stackFrameIndex--) {
            JavaStackFrame stackFrame = stackFrames.get(stackFrameIndex);
            JavaStackObject stackObject = stackFrame.getObjectMap().get(name);
            if (stackObject != null) {
                return stackObject;
            }
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("The variable ${name} is not defined",
                        SimpleMap.of("name", name)));
    }

    public boolean isStatic() {
        return _static;
    }

    public void syncOffset() {
        int offset = _static ? 0 : 1;
        for (JavaStackFrame stackFrame : stackFrames) {
            for (JavaStackObject stackObject : stackFrame.getObjects()) {
                stackObject.setOffset(offset);
                offset += stackObject.getType().getStackSize().getSize();
            }
        }
    }
}