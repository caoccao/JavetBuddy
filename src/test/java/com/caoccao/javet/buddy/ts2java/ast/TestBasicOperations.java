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

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBasicOperations extends BaseTestTs2Java {
    /*
  public add(II)I
   L0
    LINENUMBER 34 L0
    ILOAD 1
    ILOAD 2
    IADD
    IRETURN
     */
    public int add(int a, int b) {
        return a + b;
    }

    /*
  public add(IJ)J
   L0
    LINENUMBER 50 L0
    ILOAD 1
    I2L
    LLOAD 2
    LADD
    LRETURN
     */
    public long add(int a, long b) {
        return a + b;
    }

    /*
  public minus(II)I
   L0
    LINENUMBER 89 L0
    ILOAD 1
    ILOAD 2
    IADD
    INEG
    IRETURN
   L1
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestBasicOperations; L0 L1 0
    LOCALVARIABLE a I L0 L1 1
    LOCALVARIABLE b I L0 L1 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public int minus(int a, int b) {
        return -(a + b);
    }

    /*
  public pow(DD)D
   L0
    LINENUMBER 109 L0
    DLOAD 1
    DLOAD 3
    INVOKESTATIC java/lang/Math.pow (DD)D
    DRETURN
   L1
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestBasicOperations; L0 L1 0
    LOCALVARIABLE a D L0 L1 1
    LOCALVARIABLE b D L0 L1 3
    MAXSTACK = 4
    MAXLOCALS = 5
     */
    public double pow(double a, double b) {
        return Math.pow(a, b);
    }

    @Test
    public void testAdd_DD_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a + b + (-1);",
                double.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertEquals(1D + 2D - 1D, (double) tsClass.invoke(1D, 2D), 0.001D);
        assertEquals(1.23D + 2D - 1D, (double) tsClass.invoke(1.23D, 2D), 0.001D);
    }

    @Test
    public void testAdd_FF_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a + b + (-1);",
                float.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertEquals(1F + 2F - 1F, (float) tsClass.invoke(1F, 2F), 0.001F);
        assertEquals(1.23F + 2F - 1F, (float) tsClass.invoke(1.23F, 2F), 0.001F);
    }

    @Test
    public void testAdd_II_I() throws Exception {
        assertEquals(3, add(1, 2));
        TsClass tsClass = new TsClass(
                "return a + b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1 + 2, tsClass.invoke(1, 2));
        assertEquals(-1 + -2, tsClass.invoke(-1, -2));
    }

    @Test
    public void testAdd_II_J() throws Exception {
        assertEquals(3, add(1, 2));
        TsClass tsClass = new TsClass(
                "return a + b;",
                long.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        Method method = tsClass.getTestMethod();
        Object object = tsClass.getTestClass().getConstructor().newInstance();
        assertEquals(1L + 2L, method.invoke(object, 1, 2));
    }

    @Test
    public void testAdd_IJ_J() throws Exception {
        assertEquals(3, add(1, 2L));
        TsClass tsClass = new TsClass(
                "return a + b;",
                long.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(1 + 2L, tsClass.invoke(1, 2L));
    }

    @Test
    public void testAdd_JI_J() throws Exception {
        TsClass tsClass = new TsClass(
                "return a + b;",
                long.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1L + 2, tsClass.invoke(1L, 2));
    }

    @Test
    public void testDivide_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a / b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 / 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testMinus_II_I() throws Exception {
        assertEquals(-5, minus(3, 2));
        TsClass tsClass = new TsClass(
                "return -(a + b);",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(-5, tsClass.invoke(3, 2));
    }

    @Test
    public void testMinus_I_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return -(a + (-1));",
                int.class,
                TsMethodArgument.of("a", int.class));
        assertEquals(-2, tsClass.invoke(3));
    }

    @Test
    public void testMinus_L_L() throws Exception {
        TsClass tsClass = new TsClass(
                "return -a;",
                long.class,
                TsMethodArgument.of("a", long.class));
        assertEquals(-2L, tsClass.invoke(2L));
    }

    @Test
    public void testMinus_Minus_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return -(-(a + b));",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(5, tsClass.invoke(3, 2));
    }

    @Test
    public void testMod_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a % b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 % 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testMultiply_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a * b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 * 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testPow_DD_D() throws Exception {
        assertEquals(8D, pow(2D, 3D), 0.001D);
        TsClass tsClass = new TsClass(
                "return a ** b;",
                double.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertEquals(8D, (double) tsClass.invoke(2D, 3D), 0.001D);
    }

    @Test
    public void testPow_II_D() throws Exception {
        TsClass tsClass = new TsClass(
                "return a ** b;",
                double.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(8D, (double) tsClass.invoke(2, 3), 0.001D);
    }

    @Test
    public void testShiftLeft_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a << b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 << 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testShiftRight_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >> b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 >> 1, tsClass.invoke(3, 1));
    }

    @Test
    public void testSubtract_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a - b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 - 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testZeroFillShiftRight_II_I() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >>> b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 >>> 2, tsClass.invoke(3, 2));
        assertEquals(-3 >>> 2, tsClass.invoke(-3, 2));
    }

    @Test
    public void testZeroFillShiftRight_JJ_J() throws Exception {
        TsClass tsClass = new TsClass(
                "return a >>> b;",
                long.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(3L >>> 2L, tsClass.invoke(3L, 2L));
        assertEquals(-3L >>> 2L, tsClass.invoke(-3L, 2L));
    }
}
