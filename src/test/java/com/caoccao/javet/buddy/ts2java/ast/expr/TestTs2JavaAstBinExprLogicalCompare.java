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

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTs2JavaAstBinExprLogicalCompare extends BaseTestTs2Java {
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

    @Test
    public void testLogicalEQEQ_DD_Z() throws Exception {
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
                "return a > b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
    }

    @Test
    public void testLogicalGT_IJ_Z() throws Exception {
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
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
        tsClass = new TsClass(
                "return !(a < b);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertTrue((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }
}
