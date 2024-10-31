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

public final class Ts2JavaAstBinaryOp {
    private Ts2JavaAstBinaryOp() {
    }

    public static Addition getAddition(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return Addition.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return Addition.LONG;
        } else if (type.isAssignableTo(float.class)) {
            return Addition.FLOAT;
        } else if (type.isAssignableTo(double.class)) {
            return Addition.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in addition.",
                        SimpleMap.of("type", type.getName())));
    }

    public static Division getDivision(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return Division.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return Division.LONG;
        } else if (type.isAssignableTo(float.class)) {
            return Division.FLOAT;
        } else if (type.isAssignableTo(double.class)) {
            return Division.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in division.",
                        SimpleMap.of("type", type.getName())));
    }

    public static StackManipulation getLogicalStackManipulation(Swc4jAstBinaryOp binaryOp, TypeDescription type) {
        Label labelFalse = new Label();
        Label labelTrue = new Label();
        int opcodeCompare;
        if (type.isAssignableTo(int.class)) {
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
                case NotEq:
                case NotEqEq:
                default:
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} for type ${type} in logical operation.",
                                    SimpleMap.of("binaryOp", binaryOp.name(), "type", type.getName())));
            }
        } else {
            throw new Ts2JavaException(
                    SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical operation.",
                            SimpleMap.of("type", type.getName())));
        }
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitJumpInsn(opcodeCompare, labelFalse);
            methodVisitor.visitInsn(Opcodes.ICONST_1);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, labelTrue);
            methodVisitor.visitLabel(labelFalse);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(Opcodes.ICONST_0);
            methodVisitor.visitLabel(labelTrue);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            return StackManipulation.Size.ZERO;
        });
    }

    public static Multiplication getMultiplication(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return Multiplication.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return Multiplication.LONG;
        } else if (type.isAssignableTo(float.class)) {
            return Multiplication.FLOAT;
        } else if (type.isAssignableTo(double.class)) {
            return Multiplication.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in multiplication.",
                        SimpleMap.of("type", type.getName())));
    }

    public static Remainder getRemainder(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return Remainder.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return Remainder.LONG;
        } else if (type.isAssignableTo(float.class)) {
            return Remainder.FLOAT;
        } else if (type.isAssignableTo(double.class)) {
            return Remainder.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in mod.",
                        SimpleMap.of("type", type.getName())));
    }

    public static ShiftLeft getShiftLeft(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return ShiftLeft.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return ShiftLeft.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in left shift.",
                        SimpleMap.of("type", type.getName())));
    }

    public static ShiftRight getShiftRight(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return ShiftRight.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return ShiftRight.LONG;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in right shift.",
                        SimpleMap.of("type", type.getName())));
    }

    public static Subtraction getSubtraction(TypeDescription type) {
        if (type.isAssignableTo(int.class)) {
            return Subtraction.INTEGER;
        } else if (type.isAssignableTo(long.class)) {
            return Subtraction.LONG;
        } else if (type.isAssignableTo(float.class)) {
            return Subtraction.FLOAT;
        } else if (type.isAssignableTo(double.class)) {
            return Subtraction.DOUBLE;
        }
        throw new Ts2JavaException(
                SimpleFreeMarkerFormat.format("Unsupported type ${type} in subtraction.",
                        SimpleMap.of("type", type.getName())));
    }
}
