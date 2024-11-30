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
import com.caoccao.javet.buddy.ts2java.TsClassX;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTs2JavaAstBinExpr extends BaseTestTs2Java {
    /*
  public add_II_I(II)I
   L0
    LINENUMBER 34 L0
    ILOAD 1
    ILOAD 2
    IADD
    IRETURN
     */
    public int add_II_I(int a, int b) {
        return a + b;
    }

    /*
  public add_IJ_J(IJ)J
   L0
    LINENUMBER 50 L0
    ILOAD 1
    I2L
    LLOAD 2
    LADD
    LRETURN
     */
    public long add_IJ_J(int a, long b) {
        return a + b;
    }

    @Test
    public void testAdd_DD_I() throws Exception {
        tsClass = new TsClassX(
                "return a + b + (-1);",
                double.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertEquals(1D + 2D - 1D, (double) tsClass.invoke(1D, 2D), 0.001D);
        assertEquals(1.23D + 2D - 1D, (double) tsClass.invoke(1.23D, 2D), 0.001D);
    }

    @Test
    public void testAdd_FF_I() throws Exception {
        tsClass = new TsClassX(
                "return a + b + (-1);",
                float.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertEquals(1F + 2F - 1F, (float) tsClass.invoke(1F, 2F), 0.001F);
        assertEquals(1.23F + 2F - 1F, (float) tsClass.invoke(1.23F, 2F), 0.001F);
    }

    @Test
    public void testAdd_II_I() throws Exception {
        assertEquals(3, add_II_I(1, 2));
        tsClass = new TsClassX(
                "return a + b;",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1 + 2, tsClass.invoke(1, 2));
        assertEquals(-1 + -2, tsClass.invoke(-1, -2));
    }

    @Test
    public void testAdd_II_J() throws Exception {
        tsClass = new TsClassX(
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
        assertEquals(3, add_IJ_J(1, 2L));
        tsClass = new TsClassX(
                "return a + b;",
                long.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(1 + 2L, tsClass.invoke(1, 2L));
    }

    @Test
    public void testAdd_JI_J() throws Exception {
        tsClass = new TsClassX(
                "return a + b;",
                long.class,
                TsMethodArgument.of("a", long.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1L + 2, tsClass.invoke(1L, 2));
    }
}
