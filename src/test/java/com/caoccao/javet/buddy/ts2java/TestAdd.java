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

public class TestAdd extends BaseTestTs2Java {
    protected Class<?> clazz;

    public TestAdd() {
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

    protected void init() {
        String tsCode = null;
        try {
            tsCode = getTsCode("test.add.ts");
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

    @Test
    public void testAdd_II_I() throws Exception {
        assertEquals(3, add(1, 2));
        Method method = clazz.getMethod("add_II_I", int.class, int.class);
        assertNotNull(method);
        assertEquals(int.class, method.getReturnType());
        assertEquals(2, method.getParameterCount());
        assertEquals(int.class, method.getParameters()[0].getType());
        assertEquals(int.class, method.getParameters()[1].getType());
    }
}
