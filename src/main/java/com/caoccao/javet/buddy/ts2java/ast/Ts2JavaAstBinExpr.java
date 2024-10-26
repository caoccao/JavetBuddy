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

import com.caoccao.javet.buddy.ts2java.Ts2JavaException;
import com.caoccao.javet.buddy.ts2java.compiler.JavaByteCodeOpLoad;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public final class Ts2JavaAstBinExpr implements ITs2JavaAstStackManipulation<Swc4jAstBinExpr> {
    @Override
    public void manipulate(JavaFunctionContext functionContext, Swc4jAstBinExpr ast) {
        StackManipulation stackManipulation = new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            int stackSize = 0;
            for (ISwc4jAstExpr expr : new ISwc4jAstExpr[]{ast.getLeft(), ast.getRight()}) {
                switch (expr.getType()) {
                    case Ident:
                        String name = Ts2JavaAstIdent.getSym(expr.as(Swc4jAstIdent.class));
                        stackSize += JavaByteCodeOpLoad.generate(functionContext, name, methodVisitor);
                        break;
                    default:
                        throw new Ts2JavaException(
                                SimpleFreeMarkerFormat.format("BinExpr expr type ${exprType} is not supported",
                                        SimpleMap.of("exprType", expr.getType().name())));
                }
            }
            switch (ast.getOp()) {
                case Add:
                    methodVisitor.visitInsn(Opcodes.IADD);
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("BinExpr op ${op} is not supported",
                                    SimpleMap.of("op", ast.getOp().name())));
            }
            return new StackManipulation.Size(stackSize, 0);
        });
        functionContext.getStackManipulations().add(stackManipulation);
    }
}
