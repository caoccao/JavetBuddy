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
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.bytecode.StackManipulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class JavaFunctionContext {
    private final boolean _static;
    private final List<JavaLexicalScope> lexicalScopes;
    private final TypeDescription returnType;
    private final List<StackManipulation> stackManipulations;
    private int maxOffset;
    private int nextOffset;

    public JavaFunctionContext(boolean _static, TypeDescription returnType) {
        this._static = _static;
        nextOffset = _static ? 0 : 1;
        maxOffset = nextOffset;
        this.lexicalScopes = SimpleList.of(new JavaLexicalScope(0));
        this.returnType = Objects.requireNonNull(returnType);
        this.stackManipulations = new ArrayList<>();
    }

    public void addLocalVariable(JavaLocalVariable localVariable) {
        JavaLexicalScope lexicalScope = lexicalScopes.get(lexicalScopes.size() - 1);
        lexicalScope.putLocalVariable(localVariable);
        localVariable.setOffset(nextOffset);
        nextOffset += localVariable.getType().getStackSize().getSize();
        if (nextOffset > maxOffset) {
            maxOffset = nextOffset;
        }
    }

    public void addStackManipulation(StackManipulation stackManipulation) {
        stackManipulations.add(stackManipulation);
    }

    public JavaLocalVariable getLocalVariable(String name) {
        for (int lexicalScopeIndex = lexicalScopes.size() - 1; lexicalScopeIndex >= 0; lexicalScopeIndex--) {
            JavaLexicalScope lexicalScope = lexicalScopes.get(lexicalScopeIndex);
            JavaLocalVariable localVariable = lexicalScope.getLocalVariable(name);
            if (localVariable != null) {
                return localVariable;
            }
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("The variable ${name} is not defined.",
                        SimpleMap.of("name", name)));
    }

    public int getMaxOffset() {
        return maxOffset;
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public TypeList getParameters() {
        return new TypeList.Explicit(
                lexicalScopes.get(0).getLocalVariables().stream()
                        .map(JavaLocalVariable::getType)
                        .collect(Collectors.toList()));
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

    public void popLexicalScope() {
        JavaLexicalScope lexicalScope = lexicalScopes.remove(lexicalScopes.size() - 1);
        nextOffset -= lexicalScope.getLocalVariables().stream()
                .mapToInt(v -> v.getType().getStackSize().getSize())
                .sum();
    }

    public void pushLexicalScope() {
        lexicalScopes.add(new JavaLexicalScope(lexicalScopes.size()));
    }
}
