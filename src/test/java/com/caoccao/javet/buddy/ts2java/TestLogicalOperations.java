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

public class TestLogicalOperations extends BaseTestTs2Java {
    protected static Class<?> clazz = null;

    public TestLogicalOperations() {
        super();
        init();
    }

    protected void init() {
        if (clazz == null) {
            String tsCode = null;
            try {
                tsCode = getTsCode("test.logical.operations.ts");
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
  public logicalGE_IL_Z(IJ)Z
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
    public boolean logicalGE_IL_Z(int a, long b) {
        boolean c = a >= b;
        return c;
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

    @Test
    public void testLogicalEQEQ_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQEQ_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2));
        assertFalse((boolean) method.invoke(object, 2, 1));
        assertTrue((boolean) method.invoke(object, 1, 1));
    }

    @Test
    public void testLogicalEQ_II_Z() throws Exception {
        assertFalse(logicalEQ_II_Z(1, 2));
        Method method = clazz.getMethod("logicalEQ_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2));
        assertFalse((boolean) method.invoke(object, 2, 1));
        assertTrue((boolean) method.invoke(object, 1, 1));
    }

    @Test
    public void testLogicalGE_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2));
        assertTrue((boolean) method.invoke(object, 2, 1));
        assertTrue((boolean) method.invoke(object, 1, 1));
    }

    @Test
    public void testLogicalGE_IL_Z() throws Exception {
        assertFalse(logicalGE_IL_Z(1, 2L));
        Method method = clazz.getMethod("logicalGE_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2L));
        assertTrue((boolean) method.invoke(object, 2, 2L));
        assertTrue((boolean) method.invoke(object, 2, 1L));
    }

    @Test
    public void testLogicalGT_II_Z() throws Exception {
        assertFalse(logicalGT_II_Z(1, 2));
        Method method = clazz.getMethod("logicalGT_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2));
        assertTrue((boolean) method.invoke(object, 2, 1));
    }

    @Test
    public void testLogicalGT_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalGT_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2L));
        assertFalse((boolean) method.invoke(object, 2, 2L));
        assertTrue((boolean) method.invoke(object, 2, 1L));
    }

    @Test
    public void testLogicalLE_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalLE_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2));
        assertFalse((boolean) method.invoke(object, 2, 1));
        assertTrue((boolean) method.invoke(object, 1, 1));
    }

    @Test
    public void testLogicalLE_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalLE_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2L));
        assertTrue((boolean) method.invoke(object, 2, 2L));
        assertFalse((boolean) method.invoke(object, 2, 1L));
    }

    @Test
    public void testLogicalLT_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalLT_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2));
        assertFalse((boolean) method.invoke(object, 2, 1));
    }

    @Test
    public void testLogicalLT_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalLT_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2L));
        assertFalse((boolean) method.invoke(object, 2, 2L));
        assertFalse((boolean) method.invoke(object, 2, 1L));
    }

    @Test
    public void testLogicalNotEQEQ_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQEQ_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2));
        assertTrue((boolean) method.invoke(object, 2, 1));
        assertFalse((boolean) method.invoke(object, 1, 1));
    }

    @Test
    public void testLogicalNotEQ_II_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQ_II_Z", int.class, int.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2));
        assertTrue((boolean) method.invoke(object, 2, 1));
        assertFalse((boolean) method.invoke(object, 1, 1));
    }
}
