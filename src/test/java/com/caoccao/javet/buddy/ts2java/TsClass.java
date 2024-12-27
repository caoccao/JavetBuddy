/*
 * Copyright (c) 2024-2025. caoccao.com Sam Cao
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
import com.caoccao.javet.swc4j.utils.SimpleList;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public final class TsClass {
    private final List<TsMethodArgument> arguments;
    private final String body;
    private final Class<?> returnType;
    private Object instance;
    private Class<?> testClass;
    private Method testMethod;

    public TsClass(String body, TsMethodArgument... arguments) {
        this(body, null, arguments);
    }

    public TsClass(String body, Class<?> returnType, TsMethodArgument... arguments) {
        assertNotNull(body);
        assertNotNull(arguments);
        this.arguments = SimpleList.immutableOf(arguments);
        this.body = body;
        this.returnType = returnType;
        instance = null;
        testClass = null;
        testMethod = null;
        init();
    }

    public List<TsMethodArgument> getArguments() {
        return arguments;
    }

    public String getBody() {
        return body;
    }

    public Object getInstance() throws Exception {
        if (instance == null) {
            instance = testClass.getConstructor().newInstance();
        }
        return instance;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public Method getTestMethod() {
        return testMethod;
    }

    private void init() {
        String argumentsString = arguments.stream()
                .map(TsMethodArgument::toString)
                .collect(Collectors.joining(", "));
        String codeString = returnType == null
                ? SimpleFreeMarkerFormat.format(
                "class Test {\n" +
                        "  public test(${argumentsString}) {\n" +
                        "    ${body}\n" +
                        "  }\n" +
                        "}\n",
                SimpleMap.of(
                        "argumentsString", argumentsString,
                        "body", body))
                : SimpleFreeMarkerFormat.format(
                "class Test {\n" +
                        "  public test(${argumentsString}): ${returnType} {\n" +
                        "    ${body}\n" +
                        "  }\n" +
                        "}\n",
                SimpleMap.of(
                        "argumentsString", argumentsString,
                        "returnType", returnType.getName(),
                        "body", body));
        Ts2Java ts2Java = new Ts2Java("com.test", codeString);
        try {
            ts2Java.transpile();
        } catch (Swc4jCoreException e) {
            fail(e);
        }
        List<Class<?>> classes = ts2Java.getClasses();
        assertEquals(1, classes.size());
        testClass = classes.get(0);
        assertEquals("Test", testClass.getSimpleName());
        assertEquals("com.test.Test", testClass.getName());
        try {
            testMethod = testClass.getMethod(
                    "test",
                    arguments.stream().map(TsMethodArgument::getType).toArray(Class[]::new));
            if (returnType == null) {
                assertNotNull(testMethod.getReturnType());
            } else {
                assertEquals(returnType, testMethod.getReturnType());
            }
            final int argumentsLength = arguments.size();
            final Parameter[] parameters = testMethod.getParameters();
            assertEquals(argumentsLength, testMethod.getParameterCount());
            assertEquals(argumentsLength, parameters.length);
            IntStream.range(0, argumentsLength)
                    .forEach(i -> assertEquals(
                            arguments.get(i).getType(),
                            parameters[i].getType(),
                            "Argument[" + i + "] type mismatch"));
        } catch (NoSuchMethodException e) {
            fail(e);
        }
    }

    public Object invoke(Object... arguments) throws Exception {
        return testMethod.invoke(getInstance(), arguments);
    }
}
