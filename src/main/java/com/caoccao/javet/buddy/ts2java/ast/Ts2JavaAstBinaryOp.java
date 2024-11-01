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

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaException;
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstBinaryOp;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.*;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public final class Ts2JavaAstBinaryOp {
    private Ts2JavaAstBinaryOp() {
    }

    public static Addition getAddition(TypeDescription type) {
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

    public static Division getDivision(TypeDescription type) {
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

    public static StackManipulation getLogicalStackManipulation(Swc4jAstBinaryOp binaryOp, TypeDescription type) {
        Label labelFalse = new Label();
        Label labelTrue = new Label();
        List<StackManipulation> stackManipulations = new ArrayList<>();
        if (type.represents(int.class)
                || type.represents(short.class)
                || type.represents(byte.class)
                || type.represents(char.class)) {
            int opcodeCompare;
            switch (binaryOp) {
                case Gt:
                    opcodeCompare = Opcodes.IF_ICMPLE;
                    break;
                case GtEq:
                    opcodeCompare = Opcodes.IF_ICMPLT;
                    break;
                case Lt:
                    opcodeCompare = Opcodes.IF_ICMPGE;
                    break;
                case LtEq:
                    opcodeCompare = Opcodes.IF_ICMPGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = Opcodes.IF_ICMPNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = Opcodes.IF_ICMPEQ;
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} for type ${type} in logical operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name(), "type", type.getName())));
            }
            stackManipulations.add(new StackManipulation.Simple((
                    MethodVisitor methodVisitor,
                    Implementation.Context implementationContext) -> {
                methodVisitor.visitJumpInsn(opcodeCompare, labelFalse);
                return new StackManipulation.Size(-1, 0);
            }));
        } else if (type.represents(long.class)) {
            int opcodeCompare;
            switch (binaryOp) {
                case Gt:
                    opcodeCompare = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} for type ${type} in logical operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name(), "type", type.getName())));
            }
            stackManipulations.add(new StackManipulation.Simple((
                    MethodVisitor methodVisitor,
                    Implementation.Context implementationContext) -> {
                methodVisitor.visitInsn(Opcodes.LCMP);
                methodVisitor.visitJumpInsn(opcodeCompare, labelFalse);
                return new StackManipulation.Size(-2, 0);
            }));
        } else if (type.represents(float.class)) {
            int opcodeCompare1;
            int opcodeCompare2;
            switch (binaryOp) {
                case Gt:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare1 = Opcodes.FCMPG;
                    opcodeCompare2 = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare1 = Opcodes.FCMPG;
                    opcodeCompare2 = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} for type ${type} in logical operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name(), "type", type.getName())));
            }
            stackManipulations.add(new StackManipulation.Simple((
                    MethodVisitor methodVisitor,
                    Implementation.Context implementationContext) -> {
                methodVisitor.visitInsn(opcodeCompare1);
                methodVisitor.visitJumpInsn(opcodeCompare2, labelFalse);
                return new StackManipulation.Size(-1, 0);
            }));
        } else if (type.represents(double.class)) {
            int opcodeCompare1;
            int opcodeCompare2;
            switch (binaryOp) {
                case Gt:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare1 = Opcodes.DCMPG;
                    opcodeCompare2 = Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare1 = Opcodes.DCMPG;
                    opcodeCompare2 = Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = Opcodes.IFEQ;
                    break;
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} for type ${type} in logical operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name(), "type", type.getName())));
            }
            stackManipulations.add(new StackManipulation.Simple((
                    MethodVisitor methodVisitor,
                    Implementation.Context implementationContext) -> {
                methodVisitor.visitInsn(opcodeCompare1);
                methodVisitor.visitJumpInsn(opcodeCompare2, labelFalse);
                return new StackManipulation.Size(-2, 0);
            }));
        } else {
            throw new Ts2JavaException(
                    SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical operation.",
                            SimpleMap.of("type", type.getName())));
        }
        stackManipulations.add(new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelTrue);
            methodVisitor.visitLabel(labelFalse);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelTrue);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            return StackManipulation.Size.ZERO;
        }));
        return new StackManipulation.Compound(stackManipulations);
    }

    public static Multiplication getMultiplication(TypeDescription type) {
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

    public static Remainder getRemainder(TypeDescription type) {
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

    public static ShiftLeft getShiftLeft(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftLeft.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftLeft.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in left shift.",
                        SimpleMap.of("type", type.getName())));
    }

    public static ShiftRight getShiftRight(TypeDescription type) {
        if (type.represents(int.class)) {
            return ShiftRight.INTEGER;
        } else if (type.represents(long.class)) {
            return ShiftRight.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in right shift.",
                        SimpleMap.of("type", type.getName())));
    }

    public static Subtraction getSubtraction(TypeDescription type) {
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
}
