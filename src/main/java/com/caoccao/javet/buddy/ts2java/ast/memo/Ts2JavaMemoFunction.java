/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.ast.memo;

import com.caoccao.javet.buddy.ts2java.compiler.JavaLexicalScope;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleList;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;

import java.util.List;
import java.util.stream.Collectors;

public class Ts2JavaMemoFunction extends Ts2JavaMemoDynamicType {
    protected final List<JavaLexicalScope> lexicalScopes;
    protected boolean _static;
    protected int lineNumber;
    protected int maxOffset;
    protected int nextOffset;
    protected TypeDescription returnType;

    public Ts2JavaMemoFunction() {
        super(null);
        _static = false;
        lexicalScopes = SimpleList.of(new JavaLexicalScope(0));
        lineNumber = -1;
        maxOffset = nextOffset = _static ? 0 : 1;
        returnType = TypeDescription.ForLoadedType.of(void.class);
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

    public int getLineNumber() {
        return lineNumber;
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

    public boolean isStatic() {
        return _static;
    }

    public Ts2JavaMemoFunction popLexicalScope() {
        JavaLexicalScope lexicalScope = lexicalScopes.remove(lexicalScopes.size() - 1);
        nextOffset -= lexicalScope.getLocalVariables().stream()
                .mapToInt(v -> v.getType().getStackSize().getSize())
                .sum();
        return this;
    }

    public Ts2JavaMemoFunction pushLexicalScope() {
        lexicalScopes.add(new JavaLexicalScope(lexicalScopes.size()));
        return this;
    }

    public Ts2JavaMemoFunction setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public Ts2JavaMemoFunction setReturnType(TypeDescription returnType) {
        this.returnType = returnType;
        return this;
    }

    public Ts2JavaMemoFunction setStatic(boolean _static) {
        this._static = _static;
        return this;
    }
}
