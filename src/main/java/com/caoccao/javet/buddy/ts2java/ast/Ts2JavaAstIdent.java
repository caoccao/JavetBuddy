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

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.compiler.JavaByteCodeHint;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

public final class Ts2JavaAstIdent implements ITs2JavaAstStackManipulation<Swc4jAstIdent> {
    @Override
    public JavaByteCodeHint manipulate(JavaFunctionContext functionContext, Swc4jAstIdent ast) {
        Ts2JavaAst.manipulateLineNumber(functionContext, ast);
        String name = ast.getSym();
        JavaLocalVariable localVariable = functionContext.getLocalVariable(name);
        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(localVariable.getType());
        StackManipulation stackManipulation = methodVariableAccess.loadFrom(localVariable.getOffset());
        functionContext.getStackManipulations().add(stackManipulation);
        return new JavaByteCodeHint(localVariable.getType());
    }
}
