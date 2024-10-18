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

package com.caoccao.javet.buddy.interop.proxy;

import com.caoccao.javet.annotations.V8Function;
import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interfaces.IJavetAnonymous;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.converters.JavetProxyConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TestJavetReflectionObjectFactory {
    protected V8Runtime v8Runtime;

    @AfterEach
    public void afterEach() throws JavetException {
        v8Runtime.lowMemoryNotification();
        assertEquals(0, v8Runtime.getReferenceCount());
        v8Runtime.close();
    }

    @BeforeEach
    public void beforeEach() throws JavetException {
        v8Runtime = V8Host.getV8Instance().createV8Runtime();
        JavetProxyConverter javetProxyConverter = new JavetProxyConverter();
        javetProxyConverter.getConfig().setReflectionObjectFactory(JavetReflectionObjectFactory.getInstance());
        v8Runtime.setConverter(javetProxyConverter);
    }

    @Test
    public void testDynamicClassAutoCloseable() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(DynamicClassAutoCloseable mockedDynamicClass) throws Exception {
                DynamicClassAutoCloseable regularDynamicClass = new DynamicClassAutoCloseable();
                assertEquals(0, regularDynamicClass.add(1, 2));
                assertEquals(3, mockedDynamicClass.add(1, 2), "Add should work.");
                ((AutoCloseable) mockedDynamicClass).close();
            }
        };
        v8Runtime.getGlobalObject().set("a", anonymous);
        String codeString = "a.test({\n" +
                "  add: (a, b) => a + b,\n" +
                "});";
        v8Runtime.getExecutor(codeString).executeVoid();
        v8Runtime.getGlobalObject().delete("a");
    }

    @Test
    public void testDynamicClassForceCloseable() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(DynamicClassForceCloseable mockedDynamicClass) throws Exception {
                DynamicClassForceCloseable regularDynamicClass = new DynamicClassForceCloseable();
                assertEquals(0, regularDynamicClass.add(1, 2));
                assertEquals(3, mockedDynamicClass.add(1, 2), "Add should work.");
                assertEquals("a", regularDynamicClass.getDescription());
                assertEquals("b", mockedDynamicClass.getDescription());
                assertEquals("a", regularDynamicClass.getName());
                assertEquals("b", mockedDynamicClass.getName(), "String function without arguments should work.");
                assertEquals(1, regularDynamicClass.getNumber(2));
                assertEquals(2, mockedDynamicClass.getNumber(2), "Int function with arguments should work.");
                assertEquals("a", regularDynamicClass.getTitle());
                assertEquals("b", mockedDynamicClass.getTitle(), "Property as function should work.");
                assertEquals(0, regularDynamicClass.getValue());
                assertEquals(1, mockedDynamicClass.getValue(), "Getter for value should work.");
                mockedDynamicClass.setValue(2);
                assertEquals(2, mockedDynamicClass.getValue(), "Setter for value should work.");
                assertFalse(regularDynamicClass.isPassed());
                assertTrue(mockedDynamicClass.isPassed());
                ((AutoCloseable) mockedDynamicClass).close();
            }
        };
        v8Runtime.getGlobalObject().set("a", anonymous);
        String codeString = "a.test({\n" +
                "  add: (a, b) => a + b,\n" +
                "  description: 'b',\n" +
                "  getName: () => 'b',\n" +
                "  getNumber: (n) => n,\n" +
                "  getTitle: 'b',\n" +
                "  passed: true,\n" +
                "  value: 1,\n" +
                "});";
        v8Runtime.getExecutor(codeString).executeVoid();
        v8Runtime.getGlobalObject().delete("a");
    }

    @Test
    public void testFile() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(File file) throws Exception {
                assertTrue(file.exists());
                ((AutoCloseable) file).close();
            }
        };
        v8Runtime.getGlobalObject().set("a", anonymous);
        String codeString = "a.test({\n" +
                "  $: ['/tmp/not-exist-file'],\n" +
                "  exists: () => true,\n" +
                "});";
        v8Runtime.getExecutor(codeString).executeVoid();
        v8Runtime.getGlobalObject().delete("a");
    }

    public static class DynamicClassAutoCloseable implements AutoCloseable {
        public int add(int a, int b) {
            return 0;
        }

        @Override
        public void close() throws Exception {
        }
    }

    public static class DynamicClassForceCloseable {
        public int add(int a, int b) {
            return 0;
        }

        public String getDescription() {
            return "a";
        }

        public String getName() {
            return "a";
        }

        public int getNumber(int n) {
            return 1;
        }

        public String getTitle() {
            return "a";
        }

        public int getValue() {
            return 0;
        }

        public boolean isPassed() {
            return false;
        }

        public void setValue(int value) {
        }
    }
}
