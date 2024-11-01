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

    @Test
    public void testLogicalEQEQ_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQEQ_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1D, 2D));
        assertFalse((boolean) method.invoke(object, 2D, 1D));
        assertTrue((boolean) method.invoke(object, 1D, 1D));
        assertTrue((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalEQEQ_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQEQ_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1F, 2F));
        assertFalse((boolean) method.invoke(object, 2F, 1F));
        assertTrue((boolean) method.invoke(object, 1F, 1F));
        assertTrue((boolean) method.invoke(object, 1.23F, 1.23F));
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
    public void testLogicalEQEQ_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQEQ_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2L));
        assertFalse((boolean) method.invoke(object, 2, 1L));
        assertTrue((boolean) method.invoke(object, 1, 1L));
    }

    @Test
    public void testLogicalEQ_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQ_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1D, 2D));
        assertFalse((boolean) method.invoke(object, 2D, 1D));
        assertTrue((boolean) method.invoke(object, 1D, 1D));
        assertTrue((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalEQ_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQ_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1F, 2F));
        assertFalse((boolean) method.invoke(object, 2F, 1F));
        assertTrue((boolean) method.invoke(object, 1F, 1F));
        assertTrue((boolean) method.invoke(object, 1.23F, 1.23F));
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
    public void testLogicalEQ_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalEQ_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1, 2L));
        assertFalse((boolean) method.invoke(object, 2, 1L));
        assertTrue((boolean) method.invoke(object, 1, 1L));
    }

    @Test
    public void testLogicalGE_BB_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_BB_Z", byte.class, byte.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(byte.class, method.getParameters()[0].getType());
        assertEquals(byte.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, (byte) 1, (byte) 2));
        assertTrue((boolean) method.invoke(object, (byte) 2, (byte) 1));
        assertTrue((boolean) method.invoke(object, (byte) 1, (byte) 1));
    }

    @Test
    public void testLogicalGE_CC_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_CC_Z", char.class, char.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(char.class, method.getParameters()[0].getType());
        assertEquals(char.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, (char) 1, (char) 2));
        assertTrue((boolean) method.invoke(object, (char) 2, (char) 1));
        assertTrue((boolean) method.invoke(object, (char) 1, (char) 1));
    }

    @Test
    public void testLogicalGE_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1D, 2D));
        assertTrue((boolean) method.invoke(object, 2D, 1D));
        assertTrue((boolean) method.invoke(object, 1D, 1D));
        assertTrue((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalGE_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1F, 2F));
        assertTrue((boolean) method.invoke(object, 2F, 1F));
        assertTrue((boolean) method.invoke(object, 1F, 1F));
        assertTrue((boolean) method.invoke(object, 1.23F, 1.23F));
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
    public void testLogicalGE_SS_Z() throws Exception {
        Method method = clazz.getMethod("logicalGE_SS_Z", short.class, short.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(short.class, method.getParameters()[0].getType());
        assertEquals(short.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, (short) 1, (short) 2));
        assertTrue((boolean) method.invoke(object, (short) 2, (short) 1));
        assertTrue((boolean) method.invoke(object, (short) 1, (short) 1));
    }

    @Test
    public void testLogicalGT_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalGT_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1D, 2D));
        assertFalse((boolean) method.invoke(object, 1.23D, 1.23D));
        assertTrue((boolean) method.invoke(object, 2D, 1D));
    }

    @Test
    public void testLogicalGT_FF_Z() throws Exception {
        assertFalse(logicalGT_FF_Z(1F, 2F));
        assertFalse(logicalGT_FF_Z(1.23F, 1.23F));
        Method method = clazz.getMethod("logicalGT_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertFalse((boolean) method.invoke(object, 1F, 2F));
        assertFalse((boolean) method.invoke(object, 1.23F, 1.23F));
        assertTrue((boolean) method.invoke(object, 2F, 1F));
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
    public void testLogicalLE_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalLE_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1D, 2D));
        assertFalse((boolean) method.invoke(object, 2D, 1D));
        assertTrue((boolean) method.invoke(object, 1D, 1D));
        assertTrue((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalLE_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalLE_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1F, 2F));
        assertFalse((boolean) method.invoke(object, 2F, 1F));
        assertTrue((boolean) method.invoke(object, 1F, 1F));
        assertTrue((boolean) method.invoke(object, 1.23F, 1.23F));
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
    public void testLogicalLT_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalLT_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1D, 2D));
        assertFalse((boolean) method.invoke(object, 2D, 1D));
        assertFalse((boolean) method.invoke(object, 1D, 1D));
        assertFalse((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalLT_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalLT_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1F, 2F));
        assertFalse((boolean) method.invoke(object, 2F, 1F));
        assertFalse((boolean) method.invoke(object, 1F, 1F));
        assertFalse((boolean) method.invoke(object, 1.23F, 1.23F));
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
        assertFalse((boolean) method.invoke(object, 1, 1));
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
    public void testLogicalNotEQEQ_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQEQ_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1D, 2D));
        assertTrue((boolean) method.invoke(object, 2D, 1D));
        assertFalse((boolean) method.invoke(object, 1D, 1D));
        assertFalse((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalNotEQEQ_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQEQ_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1F, 2F));
        assertTrue((boolean) method.invoke(object, 2F, 1F));
        assertFalse((boolean) method.invoke(object, 1F, 1F));
        assertFalse((boolean) method.invoke(object, 1.23F, 1.23F));
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
    public void testLogicalNotEQEQ_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQEQ_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2L));
        assertTrue((boolean) method.invoke(object, 2, 1L));
        assertFalse((boolean) method.invoke(object, 1, 1L));
    }

    @Test
    public void testLogicalNotEQ_DD_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQ_DD_Z", double.class, double.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(double.class, method.getParameters()[0].getType());
        assertEquals(double.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1D, 2D));
        assertTrue((boolean) method.invoke(object, 2D, 1D));
        assertFalse((boolean) method.invoke(object, 1D, 1D));
        assertFalse((boolean) method.invoke(object, 1.23D, 1.23D));
    }

    @Test
    public void testLogicalNotEQ_FF_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQ_FF_Z", float.class, float.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(float.class, method.getParameters()[0].getType());
        assertEquals(float.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1F, 2F));
        assertTrue((boolean) method.invoke(object, 2F, 1F));
        assertFalse((boolean) method.invoke(object, 1F, 1F));
        assertFalse((boolean) method.invoke(object, 1.23F, 1.23F));
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

    @Test
    public void testLogicalNotEQ_IL_Z() throws Exception {
        Method method = clazz.getMethod("logicalNotEQ_IL_Z", int.class, long.class);
        assertNotNull(method);
        assertEquals(boolean.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(long.class, method.getParameters()[1].getType());
        Object object = clazz.getConstructor().newInstance();
        assertTrue((boolean) method.invoke(object, 1, 2L));
        assertTrue((boolean) method.invoke(object, 2, 1L));
        assertFalse((boolean) method.invoke(object, 1, 1L));
    }
}