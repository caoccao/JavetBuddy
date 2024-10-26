/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.List;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    @Override
    public void manipulate(JavaFunctionContext functionContext, List<StackManipulation> stackManipulations, Swc4jAstBinExpr ast) {
        // TODO
        StackManipulation stackManipulation = new StackManipulation.Simple((
                MethodVisitor simpleMethodVisitor,
                Implementation.Context simpleImplementationContext) -> {
            simpleMethodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
            simpleMethodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
            simpleMethodVisitor.visitInsn(Opcodes.IADD);
            return new StackManipulation.Size(2, 0);
        });
        stackManipulations.add(stackManipulation);
    }
}
