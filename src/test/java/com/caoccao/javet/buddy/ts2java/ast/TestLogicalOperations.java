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

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogicalOperations extends BaseTestTs2Java {
    /*
  public logicalAndOrAnd_II_Z(II)Z
   L0
    LINENUMBER 91 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ILOAD 1
    ICONST_1
    IF_ICMPGT L2
   L1
   FRAME SAME
    ILOAD 1
    ILOAD 2
    IF_ICMPEQ L3
    ILOAD 2
    ICONST_1
    IF_ICMPLE L3
   L2
   FRAME SAME
    ICONST_1
    GOTO L4
   L3
   FRAME SAME
    ICONST_0
   L4
   FRAME SAME1 I
    IRETURN
   L5
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L5 0
    LOCALVARIABLE a I L0 L5 1
    LOCALVARIABLE b I L0 L5 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalAndOrAnd_II_Z(int a, int b) {
        return (a == b && a > 1) || (a != b && b > 1);
    }

    /*
  public logicalAnd_II_Z(II)Z
   L0
    LINENUMBER 86 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ILOAD 1
    ICONST_1
    IF_ICMPLE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalAnd_II_Z(int a, int b) {
        return a == b && a > 1;
    }

    /*
  public logicalAnd_II_Z(II)Z
   L0
    LINENUMBER 93 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ILOAD 1
    ICONST_1
    IF_ICMPLE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalAnd_ZZ_Z(boolean a, boolean b) {
        return a && b;
    }

    /*
  public logicalEQ_II_Z(II)Z
   L0
    LINENUMBER 60 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalEQ_II_Z(int a, int b) {
        return a == b;
    }

    /*
  public logicalGE_IJ_Z(IJ)Z
   L0
    LINENUMBER 115 L0
    ILOAD 1
    I2L
    LLOAD 2
    LCMP
    IFLT L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    ISTORE 4
   L3
    LINENUMBER 116 L3
    ILOAD 4
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a I L0 L4 1
    LOCALVARIABLE b J L0 L4 2
    LOCALVARIABLE c Z L3 L4 4
    MAXSTACK = 4
    MAXLOCALS = 5
     */
    public boolean logicalGE_IJ_Z(int a, long b) {
        boolean c = a >= b;
        return c;
    }

    /*
  public logicalGT_FF_Z(FF)Z
   L0
    LINENUMBER 120 L0
    FLOAD 1
    FLOAD 2
    FCMPL
    IFLE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a F L0 L3 1
    LOCALVARIABLE b F L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalGT_FF_Z(float a, float b) {
        return a > b;
    }

    /*
  public logicalGT_II_Z(II)Z
   L0
    LINENUMBER 69 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPLE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalGT_II_Z(int a, int b) {
        return a > b;
    }

    /*
  public logicalNot_And_II_Z(II)Z
   L0
    LINENUMBER 210 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ILOAD 1
    ICONST_1
    IF_ICMPGT L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a I L0 L4 1
    LOCALVARIABLE b I L0 L4 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalNot_And_II_Z(int a, int b) {
        return !(a == b && a > 1);
    }

    /*
  public logicalNot_II_Z(II)Z
   L0
    LINENUMBER 204 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPEQ L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalNot_EQ_II_Z(int a, int b) {
        return !(a == b);
    }

    /*
  public logicalOr_DD_Z(DD)Z
   L0
    LINENUMBER 297 L0
    DLOAD 1
    DLOAD 3
    DCMPL
    IFGT L1
    DLOAD 3
    DLOAD 1
    DCMPL
    IFLE L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a D L0 L4 1
    LOCALVARIABLE b D L0 L4 3
    MAXSTACK = 4
    MAXLOCALS = 5
     */
    public boolean logicalOr_DD_Z(double a, double b) {
        return (a > b) || (b > a);
    }

    public boolean logicalOr_FF_Z(float a, float b) {
        return (a > b) || (b > a);
    }

    /*
  public logicalOr_II_Z(II)Z
   L0
    LINENUMBER 321 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPGT L1
    ILOAD 2
    ILOAD 1
    IF_ICMPLE L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a I L0 L4 1
    LOCALVARIABLE b I L0 L4 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalOr_II_Z(int a, int b) {
        return (a > b) || (b > a);
    }

    /*
  public logicalOr_JJ_Z(JJ)Z
   L0
    LINENUMBER 327 L0
    LLOAD 1
    LLOAD 3
    LCMP
    IFGT L1
    LLOAD 3
    LLOAD 1
    LCMP
    IFLE L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a J L0 L4 1
    LOCALVARIABLE b J L0 L4 3
    MAXSTACK = 4
    MAXLOCALS = 5
     */
    public boolean logicalOr_JJ_Z(long a, long b) {
        return (a > b) || (b > a);
    }

    /*
  public logicalOr_II_Z(II)Z
   L0
    LINENUMBER 296 L0
    ILOAD 1
    IFGT L1
    ILOAD 2
    IFLE L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L4 0
    LOCALVARIABLE a I L0 L4 1
    LOCALVARIABLE b I L0 L4 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean logicalOr_ZZ_Z(boolean a, boolean b) {
        return a || b;
    }

//    @Test
    public void testLogicalAndOrAnd_II_Z() throws Exception {
        enableLogging();
        assertTrue(logicalAndOrAnd_II_Z(2, 2));
        assertFalse(logicalAndOrAnd_II_Z(1, 1));
        assertTrue(logicalAndOrAnd_II_Z(1, 2));
        assertFalse(logicalAndOrAnd_II_Z(2, 1));
        TsClass tsClass = new TsClass(
                "return (a == b && a > 1) || (a != b && b > 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(1, 1));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
    }

    @Test
    public void testLogicalAnd_II_Z() throws Exception {
        assertTrue(logicalAnd_II_Z(2, 2));
        assertFalse(logicalAnd_II_Z(2, 3));
        assertFalse(logicalAnd_II_Z(1, 1));
        TsClass tsClass = new TsClass(
                "return a == b && a > 1;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(2, 3));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalAnd_ZZ_Z() throws Exception {
        assertTrue(logicalAnd_ZZ_Z(true, true));
        assertFalse(logicalAnd_ZZ_Z(true, false));
        assertFalse(logicalAnd_ZZ_Z(false, false));
        TsClass tsClass = new TsClass(
                "return a && b;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true));
        assertFalse((boolean) tsClass.invoke(true, false));
        assertFalse((boolean) tsClass.invoke(false, false));
    }

    @Test
    public void testLogicalEQEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalEQEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalEQEQ_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalEQEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a == b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a == b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalEQ_II_Z() throws Exception {
        assertFalse(logicalEQ_II_Z(1, 2));
        TsClass tsClass = new TsClass(
                "return a == b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a == b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalGE_BB_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", byte.class),
                TsMethodArgument.of("b", byte.class));
        assertFalse((boolean) tsClass.invoke((byte) 1, (byte) 2));
        assertTrue((boolean) tsClass.invoke((byte) 2, (byte) 1));
        assertTrue((boolean) tsClass.invoke((byte) 1, (byte) 1));
    }

    @Test
    public void testLogicalGE_CC_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", char.class),
                TsMethodArgument.of("b", char.class));
        assertFalse((boolean) tsClass.invoke((char) 1, (char) 2));
        assertTrue((boolean) tsClass.invoke((char) 2, (char) 1));
        assertTrue((boolean) tsClass.invoke((char) 1, (char) 1));
    }

    @Test
    public void testLogicalGE_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalGE_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalGE_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalGE_IJ_Z() throws Exception {
        assertFalse(logicalGE_IJ_Z(1, 2L));
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
    }

    @Test
    public void testLogicalGE_SS_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >= b;",
                boolean.class,
                TsMethodArgument.of("a", short.class),
                TsMethodArgument.of("b", short.class));
        assertFalse((boolean) tsClass.invoke((short) 1, (short) 2));
        assertTrue((boolean) tsClass.invoke((short) 2, (short) 1));
        assertTrue((boolean) tsClass.invoke((short) 1, (short) 1));
    }

    @Test
    public void testLogicalGT_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a > b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
    }

    @Test
    public void testLogicalGT_FF_Z() throws Exception {
        assertFalse(logicalGT_FF_Z(1F, 2F));
        assertFalse(logicalGT_FF_Z(1.23F, 1.23F));
        TsClass tsClass = new TsClass(
                "return a > b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
    }

    @Test
    public void testLogicalGT_II_Z() throws Exception {
        assertFalse(logicalGT_II_Z(1, 2));
        TsClass tsClass = new TsClass(
                "return a > b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
    }

    @Test
    public void testLogicalGT_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a > b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
    }

    @Test
    public void testLogicalGT_I_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a > 0;",
                boolean.class,
                TsMethodArgument.of("a", int.class));
        assertTrue((boolean) tsClass.invoke(1));
        assertFalse((boolean) tsClass.invoke(0));
        assertFalse((boolean) tsClass.invoke(-1));
        tsClass = new TsClass(
                "return 0 > a;",
                boolean.class,
                TsMethodArgument.of("a", int.class));
        assertFalse((boolean) tsClass.invoke(1));
        assertFalse((boolean) tsClass.invoke(0));
        assertTrue((boolean) tsClass.invoke(-1));
    }

    @Test
    public void testLogicalLE_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a <= b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalLE_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a <= b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalLE_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a <= b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalLE_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a <= b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
    }

    @Test
    public void testLogicalLT_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a < b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalLT_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a < b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalLT_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a < b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalLT_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a < b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
    }

    @Test
    public void testLogicalNotEQEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a !== b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNotEQEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a !== b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNotEQEQ_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a !== b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNotEQEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a !== b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNotEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a != b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNotEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a != b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNotEQ_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a != b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNotEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a != b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_EQEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a === b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_EQEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a === b);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNot_EQEQ_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a === b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_EQEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a === b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_EQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a == b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_EQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a == b);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNot_EQ_II_Z() throws Exception {
        assertTrue(logicalNot_EQ_II_Z(1, 2));
        TsClass tsClass = new TsClass(
                "return !(a == b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_EQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a == b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_GE_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a >= b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_GE_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a >= b);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNot_GE_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a >= b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_GE_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a >= b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_GT_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a > b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_GT_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a > b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_GT_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a > b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_LE_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a <= b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertFalse((boolean) tsClass.invoke(1D, 1D));
        assertFalse((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_LE_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a <= b);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertFalse((boolean) tsClass.invoke(1F, 1F));
        assertFalse((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNot_LE_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a <= b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_LE_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a <= b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertFalse((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_LT_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a < b);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertTrue((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalNot_LT_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a < b);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertTrue((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalNot_LT_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a < b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalNot_LT_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a < b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }

    @Test
    public void testLogicalNot_Not_Z_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !!a;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
    }

    @Test
    public void testLogicalNot_Or_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !(a == b || a > 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(2, 3));
        assertFalse((boolean) tsClass.invoke(1, 1));
        assertTrue((boolean) tsClass.invoke(0, 1));
    }

    @Test
    public void testLogicalNot_Z_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return !a;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
    }

    @Test
    public void testLogicalOr_DD_Z() throws Exception {
        assertTrue(logicalOr_DD_Z(2D, 3D));
        assertTrue(logicalOr_DD_Z(3D, 2D));
        assertFalse(logicalOr_DD_Z(2D, 2D));
        TsClass tsClassGTGT = new TsClass(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClass tsClassGEEQ = new TsClass(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClass tsClassEQLE = new TsClass(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClass tsClassLTLT = new TsClass(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClass tsClassLENE = new TsClass(
                "return (a <= b) || (b != 2);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertTrue((boolean) tsClassGTGT.invoke(2D, 3D));
        assertTrue((boolean) tsClassGTGT.invoke(3D, 2D));
        assertFalse((boolean) tsClassGTGT.invoke(2D, 2D));
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            double a = random.nextDouble();
            double b = random.nextDouble();
            assertEquals((a > b) || (b > a), tsClassGTGT.invoke(a, b), "GT GT " + a + " " + b);
            assertEquals((a >= b) || (b == 1D), tsClassGEEQ.invoke(a, b), "GE EQ " + a + " " + b);
            assertEquals((a == b) || (b <= 2D), tsClassEQLE.invoke(a, b), "EQ LE " + a + " " + b);
            assertEquals((a < b) || (b < a), tsClassLTLT.invoke(a, b), "LT LT " + a + " " + b);
            assertEquals((a <= b) || (b != 2D), tsClassLENE.invoke(a, b), "LE NE " + a + " " + b);
        }
    }

    @Test
    public void testLogicalOr_FF_Z() throws Exception {
        assertTrue(logicalOr_FF_Z(2F, 3F));
        assertTrue(logicalOr_FF_Z(3F, 2F));
        assertFalse(logicalOr_FF_Z(2F, 2F));
        TsClass tsClassGTGT = new TsClass(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClass tsClassGEEQ = new TsClass(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClass tsClassEQLE = new TsClass(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClass tsClassLTLT = new TsClass(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClass tsClassLENE = new TsClass(
                "return (a <= b) || (b != 2);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertTrue((boolean) tsClassGTGT.invoke(2F, 3F));
        assertTrue((boolean) tsClassGTGT.invoke(3F, 2F));
        assertFalse((boolean) tsClassGTGT.invoke(2F, 2F));
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            float a = random.nextFloat();
            float b = random.nextFloat();
            assertEquals((a > b) || (b > a), tsClassGTGT.invoke(a, b), "GT GT " + a + " " + b);
            assertEquals((a >= b) || (b == 1F), tsClassGEEQ.invoke(a, b), "GE EQ " + a + " " + b);
            assertEquals((a == b) || (b <= 2F), tsClassEQLE.invoke(a, b), "EQ LE " + a + " " + b);
            assertEquals((a < b) || (b < a), tsClassLTLT.invoke(a, b), "LT LT " + a + " " + b);
            assertEquals((a <= b) || (b != 2F), tsClassLENE.invoke(a, b), "LE NE " + a + " " + b);
        }
    }

    @Test
    public void testLogicalOr_II_Z() throws Exception {
        assertTrue(logicalOr_II_Z(2, 3));
        assertTrue(logicalOr_II_Z(3, 2));
        assertFalse(logicalOr_II_Z(2, 2));
        TsClass tsClassGTGT = new TsClass(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClass tsClassGEEQ = new TsClass(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClass tsClassEQLE = new TsClass(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClass tsClassLTLT = new TsClass(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClass tsClassLENE = new TsClass(
                "return (a <= b) || (b != 2);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClassGTGT.invoke(2, 3));
        assertTrue((boolean) tsClassGTGT.invoke(3, 2));
        assertFalse((boolean) tsClassGTGT.invoke(2, 2));
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            int a = random.nextInt(4);
            int b = random.nextInt(4);
            assertEquals((a > b) || (b > a), tsClassGTGT.invoke(a, b), "GT GT " + a + " " + b);
            assertEquals((a >= b) || (b == 1), tsClassGEEQ.invoke(a, b), "GE EQ " + a + " " + b);
            assertEquals((a == b) || (b <= 2), tsClassEQLE.invoke(a, b), "EQ LE " + a + " " + b);
            assertEquals((a < b) || (b < a), tsClassLTLT.invoke(a, b), "LT LT " + a + " " + b);
            assertEquals((a <= b) || (b != 2), tsClassLENE.invoke(a, b), "LE NE " + a + " " + b);
        }
    }

    @Test
    public void testLogicalOr_JJ_Z() throws Exception {
        assertTrue(logicalOr_JJ_Z(2L, 3L));
        assertTrue(logicalOr_JJ_Z(3L, 2L));
        assertFalse(logicalOr_JJ_Z(2L, 2L));
        TsClass tsClassGTGT = new TsClass(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClass tsClassGEEQ = new TsClass(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClass tsClassEQLE = new TsClass(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClass tsClassLTLT = new TsClass(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClass tsClassLENE = new TsClass(
                "return (a <= b) || (b != 2);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        assertTrue((boolean) tsClassGTGT.invoke(2L, 3L));
        assertTrue((boolean) tsClassGTGT.invoke(3L, 2L));
        assertFalse((boolean) tsClassGTGT.invoke(2L, 2L));
        Random random = new Random();
        for (long i = 0; i < 100; ++i) {
            long a = random.nextInt(4);
            long b = random.nextInt(4);
            assertEquals((a > b) || (b > a), tsClassGTGT.invoke(a, b), "GT GT " + a + " " + b);
            assertEquals((a >= b) || (b == 1L), tsClassGEEQ.invoke(a, b), "GE EQ " + a + " " + b);
            assertEquals((a == b) || (b <= 2L), tsClassEQLE.invoke(a, b), "EQ LE " + a + " " + b);
            assertEquals((a < b) || (b < a), tsClassLTLT.invoke(a, b), "LT LT " + a + " " + b);
            assertEquals((a <= b) || (b != 2L), tsClassLENE.invoke(a, b), "LE NE " + a + " " + b);
        }
    }

    @Test
    public void testLogicalOr_ZZ_Z() throws Exception {
        assertTrue(logicalOr_ZZ_Z(true, true));
        assertTrue(logicalOr_ZZ_Z(true, false));
        assertTrue(logicalOr_ZZ_Z(false, true));
        assertFalse(logicalOr_ZZ_Z(false, false));
        TsClass tsClass = new TsClass(
                "return a || b;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true));
        assertTrue((boolean) tsClass.invoke(true, false));
        assertTrue((boolean) tsClass.invoke(false, true));
        assertFalse((boolean) tsClass.invoke(false, false));
    }
}
