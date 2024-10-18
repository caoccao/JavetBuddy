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

import com.caoccao.javet.values.reference.V8ValueObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * The type Dynamic object force closeable invocation handler.
 *
 * @since 0.1.0
 */
public class DynamicObjectForceCloseableInvocationHandler
        extends DynamicObjectAutoCloseableInvocationHandler
        implements AutoCloseable {

    /**
     * Instantiates a new Dynamic object force closeable invocation handler.
     *
     * @param type          the type
     * @param v8ValueObject the V8 value object
     * @since 0.1.0
     */
    public DynamicObjectForceCloseableInvocationHandler(Class<?> type, V8ValueObject v8ValueObject) {
        super(type, v8ValueObject);
    }

    @RuntimeType
    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    protected Class<?> getObjectClass() {
        try (DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
                .subclass(type, CONSTRUCTOR_STRATEGY)
                .implement(AutoCloseable.class)
                .method(ElementMatchers.isPublic())
                .intercept(MethodDelegation.to(this))
                .make()) {
            return unloadedType.load(getClass().getClassLoader()).getLoaded();
        }
    }

    @RuntimeType
    @Override
    public Object intercept(
            @Origin Method method,
            @AllArguments Object[] arguments,
            @SuperCall Callable<Object> callable) throws Exception {
        return super.intercept(method, arguments, callable);
    }
}
