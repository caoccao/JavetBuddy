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

package com.caoccao.javet.buddy.ts2java.ast.enums;

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.*;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public final class Ts2JavaAstBinaryOp {
    public static final String JAVA_LANG_MATH = "java/lang/Math";

    private Ts2JavaAstBinaryOp() {
    }

    private static Addition getAddition(TypeDescription type) {
        if (type.represents(int.class)) {
            return Addition.INTEGER;
        } else if (type.represents(long.class)) {
            return Addition.LONG;
        } else if (type.represents(float.class)) {
            return Addition.FLOAT;
        } else if (type.represents(double.class)) {
            return Addition.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in addition.",
                        SimpleMap.of("type", type.getName())));
    }

    public static StackManipulation getArithmeticStackManipulation(Swc4jAstBinaryOp binaryOp, TypeDescription type) {
        switch (binaryOp) {
            case Add:
                return getAddition(type);
            case Div:
                return getDivision(type);
            case LShift:
                return getShiftLeft(type);
            case Mod:
                return getRemainder(type);
            case Mul:
                return getMultiplication(type);
            case RShift:
                return getShiftRight(type);
            case Sub:
                return getSubtraction(type);
            case ZeroFillRShift:
                return getZeroFillShiftRight(type);
            case Exp:
                return getExp();
            default:
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format("Binary op ${op} is not supported.",
                                SimpleMap.of("op", binaryOp.name())));
        }
    }

    private static Division getDivision(TypeDescription type) {
        if (type.represents(int.class)) {
            return Division.INTEGER;
        } else if (type.represents(long.class)) {
            return Division.LONG;
        } else if (type.represents(float.class)) {
            return Division.FLOAT;
        } else if (type.represents(double.class)) {
            return Division.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in division.",
                        SimpleMap.of("type", type.getName())));
    }

    private static StackManipulation getExp() {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    JAVA_LANG_MATH,
                    "pow",
                    "(DD)D",
                    false);
            return new StackManipulation.Size(-2, 0);
        });
    }

    public static StackManipulation getLogicalCompareStackManipulation(
            Swc4jAstBinExpr binExpr,
            Swc4jAstBinaryOp binaryOp,
            TypeDescription type,
            Label label) {
        final int opcodeCompare;
        final int opcodeCompareAndJump;
        final int sizeImpact;
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
            sizeImpact = -1;
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
            sizeImpact = -2;
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
            sizeImpact = -1;
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
            sizeImpact = -2;
        } else {
            throw new Ts2JavaAstException(
                    binExpr,
                    SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical compare operation.",
                            SimpleMap.of("type", type.getName())));
        }
        return new StackManipulation.Simple(
                (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                    if (opcodeCompare > Opcodes.NOP) {
                        methodVisitor.visitInsn(opcodeCompare);
                    }
                    methodVisitor.visitJumpInsn(opcodeCompareAndJump, label);
                    return new StackManipulation.Size(sizeImpact, 0);
                });
    }

    private static Multiplication getMultiplication(TypeDescription type) {
        if (type.represents(int.class)) {
            return Multiplication.INTEGER;
        } else if (type.represents(long.class)) {
            return Multiplication.LONG;
        } else if (type.represents(float.class)) {
            return Multiplication.FLOAT;
        } else if (type.represents(double.class)) {
            return Multiplication.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in multiplication.",
                        SimpleMap.of("type", type.getName())));
    }

    private static Remainder getRemainder(TypeDescription type) {
        if (type.represents(int.class)) {
            return Remainder.INTEGER;
        } else if (type.represents(long.class)) {
            return Remainder.LONG;
        } else if (type.represents(float.class)) {
            return Remainder.FLOAT;
        } else if (type.represents(double.class)) {
            return Remainder.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in mod.",
                        SimpleMap.of("type", type.getName())));
    }

    private static ShiftLeft getShiftLeft(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftLeft.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftLeft.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in left shift.",
                        SimpleMap.of("type", type.getName())));
    }

    private static ShiftRight getShiftRight(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftRight.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftRight.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in right shift.",
                        SimpleMap.of("type", type.getName())));
    }

    private static Subtraction getSubtraction(TypeDescription type) {
        if (type.represents(int.class)) {
            return Subtraction.INTEGER;
        } else if (type.represents(long.class)) {
            return Subtraction.LONG;
        } else if (type.represents(float.class)) {
            return Subtraction.FLOAT;
        } else if (type.represents(double.class)) {
            return Subtraction.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in subtraction.",
                        SimpleMap.of("type", type.getName())));
    }

    private static StackManipulation getZeroFillShiftRight(TypeDescription type) {
        if (type.represents(int.class)) {
            return new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitInsn(Opcodes.IUSHR);
                        return new StackManipulation.Size(-1, 0);
                    });
        } else if (type.represents(long.class)) {
            return new StackManipulation.Simple(
                    (MethodVisitor methodVisitor, Implementation.Context implementationContext) -> {
                        methodVisitor.visitInsn(Opcodes.L2I);
                        methodVisitor.visitInsn(Opcodes.LUSHR);
                        return new StackManipulation.Size(-2, 0);
                    });
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in zero fill right shift.",
                        SimpleMap.of("type", type.getName())));
    }
}
