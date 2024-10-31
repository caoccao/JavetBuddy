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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JavaLexicalScope {
    private final int index;
    private final Map<String, JavaLocalVariable> localVariableMap;

    public JavaLexicalScope(int index) {
        this.index = index;
        localVariableMap = new LinkedHashMap<>();
    }

    public int getIndex() {
        return index;
    }

    public JavaLocalVariable getLocalVariable(String name) {
        return localVariableMap.get(name);
    }

    public List<JavaLocalVariable> getLocalVariables() {
        return new ArrayList<>(localVariableMap.values());
    }

    public boolean hasLocalVariable(String name) {
        return localVariableMap.containsKey(name);
    }

    public void putLocalVariable(JavaLocalVariable localVariable) {
        localVariableMap.put(localVariable.getName(), localVariable);
    }
}
