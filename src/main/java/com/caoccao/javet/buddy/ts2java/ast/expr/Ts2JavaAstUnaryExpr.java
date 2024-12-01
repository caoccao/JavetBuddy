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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBangFlippable;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaMinusFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class Ts2JavaAstUnaryExpr
        extends BaseTs2JavaAst<Swc4jAstUnaryExpr, Ts2JavaMemoFunction>
        implements ITs2JavaBangFlippable, ITs2JavaMinusFlippable,
        ITs2JavaAstExpr<Swc4jAstUnaryExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> arg;
    protected Swc4jAstUnaryOp op;

    public Ts2JavaAstUnaryExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        arg = ITs2JavaAstExpr.cast(this, ast.getArg(), memo);
        op = ast.getOp();
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        Size size = arg.apply(methodVisitor, context);
        switch (op) {
            case Bang:
                if (!isBangFlippable()) {
                    final int opcode = getOpcodeNegative();
                    methodVisitor.visitInsn(opcode);
                }
                break;
            case Minus: {
                if (!isMinusFlippable()) {
                    final int opcode = getOpcodeNegative();
                    methodVisitor.visitInsn(opcode);
                }
                break;
            }
            case Plus:
                // There is no need to do anything.
                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Unary expr op ${op} is not supported.",
                                SimpleMap.of("op", op.name())));
        }
        return size;
    }

    @Override
    public void compile() {
        arg.compile();
        type = arg.getType();
        switch (op) {
            case Bang:
                if (arg instanceof ITs2JavaBangFlippable) {
                    arg.as(ITs2JavaBangFlippable.class).flipBang();
                }
                break;
            case Minus:
                if (arg instanceof ITs2JavaMinusFlippable) {
                    arg.as(ITs2JavaMinusFlippable.class).flipMinus();
                }
                break;
            case Plus:
                // There is no need to do anything.
                break;
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Unary expr op ${op} is not supported.",
                                SimpleMap.of("op", op.name())));
        }
    }

    @Override
    public void flipBang() {
        if (op == Swc4jAstUnaryOp.Bang && arg instanceof ITs2JavaBangFlippable) {
            arg.as(ITs2JavaBangFlippable.class).flipBang();
        }
    }

    @Override
    public void flipMinus() {
        if (op == Swc4jAstUnaryOp.Minus && arg instanceof ITs2JavaMinusFlippable) {
            arg.as(ITs2JavaMinusFlippable.class).flipMinus();
        }
    }

    public ITs2JavaAstExpr<?, ?> getArg() {
        return arg;
    }

    public Swc4jAstUnaryOp getOp() {
        return op;
    }

    protected int getOpcodeNegative() {
        if (arg.getType().represents(int.class)
                || arg.getType().represents(byte.class)
                || arg.getType().represents(char.class)
                || arg.getType().represents(boolean.class)
                || arg.getType().represents(short.class)) {
            return Opcodes.INEG;
        } else if (arg.getType().represents(long.class)) {
            return Opcodes.LNEG;
        } else if (arg.getType().represents(float.class)) {
            return Opcodes.FNEG;
        } else if (arg.getType().represents(double.class)) {
            return Opcodes.DNEG;
        } else {
            throw new Ts2JavaAstException(
                    ast.getArg(),
                    SimpleFreeMarkerFormat.format("Minus cannot be applied to type ${type}.",
                            SimpleMap.of("type", arg.getType().getName())));
        }
    }

    @Override
    public boolean isBangFlippable() {
        if (op == Swc4jAstUnaryOp.Bang && arg instanceof ITs2JavaBangFlippable) {
            return arg.as(ITs2JavaBangFlippable.class).isBangFlippable();
        }
        return false;
    }

    @Override
    public boolean isMinusFlippable() {
        if (op == Swc4jAstUnaryOp.Minus && arg instanceof ITs2JavaMinusFlippable) {
            return arg.as(ITs2JavaMinusFlippable.class).isMinusFlippable();
        }
        return false;
    }
}
