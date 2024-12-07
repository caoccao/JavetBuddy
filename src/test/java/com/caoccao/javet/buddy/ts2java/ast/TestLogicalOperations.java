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
import com.caoccao.javet.buddy.ts2java.TsClassX;
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

    //    @Test
    public void testLogicalAndOrAnd_II_Z() throws Exception {
        enableLogging();
        assertTrue(logicalAndOrAnd_II_Z(2, 2));
        assertFalse(logicalAndOrAnd_II_Z(1, 1));
        assertTrue(logicalAndOrAnd_II_Z(1, 2));
        assertFalse(logicalAndOrAnd_II_Z(2, 1));
        TsClassX tsClass = new TsClassX(
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
    public void testLogicalOr_DD_Z() throws Exception {
        assertTrue(logicalOr_DD_Z(2D, 3D));
        assertTrue(logicalOr_DD_Z(3D, 2D));
        assertFalse(logicalOr_DD_Z(2D, 2D));
        TsClassX tsClassGTGT = new TsClassX(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClassX tsClassGEEQ = new TsClassX(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClassX tsClassEQLE = new TsClassX(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClassX tsClassLTLT = new TsClassX(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        TsClassX tsClassLENE = new TsClassX(
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
        TsClassX tsClassGTGT = new TsClassX(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClassX tsClassGEEQ = new TsClassX(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClassX tsClassEQLE = new TsClassX(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClassX tsClassLTLT = new TsClassX(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        TsClassX tsClassLENE = new TsClassX(
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
        TsClassX tsClassGTGT = new TsClassX(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClassX tsClassGEEQ = new TsClassX(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClassX tsClassEQLE = new TsClassX(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClassX tsClassLTLT = new TsClassX(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        TsClassX tsClassLENE = new TsClassX(
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
        TsClassX tsClassGTGT = new TsClassX(
                "return (a > b) || (b > a);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClassX tsClassGEEQ = new TsClassX(
                "return (a >= b) || (b == 1);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClassX tsClassEQLE = new TsClassX(
                "return (a == b) || (b <= 2);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClassX tsClassLTLT = new TsClassX(
                "return (a < b) || (b < a);",
                boolean.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        TsClassX tsClassLENE = new TsClassX(
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
}
