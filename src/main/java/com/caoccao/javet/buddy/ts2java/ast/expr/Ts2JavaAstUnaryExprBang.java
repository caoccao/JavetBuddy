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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBangFlippable;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBoolEval;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstUnaryExprBang
        extends Ts2JavaAstUnaryExpr
        implements ITs2JavaBangFlippable, ITs2JavaBoolEval {
    protected Ts2JavaAstUnaryExprBang(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstUnaryExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        op = Swc4jAstUnaryOp.Bang;
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        Size size = super.apply(methodVisitor, context);
        if (!isBangFlippable()) {
            final int opcode = getOpcodeNegative();
            methodVisitor.visitInsn(opcode);
        }
        return size;
    }

    @Override
    public void compile() {
        super.compile();
        flipBang();
    }

    @Override
    public Optional<Boolean> evalBool() {
        if (arg instanceof ITs2JavaBoolEval) {
            return arg.as(ITs2JavaBoolEval.class).evalBool();
        }
        return Optional.empty();
    }

    @Override
    public void flipBang() {
        ITs2JavaBangFlippable.flipBang(arg);
    }

    @Override
    public boolean isBangFlippable() {
        return ITs2JavaBangFlippable.isBangFlippable(arg);
    }
}
