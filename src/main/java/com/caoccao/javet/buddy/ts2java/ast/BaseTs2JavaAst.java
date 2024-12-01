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

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemo;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class BaseTs2JavaAst<AST extends ISwc4jAst, Memo extends Ts2JavaMemo>
        implements ITs2JavaAst<AST, Memo> {
    protected AST ast;
    protected Memo memo;
    protected ITs2JavaAst<?, ?> parent;
    protected TypeDescription type;

    public BaseTs2JavaAst(ITs2JavaAst<?, ?> parent, AST ast, Memo memo) {
        this.ast = Objects.requireNonNull(ast);
        this.memo = Objects.requireNonNull(memo);
        this.parent = parent;
        type = null;
    }

    protected static Size aggregateSize(Size... sizes) {
        return Stream.of(sizes).reduce(BaseTs2JavaAst::aggregateSize).orElse(Size.ZERO);
    }

    protected static Size aggregateSize(Size leftSize, Size rightSize) {
        return leftSize.aggregate(rightSize);
    }

    @Override
    public AST getAst() {
        return ast;
    }

    @Override
    public Memo getMemo() {
        return memo;
    }

    public ITs2JavaAst<?, ?> getParent() {
        return parent;
    }

    @Override
    public TypeDescription getType() {
        return type;
    }

    protected void visitLineNumber(MethodVisitor methodVisitor) {
        if (memo instanceof Ts2JavaMemoFunction) {
            Ts2JavaMemoFunction memoFunction = (Ts2JavaMemoFunction) memo;
            final int lineNumber = ast.getSpan().getLine();
            if (memoFunction.getLineNumber() < lineNumber) {
                Label label = new Label();
                methodVisitor.visitLabel(label);
                methodVisitor.visitLineNumber(lineNumber, label);
                memoFunction.setLineNumber(lineNumber);
            }
        }
    }
}
