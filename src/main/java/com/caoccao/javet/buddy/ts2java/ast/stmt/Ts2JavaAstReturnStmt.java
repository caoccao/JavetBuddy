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

package com.caoccao.javet.buddy.ts2java.ast.stmt;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.expr.Ts2JavaAstParenExpr;
import com.caoccao.javet.buddy.ts2java.ast.expr.lit.Ts2JavaAstNumber;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstParenExpr;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstReturnStmt
        extends BaseTs2JavaAst<Swc4jAstReturnStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstReturnStmt, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstExpr<?, ?>> arg;

    public Ts2JavaAstReturnStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstReturnStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        if (ast.getArg().isPresent()) {
            ISwc4jAstExpr astExpr = ast.getArg().get();
            ITs2JavaAstExpr<?, ?> arg = null;
            switch (astExpr.getType()) {
                case BinExpr:
                    // TODO
                    break;
                case Number:
                    arg = new Ts2JavaAstNumber(this, astExpr.as(Swc4jAstNumber.class), null, memo);
                    break;
                case Ident:
                    // TODO
                    break;
                case ParenExpr:
                    arg = new Ts2JavaAstParenExpr(this, astExpr.as(Swc4jAstParenExpr.class), memo);
                    break;
                case UnaryExpr:
                    // TODO
                    break;
                default:
                    throw new Ts2JavaAstException(
                            astExpr,
                            SimpleFreeMarkerFormat.format("ReturnStmt arg type ${argType} is not supported.",
                                    SimpleMap.of("argType", astExpr.getType().name())));
            }
            this.arg = Optional.ofNullable(arg);
        } else {
            arg = Optional.empty();
        }
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        if (arg.isPresent()) {
            Size sizeArg = arg.get().apply(methodVisitor, context);
            TypeDescription argType = arg.get().getType();
            Size sizeCast = JavaClassCast.getUpCastStackManipulation(argType, memo.getReturnType())
                    .map(stackManipulation -> stackManipulation.apply(methodVisitor, context))
                    .orElse(Size.ZERO);
            Size sizeReturn = MethodReturn.of(memo.getReturnType()).apply(methodVisitor, context);
            return mergeSize(sizeArg, sizeCast, sizeReturn);
        }
        return Size.ZERO;
    }

    @Override
    public void compile() {
        arg.ifPresent(ITs2JavaAstExpr::compile);
    }

    public Optional<ITs2JavaAstExpr<?, ?>> getArg() {
        return arg;
    }
}
