/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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
import com.caoccao.javet.buddy.ts2java.Ts2Java;
import com.caoccao.javet.swc4j.exceptions.Swc4jCoreException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBasicOperations extends BaseTestTs2Java {
    protected static Class<?> clazz = null;

    public TestBasicOperations() {
        super();
        init();
    }

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

    protected void init() {
        if (clazz == null) {
            String tsCode = null;
            try {
                tsCode = getTsCode("test.basic.operations.ts");
            } catch (IOException e) {
                fail(e);
            }
            assertNotNull(tsCode);
            Ts2Java ts2Java = new Ts2Java("com.test", tsCode);
            try {
                ts2Java.transpile();
            } catch (Swc4jCoreException e) {
                fail(e);
            }
            List<Class<?>> classes = ts2Java.getClasses();
            assertEquals(1, classes.size());
            clazz = classes.get(0);
            assertEquals("Test", clazz.getSimpleName());
            assertEquals("com.test.Test", clazz.getName());
        }
    }

    @Test
    public void testAdd_DD_I() throws Exception {
        Method method = clazz.getMethod("add_DD_I", double.class, double.class);
        assertNotNull(method);
        assertEquals(double.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1D + 2D - 1D, (double) method.invoke(object, 1D, 2D), 0.001D);
        assertEquals(1.23D + 2D - 1D, (double) method.invoke(object, 1.23D, 2D), 0.001D);
    }

    @Test
    public void testAdd_II_I() throws Exception {
        assertEquals(3, add(1, 2));
        Method method = clazz.getMethod("add_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1 + 2, method.invoke(object, 1, 2));
        assertEquals(-1 + -2, method.invoke(object, -1, -2));
    }

    @Test
    public void testAdd_II_L() throws Exception {
        assertEquals(3, add(1, 2));
        Method method = clazz.getMethod("add_II_L", int.class, int.class);
        assertNotNull(method);
        assertEquals(long.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1L + 2L, method.invoke(object, 1, 2));
    }

    @Test
    public void testAdd_IL_L() throws Exception {
        assertEquals(3, add(1, 2L));
        Method method = clazz.getMethod("add_IL_L", int.class, long.class);
        assertNotNull(method);
        assertEquals(long.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1 + 2L, method.invoke(object, 1, 2L));
    }

    @Test
    public void testAdd_LI_L() throws Exception {
        Method method = clazz.getMethod("add_LI_L", long.class, int.class);
        assertNotNull(method);
        assertEquals(long.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(long.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1L + 2, method.invoke(object, 1L, 2));
    }

    @Test
    public void testDivide_II_I() throws Exception {
        Method method = clazz.getMethod("divide_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 / 2, method.invoke(object, 3, 2));
    }

    @Test
    public void testMod_II_I() throws Exception {
        Method method = clazz.getMethod("mod_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 % 2, method.invoke(object, 3, 2));
    }

    @Test
    public void testMultiply_II_I() throws Exception {
        Method method = clazz.getMethod("multiply_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 * 2, method.invoke(object, 3, 2));
    }

    @Test
    public void testShiftLeft_II_I() throws Exception {
        Method method = clazz.getMethod("shiftLeft_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 << 2, method.invoke(object, 3, 2));
    }

    @Test
    public void testShiftRight_II_I() throws Exception {
        Method method = clazz.getMethod("shiftRight_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 >> 1, method.invoke(object, 3, 1));
    }

    @Test
    public void testSubtract_II_I() throws Exception {
        Method method = clazz.getMethod("subtract_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(3 - 2, method.invoke(object, 3, 2));
    }
}
