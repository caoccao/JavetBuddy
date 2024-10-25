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

package com.caoccao.javet.buddy.ts2java;

import com.caoccao.javet.swc4j.Swc4j;
import com.caoccao.javet.swc4j.ast.program.Swc4jAstScript;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstClassDecl;
import com.caoccao.javet.swc4j.enums.Swc4jMediaType;
import com.caoccao.javet.swc4j.enums.Swc4jParseMode;
import com.caoccao.javet.swc4j.exceptions.Swc4jCoreException;
import com.caoccao.javet.swc4j.options.Swc4jParseOptions;
import com.caoccao.javet.swc4j.outputs.Swc4jParseOutput;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;

import java.io.IOException;
import java.util.Objects;

public class Ts2Java {
    protected final static Swc4j swc4j = new Swc4j();
    protected final static Swc4jParseOptions swc4jParseOptions = new Swc4jParseOptions()
            .setMediaType(Swc4jMediaType.TypeScript)
            .setParseMode(Swc4jParseMode.Script)
            .setCaptureAst(true);
    protected final String packageName;
    protected final String tsCode;
    protected Class<?> javaClass;

    public Ts2Java(String packageName, String tsCode) {
        javaClass = null;
        this.packageName = packageName;
        this.tsCode = Objects.requireNonNull(tsCode);
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void transpile() throws Swc4jCoreException, IOException {
        Swc4jParseOutput output = swc4j.parse(getTsCode(), swc4jParseOptions);
        Swc4jAstScript script = (Swc4jAstScript) output.getProgram();
        if (script == null) {
            throw new IOException("The TypeScript code must be a script, not a module.");
        }
        if (script.getBody().isEmpty()) {
            throw new IOException("The TypeScript code must contain at least one statement.");
        }
        if (!(script.getBody().get(0) instanceof Swc4jAstClassDecl)) {
            throw new IOException("The first statement of the TypeScript code must be a class declaration.");
        }
        DynamicType.Builder<?> builder = new ByteBuddy()
                .subclass(Object.class, ConstructorStrategy.Default.DEFAULT_CONSTRUCTOR);
        Swc4jAstClassDecl classDecl = (Swc4jAstClassDecl) script.getBody().get(0);
        builder = new Ts2JavaAstClassDecl(getPackageName()).transpile(builder, classDecl);
        try (DynamicType.Unloaded<?> unloadedType = builder.make()) {
            javaClass = unloadedType.load(getClass().getClassLoader()).getLoaded();
        }
    }
}
