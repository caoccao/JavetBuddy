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
import com.caoccao.javet.values.reference.V8ValueObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestJavetReflectionObjectFactory {
    protected V8Runtime v8Runtime;

    @AfterEach
    public void afterEach() throws Exception {
        JavetReflectionObjectFactory.getInstance().clear();
        v8Runtime.lowMemoryNotification();
        v8Runtime.close();
    }

    @BeforeEach
    public void beforeEach() throws JavetException {
        v8Runtime = V8Host.getV8Instance().createV8Runtime();
        JavetProxyConverter javetProxyConverter = new JavetProxyConverter();
        javetProxyConverter.getConfig()
                .setReflectionObjectFactory(JavetReflectionObjectFactory.getInstance())
                .setProxyArrayEnabled(true)
                .setProxyListEnabled(true);
        v8Runtime.setConverter(javetProxyConverter);
    }

    @Test
    public void testExtendHandlerArrayList() throws JavetException {
        MockExtend mockExtend = new MockExtend(ArrayList.class);
        v8Runtime.getGlobalObject().bind(mockExtend);
        v8Runtime.getGlobalObject().set("ArrayList", ArrayList.class);
        String codeString = "let ChildArrayList = extend(ArrayList, {\n" +
                "  isEmpty: () => !$super.isEmpty(),\n" +
                "});\n" +
                "let list = new ChildArrayList([1, 2, 3]);\n" +
                "JSON.stringify([list.isEmpty(), list.size()]);";
        assertEquals("[true,3]", v8Runtime.getExecutor(codeString).executeString());
        v8Runtime.getExecutor("ChildArrayList = undefined; list = undefined;").executeVoid();
        v8Runtime.getGlobalObject().unbind(mockExtend);
        v8Runtime.getGlobalObject().delete("ArrayList");
    }

    @Test
    public void testExtendHandlerFile() throws JavetException {
        MockExtend mockExtend = new MockExtend(File.class);
        v8Runtime.getGlobalObject().bind(mockExtend);
        v8Runtime.getGlobalObject().set("File", File.class);
        String codeString = "let ChildFile = extend(File, {\n" +
                "  exists: () => !$super.exists(),\n" +
                "  isFile: () => true,\n" +
                "  isDirectory: () => true,\n" +
                "});\n" +
                "let file = new ChildFile('/tmp/not-exist-file');\n" +
                "JSON.stringify([file.exists(), file.isFile(), file.isDirectory(), file.getAbsolutePath()]);";
        assertEquals(
                "[true,true,true,\"" + (new File("/tmp/not-exist-file").getAbsolutePath().replace("\\", "\\\\")) + "\"]",
                v8Runtime.getExecutor(codeString).executeString());
        v8Runtime.getExecutor("ChildFile = undefined; file = undefined;").executeVoid();
        v8Runtime.getGlobalObject().unbind(mockExtend);
        v8Runtime.getGlobalObject().delete("File");
    }

    @Test
    public void testInvocationHandlerAutoCloseable() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(TestDynamicObjectAutoCloseable mockedDynamicClass) throws Exception {
                TestDynamicObjectAutoCloseable regularDynamicClass = new TestDynamicObjectAutoCloseable();
                assertEquals(0, regularDynamicClass.add(1, 2));
                assertEquals(3, mockedDynamicClass.add(1, 2), "Add should work.");
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
    public void testInvocationHandlerFile() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(File file) throws Exception {
                assertTrue(file.exists());
                assertTrue(file.isFile());
                assertTrue(file.isDirectory());
            }
        };
        v8Runtime.getGlobalObject().set("a", anonymous);
        String codeString = "a.test({\n" +
                "  $: ['/tmp/not-exist-file'],\n" +
                "  exists: () => !$super.exists(),\n" +
                "  isFile: () => true,\n" +
                "  isDirectory: () => true,\n" +
                "});";
        v8Runtime.getExecutor(codeString).executeVoid();
        v8Runtime.getGlobalObject().delete("a");
    }

    @Test
    public void testInvocationHandlerForceAutoCloseable() throws JavetException {
        IJavetAnonymous anonymous = new IJavetAnonymous() {
            @V8Function
            public void test(TestDynamicObjectForceCloseable mockedDynamicClass) throws Exception {
                TestDynamicObjectForceCloseable regularDynamicClass = new TestDynamicObjectForceCloseable();
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

    public static class MockExtend {
        private final Class<?> clazz;

        public MockExtend(Class<?> clazz) {
            this.clazz = clazz;
        }

        @V8Function
        public Class<?> extend(Class<?> clazz, V8ValueObject v8ValueObject) throws Exception {
            assertEquals(this.clazz, clazz);
            return JavetReflectionObjectFactory.getInstance().extend(clazz, v8ValueObject);
        }
    }

    public static class TestDynamicObjectAutoCloseable implements AutoCloseable {
        public int add(int a, int b) {
            return 0;
        }

        @Override
        public void close() throws Exception {
        }
    }

    public static class TestDynamicObjectForceCloseable {
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
