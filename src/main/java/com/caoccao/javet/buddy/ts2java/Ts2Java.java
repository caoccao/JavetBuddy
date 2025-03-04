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

package com.caoccao.javet.buddy.ts2java;

import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoDynamicType;
import com.caoccao.javet.buddy.ts2java.ast.stmt.Ts2JavaAstClassDecl;
import com.caoccao.javet.buddy.ts2java.compiler.visitors.JavaLoggingMethodVisitor;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.Swc4j;
import com.caoccao.javet.swc4j.ast.program.Swc4jAstModule;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstClassDecl;
import com.caoccao.javet.swc4j.enums.Swc4jMediaType;
import com.caoccao.javet.swc4j.enums.Swc4jParseMode;
import com.caoccao.javet.swc4j.exceptions.Swc4jCoreException;
import com.caoccao.javet.swc4j.options.Swc4jParseOptions;
import com.caoccao.javet.swc4j.outputs.Swc4jParseOutput;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ts2Java {
    protected final static Swc4j swc4j = new Swc4j();
    protected final static Swc4jParseOptions swc4jParseOptions = new Swc4jParseOptions()
            .setMediaType(Swc4jMediaType.TypeScript)
            .setParseMode(Swc4jParseMode.Module)
            .setCaptureAst(true);
    protected static boolean logging = false;
    protected final String packageName;
    protected final String tsCode;
    protected List<Class<?>> classes;

    public Ts2Java(String packageName, String tsCode) {
        classes = new ArrayList<>();
        this.packageName = packageName;
        this.tsCode = Objects.requireNonNull(tsCode);
    }

    public static void disableLogging() {
        logging = false;
    }

    public static void enableLogging() {
        logging = true;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void transpile() throws Swc4jCoreException {
        classes.clear();
        Swc4jParseOutput output = swc4j.parse(getTsCode(), swc4jParseOptions);
        Swc4jAstModule module = (Swc4jAstModule) output.getProgram();
        if (module == null) {
            throw new Ts2JavaException("The TypeScript code must be a module, not a script.");
        }
        if (module.getBody().isEmpty()) {
            throw new Ts2JavaException("The TypeScript code must contain at least one statement.");
        }
        List<Swc4jAstClassDecl> classDecls = module.getBody().stream()
                .filter(ast -> ast instanceof Swc4jAstClassDecl)
                .map(ast -> (Swc4jAstClassDecl) ast)
                .collect(Collectors.toList());
        if (classDecls.isEmpty()) {
            throw new Ts2JavaException("There must be at least one class declaration in the TypeScript code.");
        }
        for (Swc4jAstClassDecl classDecl : classDecls) {
            DynamicType.Builder<?> builder = new ByteBuddy()
                    .subclass(Object.class, ConstructorStrategy.Default.DEFAULT_CONSTRUCTOR);
            Ts2JavaAstClassDecl ts2JavaAstClassDecl = Ts2JavaAstClassDecl.create(
                    null,
                    classDecl,
                    new Ts2JavaMemoDynamicType(builder),
                    getPackageName());
            ts2JavaAstClassDecl.compile();
            ts2JavaAstClassDecl.syncLabels();
            if (logging) {
                ts2JavaAstClassDecl.apply(new JavaLoggingMethodVisitor(Opcodes.ASM9), null);
            }
            builder = ts2JavaAstClassDecl.getMemo().getBuilder();
            try (DynamicType.Unloaded<?> unloadedType = builder.make()) {
                classes.add(unloadedType.load(getClass().getClassLoader()).getLoaded());
            }
        }
    }
}
