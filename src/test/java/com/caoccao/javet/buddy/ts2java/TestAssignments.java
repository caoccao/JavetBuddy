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

package com.caoccao.javet.buddy.ts2java;

import com.caoccao.javet.swc4j.exceptions.Swc4jCoreException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAssignments extends BaseTestTs2Java {
    protected static Class<?> clazz = null;

    public TestAssignments() {
        super();
        init();
    }

    /*
  public assignAndCast(IJ)D
   L0
    LINENUMBER 30 L0
    ILOAD 1
    I2L
    LSTORE 4
   L1
    LINENUMBER 31 L1
    LLOAD 2
    LSTORE 6
   L2
    LINENUMBER 32 L2
    LLOAD 4
    LLOAD 6
    LADD
    L2D
    DRETURN
     */
    public double assignAndCast(int a, long b) {
        long c = a;
        long d = b;
        return c + d;
    }

    protected void init() {
        if (clazz == null) {
            String tsCode = null;
            try {
                tsCode = getTsCode("test.assignments.ts");
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

//    @Test
    public void testAssignAndCast() throws Exception {
        assertEquals(3L, assignAndCast(1, 2L));
        Method method = clazz.getMethod("assignAndCast", int.class, long.class);
        assertNotNull(method);
        assertEquals(double.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertEquals(1 + 2L, method.invoke(object, 1, 2L));
    }
}
