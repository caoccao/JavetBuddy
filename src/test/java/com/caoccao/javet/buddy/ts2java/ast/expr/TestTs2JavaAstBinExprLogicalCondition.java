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

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestTs2JavaAstBinExprLogicalCondition extends BaseTestTs2Java {
    /*
  public andAndAnd_ZZZ_Z(ZZZ)Z
   L0
    LINENUMBER 30 L0
    ILOAD 1
    IFEQ L1
    ILOAD 2
    IFEQ L1
    ILOAD 3
    IFEQ L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b Z L0 L3 2
    LOCALVARIABLE c Z L0 L3 3
    MAXSTACK = 1
    MAXLOCALS = 4
     */
    public boolean andAndAnd_ZZZ_Z(boolean a, boolean b, boolean c) {
        return true && a && b && c;
    }

    /*
  public andOrAnd_II_Z(II)Z
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
    public boolean andOrAnd_II_Z(int a, int b) {
        return (a == b && a > 1) || (a != b && b > 1);
    }

    /*
  public and_II_Z(II)Z
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
    public boolean and_II_Z(int a, int b) {
        return a == b && a > 1;
    }

    /*
  public and_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 54 L0
    ILOAD 1
    IFEQ L1
    ILOAD 2
    IFEQ L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b Z L0 L3 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean and_ZZ_Z(boolean a, boolean b) {
        return a && b;
    }

    /*
  public notAndAndAnd_ZZZ_Z(ZZZ)Z
   L0
    LINENUMBER 155 L0
    ILOAD 1
    IFEQ L1
    ILOAD 2
    IFEQ L1
    ILOAD 3
    IFNE L2
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
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L4 0
    LOCALVARIABLE a Z L0 L4 1
    LOCALVARIABLE b Z L0 L4 2
    LOCALVARIABLE c Z L0 L4 3
    MAXSTACK = 1
    MAXLOCALS = 4
     */
    public boolean notAndAndAnd_ZZZ_Z(boolean a, boolean b, boolean c) {
        return !(a && b && c);
    }

    /*
  public notAnd_II_Z(II)Z
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
    public boolean notAnd_II_Z(int a, int b) {
        return !(a == b && a > 1);
    }

    /*
  public notOr_II_Z(II)Z
   L0
    LINENUMBER 85 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPEQ L1
    ILOAD 1
    ICONST_1
    IF_ICMPGT L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean notOr_II_Z(int a, int b) {
        return !(a == b || a > 1);
    }

    /*
  public not_LogicalAnd_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 56 L0
    ILOAD 1
    IFNE L1
    ILOAD 2
    IFNE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b Z L0 L3 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean notOr_ZZ_Z(boolean a, boolean b) {
        return !(a || b);
    }

    /*
  public orAndOr_II_Z(II)Z
   L0
    LINENUMBER 212 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPEQ L1
    ILOAD 1
    ICONST_1
    IF_ICMPLE L2
   L1
   FRAME SAME
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L3
    ILOAD 2
    ICONST_1
    IF_ICMPLE L2
   L3
   FRAME SAME
    ICONST_1
    GOTO L4
   L2
   FRAME SAME
    ICONST_0
   L4
   FRAME SAME1 I
    IRETURN
   L5
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L5 0
    LOCALVARIABLE a I L0 L5 1
    LOCALVARIABLE b I L0 L5 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean orAndOr_II_Z(int a, int b) {
        return (a == b || a > 1) && (a != b || b > 1);
    }

    /*
  public or_DD_Z(DD)Z
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
    public boolean or_DD_Z(double a, double b) {
        return (a > b) || (b > a);
    }

    public boolean or_FF_Z(float a, float b) {
        return (a > b) || (b > a);
    }

    /*
  public or_II_Z(II)Z
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
    public boolean or_II_Z(int a, int b) {
        return (a > b) || (b > a);
    }

    /*
  public or_JJ_Z(JJ)Z
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
    public boolean or_JJ_Z(long a, long b) {
        return (a > b) || (b > a);
    }

    /*
  public or_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 81 L0
    ILOAD 1
    IFNE L1
    ILOAD 2
    IFEQ L2
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
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L4 0
    LOCALVARIABLE a Z L0 L4 1
    LOCALVARIABLE b Z L0 L4 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean or_ZZ_Z(boolean a, boolean b) {
        return a || b;
    }

    @Test
    public void testAndAndAnd_ZZZ_Z() throws Exception {
        assertTrue(andAndAnd_ZZZ_Z(true, true, true));
        assertFalse(andAndAnd_ZZZ_Z(true, false, true));
        tsClass = new TsClass(
                "return true && a && b && c;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class),
                TsMethodArgument.of("c", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true, true));
        assertFalse((boolean) tsClass.invoke(true, false, true));
    }

    @Test
    public void testAndBool_Z_Z() throws Exception {
        tsClass = new TsClass(
                "return a && false;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertFalse((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
        tsClass = new TsClass(
                "return a && true;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
    }

    @Test
    public void testAndOrAnd_II_Z() throws Exception {
        assertTrue(andOrAnd_II_Z(2, 2));
        assertFalse(andOrAnd_II_Z(1, 1));
        assertTrue(andOrAnd_II_Z(1, 2));
        assertFalse(andOrAnd_II_Z(2, 1));
        tsClass = new TsClass(
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
    public void testAnd_II_Z() throws Exception {
        assertTrue(and_II_Z(2, 2));
        assertFalse(and_II_Z(2, 3));
        assertFalse(and_II_Z(1, 1));
        tsClass = new TsClass(
                "return a == b && a > 1;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(2, 3));
        assertFalse((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testAnd_ZZ_Z() throws Exception {
        assertTrue(and_ZZ_Z(true, true));
        assertFalse(and_ZZ_Z(true, false));
        assertFalse(and_ZZ_Z(false, false));
        tsClass = new TsClass(
                "return a && b;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true));
        assertFalse((boolean) tsClass.invoke(true, false));
        assertFalse((boolean) tsClass.invoke(false, false));
    }

    @Test
    public void testBoolAndBool_Z_Z() throws Exception {
        tsClass = new TsClass("return true && true;");
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return false && true;");
        assertFalse((boolean) tsClass.invoke());
        tsClass = new TsClass("return true && false;");
        assertFalse((boolean) tsClass.invoke());
        tsClass = new TsClass("return false && false;");
        assertFalse((boolean) tsClass.invoke());
    }

    @Test
    public void testBoolAnd_Z_Z() throws Exception {
        tsClass = new TsClass(
                "return false && a;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertFalse((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
        tsClass = new TsClass(
                "return true && a;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
    }

    @Test
    public void testNotAndAndAnd_ZZZ_Z() throws Exception {
        assertFalse(notAndAndAnd_ZZZ_Z(true, true, true));
        assertTrue(notAndAndAnd_ZZZ_Z(true, false, true));
        tsClass = new TsClass(
                "return !(a && b && c);",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class),
                TsMethodArgument.of("c", boolean.class));
        assertFalse((boolean) tsClass.invoke(true, true, true));
        assertTrue((boolean) tsClass.invoke(true, false, true));
    }

    @Test
    public void testNotOr_II_Z() throws Exception {
        assertFalse(notOr_II_Z(2, 2));
        assertFalse(notOr_II_Z(2, 3));
        assertFalse(notOr_II_Z(1, 1));
        assertTrue(notOr_II_Z(0, 1));
        tsClass = new TsClass(
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
    public void testNotOr_ZZ_Z() throws Exception {
        assertFalse(notOr_ZZ_Z(true, true));
        assertFalse(notOr_ZZ_Z(true, false));
        assertFalse(notOr_ZZ_Z(false, true));
        assertTrue(notOr_ZZ_Z(false, false));
        tsClass = new TsClass(
                "return !(a || b);",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertFalse((boolean) tsClass.invoke(true, true));
        assertFalse((boolean) tsClass.invoke(true, false));
        assertFalse((boolean) tsClass.invoke(false, true));
        assertTrue((boolean) tsClass.invoke(false, false));
    }

    @Test
    public void testNot_And_II_Z() throws Exception {
        assertFalse(notAnd_II_Z(2, 2));
        assertTrue(notAnd_II_Z(2, 3));
        assertTrue(notAnd_II_Z(1, 1));
        assertTrue(notAnd_II_Z(0, 1));
        tsClass = new TsClass(
                "return !(a == b && a > 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(2, 2));
        assertTrue((boolean) tsClass.invoke(2, 3));
        assertTrue((boolean) tsClass.invoke(1, 1));
        assertTrue((boolean) tsClass.invoke(0, 1));
    }

    //    @Test
    public void testOrAndOr_II_Z() throws Exception {
        assertTrue(orAndOr_II_Z(2, 2));
        assertFalse(orAndOr_II_Z(1, 1));
        assertFalse(orAndOr_II_Z(1, 2));
        assertTrue(orAndOr_II_Z(2, 1));
        enableLogging();
        tsClass = new TsClass(
                "return (a == b || a > 1) && (a != b || b > 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(1, 1));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertTrue((boolean) tsClass.invoke(2, 1));
    }

    @Test
    public void testOr_DD_Z() throws Exception {
        assertTrue(or_DD_Z(2D, 3D));
        assertTrue(or_DD_Z(3D, 2D));
        assertFalse(or_DD_Z(2D, 2D));
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
    public void testOr_FF_Z() throws Exception {
        assertTrue(or_FF_Z(2F, 3F));
        assertTrue(or_FF_Z(3F, 2F));
        assertFalse(or_FF_Z(2F, 2F));
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
    public void testOr_II_Z() throws Exception {
        assertTrue(or_II_Z(2, 3));
        assertTrue(or_II_Z(3, 2));
        assertFalse(or_II_Z(2, 2));
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
    public void testOr_JJ_Z() throws Exception {
        assertTrue(or_JJ_Z(2L, 3L));
        assertTrue(or_JJ_Z(3L, 2L));
        assertFalse(or_JJ_Z(2L, 2L));
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
    public void testOr_ZZ_Z() throws Exception {
        assertTrue(or_ZZ_Z(true, true));
        assertTrue(or_ZZ_Z(true, false));
        assertTrue(or_ZZ_Z(false, true));
        assertFalse(or_ZZ_Z(false, false));
        tsClass = new TsClass(
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
