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

package com.caoccao.javet.buddy.ts2java.compiler.instructions;

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

public class JavaInstructionLogicalCompare implements StackManipulation {
    protected Swc4jAstBinExpr binExpr;
    protected Swc4jAstBinaryOp binaryOp;
    protected Label label;
    protected TypeDescription type;

    public JavaInstructionLogicalCompare(
            Swc4jAstBinExpr binExpr,
            Swc4jAstBinaryOp binaryOp,
            TypeDescription type,
            Label label) {
        setBinExpr(binExpr);
        setBinaryOp(binaryOp);
        setLabel(label);
        setType(type);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context implementationContext) {
        final int opcodeCompare;
        final int opcodeCompareAndJump;
        final int stackImpact;
        if (type.represents(int.class)
                || type.represents(short.class)
                || type.represents(byte.class)
                || type.represents(char.class)) {
            switch (binaryOp) {
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
                            binExpr,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical compare int operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name())));
            }
            opcodeCompare = Opcodes.NOP;
            stackImpact = -1;
        } else if (type.represents(long.class)) {
            switch (binaryOp) {
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
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical compare long operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name())));
            }
            opcodeCompare = Opcodes.LCMP;
            stackImpact = -2;
        } else if (type.represents(float.class)) {
            switch (binaryOp) {
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
                            binExpr,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical compare float operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name())));
            }
            stackImpact = -1;
        } else if (type.represents(double.class)) {
            switch (binaryOp) {
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
                            binExpr,
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical compare double operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name())));
            }
            stackImpact = -2;
        } else {
            throw new Ts2JavaAstException(
                    binExpr,
                    SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical operation.",
                            SimpleMap.of("type", type.getName())));
        }
        if (opcodeCompare > Opcodes.NOP) {
            methodVisitor.visitInsn(opcodeCompare);
        }
        methodVisitor.visitJumpInsn(opcodeCompareAndJump, label);
        return new StackManipulation.Size(stackImpact, 0);
    }

    public Swc4jAstBinExpr getBinExpr() {
        return binExpr;
    }

    public Swc4jAstBinaryOp getBinaryOp() {
        return binaryOp;
    }

    public Label getLabel() {
        return label;
    }

    public TypeDescription getType() {
        return type;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public JavaInstructionLogicalCompare setBinExpr(Swc4jAstBinExpr binExpr) {
        this.binExpr = binExpr;
        return this;
    }

    public JavaInstructionLogicalCompare setBinaryOp(Swc4jAstBinaryOp binaryOp) {
        this.binaryOp = Objects.requireNonNull(binaryOp);
        return this;
    }

    public JavaInstructionLogicalCompare setLabel(Label label) {
        this.label = Objects.requireNonNull(label);
        return this;
    }

    public JavaInstructionLogicalCompare setType(TypeDescription type) {
        this.type = Objects.requireNonNull(type);
        return this;
    }
}
