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

package com.caoccao.javet.buddy.ts2java.compiler;

import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

public class JavaLoggingMethodVisitor extends MethodVisitor {
    protected static final Map<Integer, String> FRAME_TYPE_MAP = new HashMap<>();
    protected static final Map<Integer, String> OPCODE_MAP = new HashMap<>();

    static {
        FRAME_TYPE_MAP.put(Opcodes.F_FULL, "F_FULL");
        FRAME_TYPE_MAP.put(Opcodes.F_APPEND, "F_APPEND");
        FRAME_TYPE_MAP.put(Opcodes.F_CHOP, "F_CHOP");
        FRAME_TYPE_MAP.put(Opcodes.F_SAME, "F_SAME");
        FRAME_TYPE_MAP.put(Opcodes.F_SAME1, "F_SAME1");

        OPCODE_MAP.put(Opcodes.NOP, "NOP");
        OPCODE_MAP.put(Opcodes.ACONST_NULL, "ACONST_NULL");
        OPCODE_MAP.put(Opcodes.ICONST_M1, "ICONST_M1");
        OPCODE_MAP.put(Opcodes.ICONST_0, "ICONST_0");
        OPCODE_MAP.put(Opcodes.ICONST_1, "ICONST_1");
        OPCODE_MAP.put(Opcodes.ICONST_2, "ICONST_2");
        OPCODE_MAP.put(Opcodes.ICONST_3, "ICONST_3");
        OPCODE_MAP.put(Opcodes.ICONST_4, "ICONST_4");
        OPCODE_MAP.put(Opcodes.ICONST_5, "ICONST_5");
        OPCODE_MAP.put(Opcodes.LCONST_0, "LCONST_0");
        OPCODE_MAP.put(Opcodes.LCONST_1, "LCONST_1");
        OPCODE_MAP.put(Opcodes.FCONST_0, "FCONST_0");
        OPCODE_MAP.put(Opcodes.FCONST_1, "FCONST_1");
        OPCODE_MAP.put(Opcodes.FCONST_2, "FCONST_2");
        OPCODE_MAP.put(Opcodes.DCONST_0, "DCONST_0");
        OPCODE_MAP.put(Opcodes.DCONST_1, "DCONST_1");
        OPCODE_MAP.put(Opcodes.BIPUSH, "BIPUSH");
        OPCODE_MAP.put(Opcodes.SIPUSH, "SIPUSH");
        OPCODE_MAP.put(Opcodes.LDC, "LDC");
        OPCODE_MAP.put(Opcodes.ILOAD, "ILOAD");
        OPCODE_MAP.put(Opcodes.LLOAD, "LLOAD");
        OPCODE_MAP.put(Opcodes.FLOAD, "FLOAD");
        OPCODE_MAP.put(Opcodes.DLOAD, "DLOAD");
        OPCODE_MAP.put(Opcodes.ALOAD, "ALOAD");
        OPCODE_MAP.put(Opcodes.IALOAD, "IALOAD");
        OPCODE_MAP.put(Opcodes.LALOAD, "LALOAD");
        OPCODE_MAP.put(Opcodes.FALOAD, "FALOAD");
        OPCODE_MAP.put(Opcodes.DALOAD, "DALOAD");
        OPCODE_MAP.put(Opcodes.AALOAD, "AALOAD");
        OPCODE_MAP.put(Opcodes.BALOAD, "BALOAD");
        OPCODE_MAP.put(Opcodes.CALOAD, "CALOAD");
        OPCODE_MAP.put(Opcodes.SALOAD, "SALOAD");
        OPCODE_MAP.put(Opcodes.ISTORE, "ISTORE");
        OPCODE_MAP.put(Opcodes.LSTORE, "LSTORE");
        OPCODE_MAP.put(Opcodes.FSTORE, "FSTORE");
        OPCODE_MAP.put(Opcodes.DSTORE, "DSTORE");
        OPCODE_MAP.put(Opcodes.ASTORE, "ASTORE");
        OPCODE_MAP.put(Opcodes.IASTORE, "IASTORE");
        OPCODE_MAP.put(Opcodes.LASTORE, "LASTORE");
        OPCODE_MAP.put(Opcodes.FASTORE, "FASTORE");
        OPCODE_MAP.put(Opcodes.DASTORE, "DASTORE");
        OPCODE_MAP.put(Opcodes.AASTORE, "AASTORE");
        OPCODE_MAP.put(Opcodes.BASTORE, "BASTORE");
        OPCODE_MAP.put(Opcodes.CASTORE, "CASTORE");
        OPCODE_MAP.put(Opcodes.SASTORE, "SASTORE");
        OPCODE_MAP.put(Opcodes.POP, "POP");
        OPCODE_MAP.put(Opcodes.POP2, "POP2");
        OPCODE_MAP.put(Opcodes.DUP, "DUP");
        OPCODE_MAP.put(Opcodes.DUP_X1, "DUP_X1");
        OPCODE_MAP.put(Opcodes.DUP_X2, "DUP_X2");
        OPCODE_MAP.put(Opcodes.DUP2, "DUP2");
        OPCODE_MAP.put(Opcodes.DUP2_X1, "DUP2_X1");
        OPCODE_MAP.put(Opcodes.DUP2_X2, "DUP2_X2");
        OPCODE_MAP.put(Opcodes.SWAP, "SWAP");
        OPCODE_MAP.put(Opcodes.IADD, "IADD");
        OPCODE_MAP.put(Opcodes.LADD, "LADD");
        OPCODE_MAP.put(Opcodes.FADD, "FADD");
        OPCODE_MAP.put(Opcodes.DADD, "DADD");
        OPCODE_MAP.put(Opcodes.ISUB, "ISUB");
        OPCODE_MAP.put(Opcodes.LSUB, "LSUB");
        OPCODE_MAP.put(Opcodes.FSUB, "FSUB");
        OPCODE_MAP.put(Opcodes.DSUB, "DSUB");
        OPCODE_MAP.put(Opcodes.IMUL, "IMUL");
        OPCODE_MAP.put(Opcodes.LMUL, "LMUL");
        OPCODE_MAP.put(Opcodes.FMUL, "FMUL");
        OPCODE_MAP.put(Opcodes.DMUL, "DMUL");
        OPCODE_MAP.put(Opcodes.IDIV, "IDIV");
        OPCODE_MAP.put(Opcodes.LDIV, "LDIV");
        OPCODE_MAP.put(Opcodes.FDIV, "FDIV");
        OPCODE_MAP.put(Opcodes.DDIV, "DDIV");
        OPCODE_MAP.put(Opcodes.IREM, "IREM");
        OPCODE_MAP.put(Opcodes.LREM, "LREM");
        OPCODE_MAP.put(Opcodes.FREM, "FREM");
        OPCODE_MAP.put(Opcodes.DREM, "DREM");
        OPCODE_MAP.put(Opcodes.INEG, "INEG");
        OPCODE_MAP.put(Opcodes.LNEG, "LNEG");
        OPCODE_MAP.put(Opcodes.FNEG, "FNEG");
        OPCODE_MAP.put(Opcodes.DNEG, "DNEG");
        OPCODE_MAP.put(Opcodes.ISHL, "ISHL");
        OPCODE_MAP.put(Opcodes.LSHL, "LSHL");
        OPCODE_MAP.put(Opcodes.ISHR, "ISHR");
        OPCODE_MAP.put(Opcodes.LSHR, "LSHR");
        OPCODE_MAP.put(Opcodes.IUSHR, "IUSHR");
        OPCODE_MAP.put(Opcodes.LUSHR, "LUSHR");
        OPCODE_MAP.put(Opcodes.IAND, "IAND");
        OPCODE_MAP.put(Opcodes.LAND, "LAND");
        OPCODE_MAP.put(Opcodes.IOR, "IOR");
        OPCODE_MAP.put(Opcodes.LOR, "LOR");
        OPCODE_MAP.put(Opcodes.IXOR, "IXOR");
        OPCODE_MAP.put(Opcodes.LXOR, "LXOR");
        OPCODE_MAP.put(Opcodes.IINC, "IINC");
        OPCODE_MAP.put(Opcodes.I2L, "I2L");
        OPCODE_MAP.put(Opcodes.I2F, "I2F");
        OPCODE_MAP.put(Opcodes.I2D, "I2D");
        OPCODE_MAP.put(Opcodes.L2I, "L2I");
        OPCODE_MAP.put(Opcodes.L2F, "L2F");
        OPCODE_MAP.put(Opcodes.L2D, "L2D");
        OPCODE_MAP.put(Opcodes.F2I, "F2I");
        OPCODE_MAP.put(Opcodes.F2L, "F2L");
        OPCODE_MAP.put(Opcodes.F2D, "F2D");
        OPCODE_MAP.put(Opcodes.D2I, "D2I");
        OPCODE_MAP.put(Opcodes.D2L, "D2L");
        OPCODE_MAP.put(Opcodes.D2F, "D2F");
        OPCODE_MAP.put(Opcodes.I2B, "I2B");
        OPCODE_MAP.put(Opcodes.I2C, "I2C");
        OPCODE_MAP.put(Opcodes.I2S, "I2S");
        OPCODE_MAP.put(Opcodes.LCMP, "LCMP");
        OPCODE_MAP.put(Opcodes.FCMPL, "FCMPL");
        OPCODE_MAP.put(Opcodes.FCMPG, "FCMPG");
        OPCODE_MAP.put(Opcodes.DCMPL, "DCMPL");
        OPCODE_MAP.put(Opcodes.DCMPG, "DCMPG");
        OPCODE_MAP.put(Opcodes.IFEQ, "IFEQ");
        OPCODE_MAP.put(Opcodes.IFNE, "IFNE");
        OPCODE_MAP.put(Opcodes.IFLT, "IFLT");
        OPCODE_MAP.put(Opcodes.IFGE, "IFGE");
        OPCODE_MAP.put(Opcodes.IFGT, "IFGT");
        OPCODE_MAP.put(Opcodes.IFLE, "IFLE");
        OPCODE_MAP.put(Opcodes.IF_ICMPEQ, "IF_ICMPEQ");
        OPCODE_MAP.put(Opcodes.IF_ICMPNE, "IF_ICMPNE");
        OPCODE_MAP.put(Opcodes.IF_ICMPLT, "IF_ICMPLT");
        OPCODE_MAP.put(Opcodes.IF_ICMPGE, "IF_ICMPGE");
        OPCODE_MAP.put(Opcodes.IF_ICMPGT, "IF_ICMPGT");
        OPCODE_MAP.put(Opcodes.IF_ICMPLE, "IF_ICMPLE");
        OPCODE_MAP.put(Opcodes.IF_ACMPEQ, "IF_ACMPEQ");
        OPCODE_MAP.put(Opcodes.IF_ACMPNE, "IF_ACMPNE");
        OPCODE_MAP.put(Opcodes.GOTO, "GOTO");
        OPCODE_MAP.put(Opcodes.JSR, "JSR");
        OPCODE_MAP.put(Opcodes.RET, "RET");
        OPCODE_MAP.put(Opcodes.TABLESWITCH, "TABLESWITCH");
        OPCODE_MAP.put(Opcodes.LOOKUPSWITCH, "LOOKUPSWITCH");
        OPCODE_MAP.put(Opcodes.IRETURN, "IRETURN");
        OPCODE_MAP.put(Opcodes.LRETURN, "LRETURN");
        OPCODE_MAP.put(Opcodes.FRETURN, "FRETURN");
        OPCODE_MAP.put(Opcodes.DRETURN, "DRETURN");
        OPCODE_MAP.put(Opcodes.ARETURN, "ARETURN");
        OPCODE_MAP.put(Opcodes.RETURN, "RETURN");
        OPCODE_MAP.put(Opcodes.GETSTATIC, "GETSTATIC");
        OPCODE_MAP.put(Opcodes.PUTSTATIC, "PUTSTATIC");
        OPCODE_MAP.put(Opcodes.GETFIELD, "GETFIELD");
        OPCODE_MAP.put(Opcodes.PUTFIELD, "PUTFIELD");
        OPCODE_MAP.put(Opcodes.INVOKEVIRTUAL, "INVOKEVIRTUAL");
        OPCODE_MAP.put(Opcodes.INVOKESPECIAL, "INVOKESPECIAL");
        OPCODE_MAP.put(Opcodes.INVOKESTATIC, "INVOKESTATIC");
        OPCODE_MAP.put(Opcodes.INVOKEINTERFACE, "INVOKEINTERFACE");
        OPCODE_MAP.put(Opcodes.INVOKEDYNAMIC, "INVOKEDYNAMIC");
        OPCODE_MAP.put(Opcodes.NEW, "NEW");
        OPCODE_MAP.put(Opcodes.NEWARRAY, "NEWARRAY");
        OPCODE_MAP.put(Opcodes.ANEWARRAY, "ANEWARRAY");
        OPCODE_MAP.put(Opcodes.ARRAYLENGTH, "ARRAYLENGTH");
        OPCODE_MAP.put(Opcodes.ATHROW, "ATHROW");
        OPCODE_MAP.put(Opcodes.CHECKCAST, "CHECKCAST");
        OPCODE_MAP.put(Opcodes.INSTANCEOF, "INSTANCEOF");
        OPCODE_MAP.put(Opcodes.MONITORENTER, "MONITORENTER");
        OPCODE_MAP.put(Opcodes.MONITOREXIT, "MONITOREXIT");
        OPCODE_MAP.put(Opcodes.MULTIANEWARRAY, "MULTIANEWARRAY");
        OPCODE_MAP.put(Opcodes.IFNULL, "IFNULL");
        OPCODE_MAP.put(Opcodes.IFNONNULL, "IFNONNULL");
    }

    public JavaLoggingMethodVisitor(int api) {
        super(api);
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        System.out.println(" FRAME " + FRAME_TYPE_MAP.get(type) + " " + numLocal + " " + local + " " + numStack + " " + stack);
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    @Override
    public void visitInsn(int opcode) {
        System.out.println(" " + OPCODE_MAP.get(opcode));
        super.visitInsn(opcode);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        System.out.println(" " + OPCODE_MAP.get(opcode) + " " + label);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println(label + ":");
        super.visitLabel(label);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        System.out.println(" LINENUMBER " + line + " " + start);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        System.out.println(" " + OPCODE_MAP.get(opcode) + " " + varIndex);
        super.visitVarInsn(opcode, varIndex);
    }
}
