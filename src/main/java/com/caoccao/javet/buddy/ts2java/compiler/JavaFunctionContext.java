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
import com.caoccao.javet.utils.SimpleList;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class JavaFunctionContext {
    private final boolean _static;
    private final TypeDescription returnType;
    private final List<JavaStackFrame> stackFrames;
    private final List<StackManipulation> stackManipulations;
    private int maxLocals;

    public JavaFunctionContext(boolean _static, TypeDescription returnType) {
        this._static = _static;
        maxLocals = _static ? 0 : 1;
        this.stackFrames = SimpleList.of(new JavaStackFrame(0));
        this.returnType = Objects.requireNonNull(returnType);
        this.stackManipulations = new ArrayList<>();
    }

    public void addLocalVariable(JavaLocalVariable localVariable) {
        JavaStackFrame stackFrame = stackFrames.get(stackFrames.size() - 1);
        stackFrame.putLocalVariable(localVariable);
        localVariable.setOffset(maxLocals);
        maxLocals += localVariable.getType().getStackSize().toIncreasingSize().getSizeImpact();
    }

    public void addStackFrame() {
        stackFrames.add(new JavaStackFrame(stackFrames.size()));
    }

    public void addStackManipulation(StackManipulation stackManipulation) {
        stackManipulations.add(stackManipulation);
    }

    public JavaLocalVariable getLocalVariable(String name) {
        for (int stackFrameIndex = stackFrames.size() - 1; stackFrameIndex >= 0; stackFrameIndex--) {
            JavaStackFrame stackFrame = stackFrames.get(stackFrameIndex);
            JavaLocalVariable localVariable = stackFrame.getLocalVariable(name);
            if (localVariable != null) {
                return localVariable;
            }
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("The variable ${name} is not defined.",
                        SimpleMap.of("name", name)));
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public List<TypeDescription> getParameters() {
        return stackFrames.get(0).getLocalVariables().stream()
                .map(JavaLocalVariable::getType)
                .collect(Collectors.toList());
    }

    public TypeDescription getReturnType() {
        return returnType;
    }

    public List<StackManipulation> getStackManipulations() {
        return new ArrayList<>(stackManipulations);
    }

    public boolean isStatic() {
        return _static;
    }
}
