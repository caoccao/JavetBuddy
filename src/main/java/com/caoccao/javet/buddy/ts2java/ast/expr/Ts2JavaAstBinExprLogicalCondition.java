/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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
import com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities.ITs2JavaBoolEval;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLabelUtils;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ts2JavaAstBinExprLogicalCondition extends Ts2JavaAstBinExprLogical {

    protected Ts2JavaAstBinExprLogicalCondition(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstBinExpr ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        super.apply(methodVisitor, context);
        boolean ignoreClose = isLabelOverridden();
        final List<Size> sizes = new ArrayList<>();
        final Swc4jAstBinaryOp resolvedOp = getResolvedOp();
        final Optional<Boolean> optionalLeftBool = ITs2JavaBoolEval.evalBool(left);
        final Optional<Boolean> optionalRightBool = ITs2JavaBoolEval.evalBool(right);
        final boolean isLeftLogical = left instanceof Ts2JavaAstBinExprLogical;
        final boolean isRightLogical = right instanceof Ts2JavaAstBinExprLogical;
        final int opcodeCompareFalse = bangFlipped ? Opcodes.IFNE : Opcodes.IFEQ;
        switch (resolvedOp) {
            case LogicalAnd: {
                if (optionalLeftBool.isPresent() && optionalRightBool.isPresent()) {
                    if (!optionalRightBool.get()) {
                        sizes.add(right.apply(methodVisitor, context));
                    } else {
                        sizes.add(left.apply(methodVisitor, context));
                    }
                    ignoreClose = true;
                    break;
                } else if (optionalLeftBool.isPresent()) {
                    if (!optionalLeftBool.get()) {
                        sizes.add(left.apply(methodVisitor, context));
                        ignoreClose = true;
                        break;
                    }
                } else if (optionalRightBool.isPresent()) {
                    if (!optionalRightBool.get()) {
                        sizes.add(right.apply(methodVisitor, context));
                        ignoreClose = true;
                        break;
                    }
                }
                if (!optionalLeftBool.isPresent()) {
                    sizes.add(left.apply(methodVisitor, context));
                    if (!isLeftLogical) {
                        methodVisitor.visitJumpInsn(opcodeCompareFalse, labelFalse);
                    }
                }
                if (labelSwitched && isRightLogical) {
                    right.as(Ts2JavaAstBinExprLogical.class).setLabelSwitched(true);
                }
                if (!optionalRightBool.isPresent()) {
                    sizes.add(right.apply(methodVisitor, context));
                    if (!isRightLogical) {
                        methodVisitor.visitJumpInsn(opcodeCompareFalse, labelSwitched ? labelTrue : labelFalse);
                    }
                }
                if (labelSwitched && isLeftLogical) {
                    Ts2JavaAstBinExprLogical leftLogical = left.as(Ts2JavaAstBinExprLogical.class);
                    methodVisitor.visitLabel(leftLogical.getLabelFalse());
                    methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                }
                break;
            }
            case LogicalOr: {
                if (optionalLeftBool.isPresent() && optionalRightBool.isPresent()) {
                    if (optionalRightBool.get()) {
                        sizes.add(right.apply(methodVisitor, context));
                    } else {
                        sizes.add(left.apply(methodVisitor, context));
                    }
                    ignoreClose = true;
                    break;
                } else if (optionalLeftBool.isPresent()) {
                    if (optionalLeftBool.get()) {
                        sizes.add(left.apply(methodVisitor, context));
                        ignoreClose = true;
                        break;
                    }
                } else if (optionalRightBool.isPresent()) {
                    if (optionalRightBool.get()) {
                        sizes.add(right.apply(methodVisitor, context));
                        ignoreClose = true;
                        break;
                    }
                }
                final int opcodeCompareTrue = bangFlipped ? Opcodes.IFEQ : Opcodes.IFNE;
                if (!optionalLeftBool.isPresent()) {
                    if (isLeftLogical) {
                        left.as(Ts2JavaAstBinExprLogical.class).setLabelSwitched(true);
                    }
                    sizes.add(left.apply(methodVisitor, context));
                    if (!isLeftLogical) {
                        methodVisitor.visitJumpInsn(opcodeCompareTrue, labelSwitched ? labelFalse : labelTrue);
                    }
                    if (optionalRightBool.isPresent()) {
                        methodVisitor.visitJumpInsn(Opcodes.GOTO, labelSwitched ? labelTrue : labelFalse);
                    }
                }
                if (!optionalRightBool.isPresent()) {
                    sizes.add(right.apply(methodVisitor, context));
                    if (!isRightLogical) {
                        methodVisitor.visitJumpInsn(
                                isLabelOverridden() ? opcodeCompareTrue : opcodeCompareFalse,
                                labelSwitched ? labelTrue : labelFalse);
                    }
                }
                methodVisitor.visitLabel(labelTrue);
                methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                break;
            }
            default:
                throw new Ts2JavaAstException(
                        ast,
                        SimpleFreeMarkerFormat.format("Bin expr op ${op} is not supported.",
                                SimpleMap.of("op", ast.getOp().name())));
        }
        if (!ignoreClose) {
            sizes.add(JavaLabelUtils.generateLogicalClose(methodVisitor, labelFalse));
        }
        return aggregateSize(sizes);
    }

    @Override
    public void compile() {
        super.compile();
        if (left.getType() == null) {
            throw new Ts2JavaAstException(ast.getLeft(), "Left type is unknown in logical AND (&&).");
        }
        if (right.getType() == null) {
            throw new Ts2JavaAstException(ast.getRight(), "Right type is unknown in logical AND (&&).");
        }
        if (!left.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    ast.getLeft(),
                    SimpleFreeMarkerFormat.format("Unsupported left type ${type} in logical AND (&&).",
                            SimpleMap.of("type", left.getType().getName())));
        }
        if (!right.getType().represents(boolean.class)) {
            throw new Ts2JavaAstException(
                    ast.getRight(),
                    SimpleFreeMarkerFormat.format("Unsupported right type ${type} in logical AND (&&).",
                            SimpleMap.of("type", right.getType().getName())));
        }
    }

    @Override
    public Swc4jAstBinaryOp getResolvedOp() {
        return bangFlipped ? op.getOppositeOperator() : op;
    }

    @Override
    public void syncLabels() {
        if (left instanceof Ts2JavaAstBinExprLogicalCompare) {
            Ts2JavaAstBinExprLogicalCompare leftLogical = left.as(Ts2JavaAstBinExprLogicalCompare.class);
            leftLogical.setLabelTrue(labelTrue);
            leftLogical.setLabelFalse(labelFalse);
        } else if (left instanceof Ts2JavaAstBinExprLogicalCondition) {
            Ts2JavaAstBinExprLogicalCondition leftLogical = left.as(Ts2JavaAstBinExprLogicalCondition.class);
            if ((leftLogical.getResolvedOp() == Swc4jAstBinaryOp.LogicalOr)
                    || (!(right instanceof Ts2JavaAstBinExprLogical))) {
                leftLogical.setLabelFalse(labelFalse);
            }
            leftLogical.setLabelTrue(labelTrue);
        }
        if (right instanceof Ts2JavaAstBinExprLogicalCompare) {
            Ts2JavaAstBinExprLogicalCompare rightLogical = right.as(Ts2JavaAstBinExprLogicalCompare.class);
            rightLogical.setLabelTrue(labelTrue);
            rightLogical.setLabelFalse(labelFalse);
        } else if (right instanceof Ts2JavaAstBinExprLogicalCondition) {
            Ts2JavaAstBinExprLogicalCondition rightLogical = right.as(Ts2JavaAstBinExprLogicalCondition.class);
            if ((rightLogical.getResolvedOp() == Swc4jAstBinaryOp.LogicalAnd)
                    || (!(left instanceof Ts2JavaAstBinExprLogical))) {
                rightLogical.setLabelTrue(labelTrue);
            }
            rightLogical.setLabelFalse(labelFalse);
        }
        super.syncLabels();
    }
}
