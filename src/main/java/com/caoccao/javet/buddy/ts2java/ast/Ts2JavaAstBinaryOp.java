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
import com.caoccao.javet.swc4j.ast.enums.Swc4jAstUnaryOp;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstUnaryExpr;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAst;
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

    public static StackManipulation getArithmetic(Swc4jAstBinaryOp binaryOp, TypeDescription upCaseType) {
        switch (binaryOp) {
            case Add:
                return getAddition(upCaseType);
            case Div:
                return getDivision(upCaseType);
            case LShift:
                return getShiftLeft(upCaseType);
            case Mod:
                return getRemainder(upCaseType);
            case Mul:
                return getMultiplication(upCaseType);
            case RShift:
                return getShiftRight(upCaseType);
            case Sub:
                return getSubtraction(upCaseType);
            case ZeroFillRShift:
                return getZeroFillShiftRight(upCaseType);
            case Exp:
                return getExp();
            default:
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format("Binary op ${op} is not supported.",
                                SimpleMap.of("op", binaryOp.name())));
        }
    }

    private static int getBangCount(ISwc4jAst ast) {
        switch (ast.getType()) {
            case BinExpr:
                if (ast.as(Swc4jAstBinExpr.class).getOp().isLogicalOperator()) {
                    return getBangCount(ast.getParent());
                }
                return 0;
            case ParenExpr:
                return getBangCount(ast.getParent());
            case UnaryExpr:
                if (ast.as(Swc4jAstUnaryExpr.class).getOp() == Swc4jAstUnaryOp.Bang) {
                    return getBangCount(ast.getParent()) + 1;
                }
                return 0;
            default:
                return 0;
        }
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

    private static Swc4jAstBinaryOp getFlippedBinaryOpLogical(Swc4jAstBinaryOp binaryOp) {
        switch (binaryOp) {
            case EqEq:
                return Swc4jAstBinaryOp.NotEq;
            case EqEqEq:
                return Swc4jAstBinaryOp.NotEqEq;
            case Gt:
                return Swc4jAstBinaryOp.LtEq;
            case GtEq:
                return Swc4jAstBinaryOp.Lt;
            case Lt:
                return Swc4jAstBinaryOp.GtEq;
            case LtEq:
                return Swc4jAstBinaryOp.Gt;
            case LogicalAnd:
                return Swc4jAstBinaryOp.LogicalOr;
            case LogicalOr:
                return Swc4jAstBinaryOp.LogicalAnd;
            case NotEq:
                return Swc4jAstBinaryOp.EqEq;
            case NotEqEq:
                return Swc4jAstBinaryOp.EqEqEq;
            default:
                throw new Ts2JavaException(
                        SimpleFreeMarkerFormat.format("Unsupported binary op ${op} in logical operation.",
                                SimpleMap.of("op", binaryOp.name())));
        }
    }

    public static StackManipulation getLogical(
            JavaFunctionContext functionContext,
            ISwc4jAst ast,
            Swc4jAstBinaryOp binaryOp,
            TypeDescription type) {
        final JavaLogicalLabels logicalLabels = functionContext.getLogicalLabels();
        final List<StackManipulation> stackManipulations = new ArrayList<>();
        if (getBangCount(ast) % 2 == 1) {
            binaryOp = getFlippedBinaryOpLogical(binaryOp);
        }
        switch (binaryOp) {
            case LogicalAnd:
                stackManipulations.add(getLogicalAnd(logicalLabels));
                break;
            case LogicalOr:
                stackManipulations.add(getLogicalOr(logicalLabels));
                break;
            default: {
                if (type.represents(int.class)
                        || type.represents(short.class)
                        || type.represents(byte.class)
                        || type.represents(char.class)) {
                    stackManipulations.add(getLogicalCompareForInt(logicalLabels, binaryOp));
                } else if (type.represents(long.class)) {
                    stackManipulations.add(getLogicalCompareForLong(logicalLabels, binaryOp));
                } else if (type.represents(float.class)) {
                    stackManipulations.add(getLogicalCompareForFloat(logicalLabels, binaryOp));
                } else if (type.represents(double.class)) {
                    stackManipulations.add(getLogicalCompareForDouble(logicalLabels, binaryOp));
                } else {
                    throw new Ts2JavaException(
                            SimpleFreeMarkerFormat.format("Unsupported type ${type} in logical operation.",
                                    SimpleMap.of("type", type.getName())));
                }
                break;
            }
        }
        return new StackManipulation.Compound(stackManipulations);
    }

    public static StackManipulation getLogicalAnd(JavaLogicalLabels logicalLabels) {
        // There is no need to do anything.
        return StackManipulation.Trivial.INSTANCE;
    }

    private static StackManipulation getLogicalCompareForDouble(
            JavaLogicalLabels logicalLabels,
            Swc4jAstBinaryOp binaryOp) {
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
                        SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical operation.",
                                SimpleMap.of("binaryOp", binaryOp.name())));
        }
        final Label labelFalse = logicalLabels.getLastLabel();
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitInsn(opcodeCompare1);
            methodVisitor.visitJumpInsn(opcodeCompare2, labelFalse);
            return new StackManipulation.Size(-2, 0);
        });
    }

    private static StackManipulation getLogicalCompareForFloat(
            JavaLogicalLabels logicalLabels,
            Swc4jAstBinaryOp binaryOp) {
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
                        SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical operation.",
                                SimpleMap.of("binaryOp", binaryOp.name())));
        }
        final Label labelFalse = logicalLabels.getLastLabel();
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitInsn(opcodeCompare1);
            methodVisitor.visitJumpInsn(opcodeCompare2, labelFalse);
            return new StackManipulation.Size(-1, 0);
        });
    }

    private static StackManipulation getLogicalCompareForInt(
            JavaLogicalLabels logicalLabels,
            Swc4jAstBinaryOp binaryOp) {
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
                        SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical operation.",
                                SimpleMap.of("binaryOp", binaryOp.name())));
        }
        final Label labelFalse = logicalLabels.getLastLabel();
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitJumpInsn(opcodeCompare, labelFalse);
            return new StackManipulation.Size(-1, 0);
        });
    }

    private static StackManipulation getLogicalCompareForLong(
            JavaLogicalLabels logicalLabels,
            Swc4jAstBinaryOp binaryOp) {
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
                        SimpleFreeMarkerFormat.format("Unsupported binary operation ${binaryOp} in logical operation.",
                                SimpleMap.of("binaryOp", binaryOp.name())));
        }
        final Label labelFalse = logicalLabels.getLastLabel();
        return new StackManipulation.Simple((
                MethodVisitor methodVisitor,
                Implementation.Context implementationContext) -> {
            methodVisitor.visitInsn(Opcodes.LCMP);
            methodVisitor.visitJumpInsn(opcodeCompare, labelFalse);
            return new StackManipulation.Size(-2, 0);
        });
    }

    public static StackManipulation getLogicalOr(JavaLogicalLabels logicalLabels) {
        return StackManipulation.Trivial.INSTANCE;
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
