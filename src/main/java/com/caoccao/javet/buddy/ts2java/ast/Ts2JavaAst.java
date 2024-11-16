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

import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;

public final class Ts2JavaAst {
    private Ts2JavaAst() {
    }

    public static void manipulateLineNumber(JavaFunctionContext functionContext, ISwc4jAst ast) {
        final int lineNumber = ast.getSpan().getLine();
        if (functionContext.getLineNumber() < lineNumber) {
            StackManipulation stackManipulation = new StackManipulation.Simple((
                    MethodVisitor methodVisitor,
                    Implementation.Context context) -> {
                Label label = new Label();
                methodVisitor.visitLabel(label);
                methodVisitor.visitLineNumber(lineNumber, label);
                return StackManipulation.Size.ZERO;
            });
            functionContext.getStackManipulations().add(stackManipulation);
            functionContext.setLineNumber(lineNumber);
        }
    }
}
