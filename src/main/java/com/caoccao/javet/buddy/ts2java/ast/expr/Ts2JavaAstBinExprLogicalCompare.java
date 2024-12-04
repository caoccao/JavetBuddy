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
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.Objects;
import java.util.Optional;

public class Ts2JavaAstBinExprLogicalCompare extends Ts2JavaAstBinExpr
        implements ITs2JavaBangFlippable {
    protected Label label;

    protected Ts2JavaAstBinExprLogicalCompare(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        label = new Label();
        type = TypeDescription.ForLoadedType.of(boolean.class);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        super.apply(methodVisitor, context);
        Size sizeLoadLeft = left.apply(methodVisitor, context);
        TypeDescription upCastType = left.getType();
        Size sizeCastLeft = Size.ZERO;
        TypeDescription rightTargetType = op == Swc4jAstBinaryOp.Exp ? type : right.getType();
        Optional<StackManipulation> optionalStackManipulation =
                JavaClassCast.getUpCastStackManipulation(left.getType(), rightTargetType);
        if (optionalStackManipulation.isPresent()) {
            sizeCastLeft = optionalStackManipulation.get().apply(methodVisitor, context);
            upCastType = rightTargetType;
        }
        Size sizeLoadRight = right.apply(methodVisitor, context);
        Size sizeCastRight = JavaClassCast.getUpCastStackManipulation(right.getType(), upCastType)
                .map(s -> s.apply(methodVisitor, context))
                .orElse(Size.ZERO);
        final int opcodeCompare;
        final int opcodeCompareAndJump;
        final int sizeImpact;
        if (upCastType.represents(int.class)
                || upCastType.represents(short.class)
                || upCastType.represents(byte.class)
                || upCastType.represents(char.class)) {
            switch (op) {
                case Gt:
                    opcodeCompareAndJump = Opcodes.IF_ICMPLE;
                    break;
                case GtEq:
                    opcodeCompareAndJump = Opcodes.IF_ICMPLT;
                    break;
                case Lt:
                    opcodeCompareAndJump = Opcodes.IF_ICMPGE;
                    break;
                case LtEq:
                    opcodeCompareAndJump = Opcodes.IF_ICMPGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompareAndJump = Opcodes.IF_ICMPNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompareAndJump = Opcodes.IF_ICMPEQ;
                    break;
                default:
                    throw new Ts2JavaAstException(
                            ast,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${op} in logical compare int operation.",
                                    SimpleMap.of("op", op.name())));
            }
            opcodeCompare = Opcodes.NOP;
            sizeImpact = -1;
        } else if (upCastType.represents(long.class)) {
            switch (op) {
                case Gt:
                    opcodeCompareAndJump = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompareAndJump = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompareAndJump = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompareAndJump = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompareAndJump = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompareAndJump = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${op} in logical compare long operation.",
                                    SimpleMap.of("op", op.name())));
            }
            opcodeCompare = Opcodes.LCMP;
            sizeImpact = -2;
        } else if (upCastType.represents(float.class)) {
            switch (op) {
                case Gt:
                    opcodeCompare = Opcodes.FCMPL;
                    opcodeCompareAndJump = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare = Opcodes.FCMPL;
                    opcodeCompareAndJump = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare = Opcodes.FCMPG;
                    opcodeCompareAndJump = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare = Opcodes.FCMPG;
                    opcodeCompareAndJump = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = Opcodes.FCMPL;
                    opcodeCompareAndJump = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = Opcodes.FCMPL;
                    opcodeCompareAndJump = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaAstException(
                            ast,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${op} in logical compare float operation.",
                                    SimpleMap.of("op", op.name())));
            }
            sizeImpact = -1;
        } else if (upCastType.represents(double.class)) {
            switch (op) {
                case Gt:
                    opcodeCompare = Opcodes.DCMPL;
                    opcodeCompareAndJump = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare = Opcodes.DCMPL;
                    opcodeCompareAndJump = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare = Opcodes.DCMPG;
                    opcodeCompareAndJump = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare = Opcodes.DCMPG;
                    opcodeCompareAndJump = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = Opcodes.DCMPL;
                    opcodeCompareAndJump = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = Opcodes.DCMPL;
                    opcodeCompareAndJump = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaAstException(
                            ast,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${op} in logical compare double operation.",
                                    SimpleMap.of("op", op.name())));
            }
            sizeImpact = -2;
        } else {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical compare operation.",
                            SimpleMap.of("type", upCastType.getName())));
        }
        if (opcodeCompare > Opcodes.NOP) {
            methodVisitor.visitInsn(opcodeCompare);
        }
        methodVisitor.visitJumpInsn(opcodeCompareAndJump, label);
        Size sizeOp = new StackManipulation.Size(sizeImpact, 0);
        if (!(parent instanceof Ts2JavaAstBinExpr)) {
            // This is the top bin expr. Let's close it.
            Label labelClose = new Label();
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelClose);
            methodVisitor.visitLabel(label);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelClose);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
        }
        return aggregateSize(sizeLoadLeft, sizeCastLeft, sizeLoadRight, sizeCastRight, sizeOp);
    }

    @Override
    public void flipBang() {
        op = op.getOppositeOperator();
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public boolean isBangFlippable() {
        return true;
    }

    public Ts2JavaAstBinExprLogicalCompare setLabel(Label label) {
        this.label = Objects.requireNonNull(label);
        return this;
    }
}
