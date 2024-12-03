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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstExpr;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstStmt;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstReturnStmt;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Optional;

public class Ts2JavaAstReturnStmt
        extends BaseTs2JavaAst<Swc4jAstReturnStmt, Ts2JavaMemoFunction>
        implements ITs2JavaAstStmt<Swc4jAstReturnStmt, Ts2JavaMemoFunction> {
    protected final Optional<ITs2JavaAstExpr<?, ?>> arg;

    protected Ts2JavaAstReturnStmt(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstReturnStmt ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        arg = ast.getArg().map(arg -> ITs2JavaAstExpr.create(this, arg, memo));
        type = memo.getReturnType();
    }

    public static Ts2JavaAstReturnStmt create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstReturnStmt ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstReturnStmt(parent, ast, memo);
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
            return aggregateSize(sizeArg, sizeCast, sizeReturn);
        }
        return Size.ZERO;
    }

    @Override
    public void compile() {
        arg.ifPresent(ITs2JavaAstExpr::compile);
        // Type can be deduced from arg.
        if (type.represents(void.class) && arg.isPresent()) {
            TypeDescription argType = arg.get().getType();
            if (argType != null) {
                type = argType;
                memo.setReturnType(type);
            }
        }
    }

    public Optional<ITs2JavaAstExpr<?, ?>> getArg() {
        return arg;
    }
}
