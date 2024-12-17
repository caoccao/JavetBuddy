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
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class Ts2JavaAstUnaryExpr
        extends BaseTs2JavaAst<Swc4jAstUnaryExpr, Ts2JavaMemoFunction>
        implements ITs2JavaAstExpr<Swc4jAstUnaryExpr, Ts2JavaMemoFunction> {
    protected final ITs2JavaAstExpr<?, ?> arg;
    protected Swc4jAstUnaryOp op;

    protected Ts2JavaAstUnaryExpr(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        arg = ITs2JavaAstExpr.create(this, ast.getArg(), memo);
        op = ast.getOp();
    }

    public static Ts2JavaAstUnaryExpr create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        switch (ast.getOp()) {
            case Bang:
                return new Ts2JavaAstUnaryExprBang(parent, ast, memo);
            case Minus:
                return new Ts2JavaAstUnaryExprMinus(parent, ast, memo);
            default:
                return new Ts2JavaAstUnaryExpr(parent, ast, memo);
        }
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return arg.apply(methodVisitor, context);
    }

    @Override
    public void compile() {
        arg.compile();
        type = arg.getType();
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
    public void syncLabels() {
        arg.syncLabels();
    }
}
