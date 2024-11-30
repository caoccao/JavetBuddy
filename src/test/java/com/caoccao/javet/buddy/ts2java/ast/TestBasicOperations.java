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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBasicOperations extends BaseTestTs2Java {
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
    public void testMod_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a % b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 % 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testMultiply_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a * b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 * 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testPow_DD_D() throws Exception {
        assertEquals(8D, pow(2D, 3D), 0.001D);
        TsClassX tsClass = new TsClassX(
                "return a ** b;",
                double.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertEquals(8D, (double) tsClass.invoke(2D, 3D), 0.001D);
    }

    @Test
    public void testPow_II_D() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a ** b;",
                double.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(8D, (double) tsClass.invoke(2, 3), 0.001D);
    }

    @Test
    public void testShiftLeft_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a << b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 << 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testShiftRight_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a >> b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 >> 1, tsClass.invoke(3, 1));
    }

    @Test
    public void testSubtract_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a - b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 - 2, tsClass.invoke(3, 2));
    }

    @Test
    public void testZeroFillShiftRight_II_I() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a >>> b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(3 >>> 2, tsClass.invoke(3, 2));
        assertEquals(-3 >>> 2, tsClass.invoke(-3, 2));
    }

    @Test
    public void testZeroFillShiftRight_JJ_J() throws Exception {
        TsClassX tsClass = new TsClassX(
                "return a >>> b;",
                long.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(3L >>> 2L, tsClass.invoke(3L, 2L));
        assertEquals(-3L >>> 2L, tsClass.invoke(-3L, 2L));
    }
}
