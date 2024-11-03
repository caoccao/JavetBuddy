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

import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLogicalLabels;
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
    public static final String JAVA_LANG_MATH = "java/lang/Math";

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

    public static StackManipulation getBitAndStackManipulation(JavaFunctionContext functionContext) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            // TODO
            return new StackManipulation.Size(-1, 0);
        });
    }

    public static StackManipulation getBitOrStackManipulation(JavaFunctionContext functionContext) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            // TODO
            return new StackManipulation.Size(-1, 0);
        });
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

    public static StackManipulation getExp() {
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

    public static StackManipulation getLogical(
            JavaFunctionContext functionContext,
            Swc4jAstBinaryOp binaryOp,
            TypeDescription type,
            boolean logicalNot) {
        functionContext.increaseLogicalDepth();
        final Label labelFalse = functionContext.getLogicalLabels().getLabelFalse();
        final List<StackManipulation> stackManipulations = new ArrayList<>();
        if (type.represents(int.class)
                || type.represents(short.class)
                || type.represents(byte.class)
                || type.represents(char.class)) {
            int opcodeCompare;
            switch (binaryOp) {
                case Gt:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPGT : Opcodes.IF_ICMPLE;
                    break;
                case GtEq:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPGE : Opcodes.IF_ICMPLT;
                    break;
                case Lt:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE;
                    break;
                case LtEq:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPLE : Opcodes.IF_ICMPGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPEQ : Opcodes.IF_ICMPNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = logicalNot ? Opcodes.IF_ICMPNE : Opcodes.IF_ICMPEQ;
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
                    opcodeCompare = logicalNot ? Opcodes.IFGT : Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare = logicalNot ? Opcodes.IFGE : Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare = logicalNot ? Opcodes.IFLT : Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare = logicalNot ? Opcodes.IFLE : Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare = logicalNot ? Opcodes.IFEQ : Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare = logicalNot ? Opcodes.IFNE : Opcodes.IFEQ;
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
                    opcodeCompare2 = logicalNot ? Opcodes.IFGT : Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFGE : Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare1 = Opcodes.FCMPG;
                    opcodeCompare2 = logicalNot ? Opcodes.IFLT : Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare1 = Opcodes.FCMPG;
                    opcodeCompare2 = logicalNot ? Opcodes.IFLE : Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFEQ : Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare1 = Opcodes.FCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFNE : Opcodes.IFEQ;
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
                    opcodeCompare2 = logicalNot ? Opcodes.IFGT : Opcodes.IFLE;
                    break;
                case GtEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFGE : Opcodes.IFLT;
                    break;
                case Lt:
                    opcodeCompare1 = Opcodes.DCMPG;
                    opcodeCompare2 = logicalNot ? Opcodes.IFLT : Opcodes.IFGE;
                    break;
                case LtEq:
                    opcodeCompare1 = Opcodes.DCMPG;
                    opcodeCompare2 = logicalNot ? Opcodes.IFLE : Opcodes.IFGT;
                    break;
                case EqEq:
                case EqEqEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFEQ : Opcodes.IFNE;
                    break;
                case NotEq:
                case NotEqEq:
                    opcodeCompare1 = Opcodes.DCMPL;
                    opcodeCompare2 = logicalNot ? Opcodes.IFNE : Opcodes.IFEQ;
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
        if (functionContext.getLogicalDepth() == 1) {
            stackManipulations.add(getLogicalEnd(functionContext.getLogicalLabels()));
        }
        functionContext.decreaseLogicalDepth();
        return new StackManipulation.Compound(stackManipulations);
    }

    public static StackManipulation getLogicalAndStackManipulation(
            JavaFunctionContext functionContext,
            TypeDescription type) {
        return StackManipulation.Trivial.INSTANCE;
    }

    private static StackManipulation getLogicalEnd(JavaLogicalLabels logicalLabels) {
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            if (logicalLabels.hasLabelTrue()) {
                Label labelTrue = logicalLabels.getLabelTrue();
                methodVisitor.visitLabel(labelTrue);
                methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
            Label labelFalse = logicalLabels.getLabelFalse();
            Label labelEnd = logicalLabels.getLabelEnd();
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelEnd);
            methodVisitor.visitLabel(labelFalse);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelEnd);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            return StackManipulation.Size.ZERO;
        });
    }

    public static StackManipulation getLogicalOrStackManipulation(
            JavaFunctionContext functionContext,
            TypeDescription type) {
        return StackManipulation.Trivial.INSTANCE;
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

    public static StackManipulation getZeroFillShiftRight(TypeDescription type) {
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
