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
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public final class Ts2JavaAstUnaryExpr implements ITs2JavaAstStackManipulation<Swc4jAstUnaryExpr> {
    private int getOpcodeNegative(Swc4jAstUnaryExpr ast, TypeDescription type) {
        if (type.represents(int.class)
                || type.represents(byte.class)
                || type.represents(char.class)
                || type.represents(short.class)) {
            return Opcodes.INEG;
        } else if (type.represents(long.class)) {
            return Opcodes.LNEG;
        } else if (type.represents(float.class)) {
            return Opcodes.FNEG;
        } else if (type.represents(double.class)) {
            return Opcodes.DNEG;
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Minus cannot be applied to type ${type}.",
                            SimpleMap.of("type", type.getName())));
        }
    }

    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstUnaryExpr ast) {
        TypeDescription returnType;
        ISwc4jAstExpr arg = ast.getArg().unParenExpr();
        switch (ast.getOp()) {
            case Bang: {
                functionContext.increaseBangCount();
                switch (arg.getType()) {
                    case BinExpr:
                        returnType = new Ts2JavaAstBinExpr()
                                .manipulate(functionContext, arg.as(Swc4jAstBinExpr.class));
                        break;
                    case Ident:
                        returnType = new Ts2JavaAstIdent()
                                .manipulate(functionContext, arg.as(Swc4jAstIdent.class));
                        break;
                    case UnaryExpr:
                        returnType = new Ts2JavaAstUnaryExpr()
                                .manipulate(functionContext, arg.as(Swc4jAstUnaryExpr.class));
                        break;
                    default:
                        throw new Ts2JavaAstException(
                                arg,
                                SimpleFreeMarkerFormat.format("UnaryExpr arg type ${argType} for ! is not supported.",
                                        SimpleMap.of("argType", arg.getType().name())));
                }
                functionContext.decreateBangCount();
                break;
            }
            case Minus: {
                boolean opcodeNegativeRequired = true;
                switch (arg.getType()) {
                    case BinExpr:
                        returnType = new Ts2JavaAstBinExpr()
                                .manipulate(functionContext, arg.as(Swc4jAstBinExpr.class));
                        break;
                    case Ident:
                        returnType = new Ts2JavaAstIdent()
                                .manipulate(functionContext, arg.as(Swc4jAstIdent.class));
                        break;
                    case Number:
                        opcodeNegativeRequired = false;
                        returnType = new Ts2JavaAstNumber()
                                .setNegative(true)
                                .manipulate(functionContext, arg.as(Swc4jAstNumber.class));
                        break;
                    case UnaryExpr:
                        returnType = new Ts2JavaAstUnaryExpr()
                                .manipulate(functionContext, arg.as(Swc4jAstUnaryExpr.class));
                        break;
                    default:
                        throw new Ts2JavaAstException(
                                arg,
                                SimpleFreeMarkerFormat.format("UnaryExpr arg type ${argType} for - is not supported.",
                                        SimpleMap.of("argType", arg.getType().name())));
                }
                if (opcodeNegativeRequired) {
                    final int opcode = getOpcodeNegative(ast, returnType);
                    StackManipulation stackManipulation = new StackManipulation.Simple((
                            MethodVisitor methodVisitor,
                            Implementation.Context implementationContext) -> {
                        methodVisitor.visitInsn(opcode);
                        return StackManipulation.Size.ZERO;
                    });
                    functionContext.getStackManipulations().add(stackManipulation);
                }
                break;
            }
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("UnaryExpr op ${op} is not supported.",
                                SimpleMap.of("op", ast.getOp().name())));
        }
        return returnType;
    }
}
