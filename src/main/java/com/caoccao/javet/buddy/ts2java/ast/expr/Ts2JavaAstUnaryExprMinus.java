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

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaMinusFlippable;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

public class Ts2JavaAstUnaryExprMinus
        extends Ts2JavaAstUnaryExpr
        implements ITs2JavaMinusFlippable {
    protected Ts2JavaAstUnaryExprMinus(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        op = Swc4jAstUnaryOp.Minus;
    }

    public static ITs2JavaAstExpr<?, ?> create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        Ts2JavaAstUnaryExprMinus unaryExpr = new Ts2JavaAstUnaryExprMinus(parent, ast, memo);
        if (unaryExpr.isMinusFlippable()) {
            ITs2JavaAstExpr<?, ?> arg = unaryExpr.getArg();
            arg.setParent(parent);
            arg.as(ITs2JavaMinusFlippable.class).flipMinus();
            return arg;
        }
        return unaryExpr;
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        Size size = super.apply(methodVisitor, context);
        if (!isMinusFlippable()) {
            final int opcode = getOpcodeNegative();
            methodVisitor.visitInsn(opcode);
        }
        return size;
    }

    @Override
    public void compile() {
        super.compile();
        flipMinus();
    }

    @Override
    public void flipMinus() {
        ITs2JavaMinusFlippable.flipMinus(arg);
    }

    @Override
    public boolean isMinusFlippable() {
        return ITs2JavaMinusFlippable.isMinusFlippable(arg);
    }
}
