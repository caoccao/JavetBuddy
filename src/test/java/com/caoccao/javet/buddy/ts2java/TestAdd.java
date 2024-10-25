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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestAdd extends BaseTestTs2Java {
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

    @Test
    public void testAdd() throws Exception {
        assertEquals(3, add(1, 2));
        String tsCode = getTsCode("test.add.ts");
        assertNotNull(tsCode);
        Ts2Java ts2Java = new Ts2Java("com.test", tsCode);
        ts2Java.transpile();
        Class<?> javaClass = ts2Java.getJavaClass();
        assertNotNull(javaClass);
        assertEquals("Test", javaClass.getSimpleName());
        assertEquals("com.test.Test", javaClass.getName());
    }
}