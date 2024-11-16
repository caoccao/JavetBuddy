/*
 * Copyright (c) 2022-2024. caoccao.com Sam Cao
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

import com.caoccao.javet.interfaces.IJavetLogger;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.callback.IJavetDirectCallable;
import com.caoccao.javet.interop.callback.JavetCallbackContext;
import com.caoccao.javet.interop.callback.JavetCallbackType;
import com.caoccao.javet.interop.proxy.IJavetReflectionObjectFactory;
import com.caoccao.javet.utils.JavetDefaultLogger;
import com.caoccao.javet.utils.JavetResourceUtils;
import com.caoccao.javet.utils.V8ValueUtils;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The type Javet dynamic object factory.
 *
 * @since 0.1.0
 */
public final class JavetReflectionObjectFactory implements IJavetReflectionObjectFactory {
    private static final String EXTEND = "extend";
    private static final JavetReflectionObjectFactory instance = new JavetReflectionObjectFactory();
    private final AtomicLong currentExtendHandle;
    private final Map<Long, DynamicObjectExtendHandler<?>> extendHandlerMap;
    private final Map<Long, DynamicObjectInvocationHandler<?>> invocationHandlerMap;
    private final IJavetLogger logger;

    private JavetReflectionObjectFactory() {
        logger = new JavetDefaultLogger(getClass().getName());
        currentExtendHandle = new AtomicLong();
        invocationHandlerMap = new ConcurrentHashMap<>();
        extendHandlerMap = new ConcurrentHashMap<>();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @since 0.1.0
     */
    public static JavetReflectionObjectFactory getInstance() {
        return instance;
    }

    /**
     * Clear the internal handlers and return the cleared handler count.
     *
     * @return the cleared handler count
     * @throws Exception the exception
     * @since 0.4.0
     */
    public int clear() throws Exception {
        final int invocationHandlerCount = invocationHandlerMap.size();
        if (invocationHandlerCount > 0) {
            for (DynamicObjectInvocationHandler<?> handler : invocationHandlerMap.values()) {
                handler.close();
            }
            invocationHandlerMap.clear();
        }
        final int extendHandlerCount = extendHandlerMap.size();
        if (extendHandlerCount > 0) {
            for (DynamicObjectExtendHandler<?> handler : extendHandlerMap.values()) {
                handler.close();
            }
            extendHandlerMap.clear();
        }
        return invocationHandlerCount + extendHandlerCount;
    }

    /**
     * Extend class.
     *
     * @param <T>     the type parameter
     * @param type    the type
     * @param v8Value the V8 value
     * @return the class
     * @since 0.4.0
     */
    public <T> Class<T> extend(Class<T> type, V8Value v8Value) {
        if (v8Value instanceof V8ValueObject) {
            V8ValueObject v8ValueObject = null;
            try {
                v8ValueObject = v8Value.toClone();
                DynamicObjectExtendHandler<T> extendHandler = new DynamicObjectExtendHandler<>(
                        currentExtendHandle.incrementAndGet(),
                        type,
                        v8ValueObject);
                extendHandlerMap.put(extendHandler.getHandle(), extendHandler);
                return extendHandler.getObjectClass();
            } catch (Throwable t) {
                logger.logError(t, "Failed to extend {0} by a dynamic object.", type.getName());
                JavetResourceUtils.safeClose(v8ValueObject);
            }
        }
        return null;
    }

    /**
     * Get callback contexts.
     *
     * @param v8Runtime the V8 runtime
     * @return the callback contexts
     * @since 0.5.0
     */
    public JavetCallbackContext[] getCallbackContexts(final V8Runtime v8Runtime) {
        return new JavetCallbackContext[]{new JavetCallbackContext(
                EXTEND,
                this, JavetCallbackType.DirectCallNoThisAndResult,
                (IJavetDirectCallable.NoThisAndResult<Exception>) (v8Values) -> {
                    if (v8Values.length >= 2) {
                        Object object = v8Runtime.toObject(v8Values[0]);
                        if (object instanceof Class) {
                            Class<?> clazz = (Class<?>) object;
                            V8ValueObject v8ValueObject = V8ValueUtils.asV8ValueObject(v8Values, 1);
                            if (v8ValueObject != null) {
                                Class<?> childClass = JavetReflectionObjectFactory.getInstance()
                                        .extend(clazz, v8ValueObject);
                                return v8Runtime.toV8Value(childClass);
                            }
                        }
                    }
                    return v8Runtime.createV8ValueUndefined();
                })
        };
    }

    /**
     * Gets extend handler map.
     *
     * @return the extend handler map
     * @since 0.4.0
     */
    public Map<Long, DynamicObjectExtendHandler<?>> getExtendHandlerMap() {
        return extendHandlerMap;
    }

    /**
     * Gets invocation handler map.
     *
     * @return the invocation handler map
     * @since 0.4.0
     */
    public Map<Long, DynamicObjectInvocationHandler<?>> getInvocationHandlerMap() {
        return invocationHandlerMap;
    }

    @Override
    public Object toObject(Class<?> type, V8Value v8Value) {
        if (v8Value instanceof V8ValueObject) {
            V8ValueObject v8ValueObject = null;
            try {
                v8ValueObject = v8Value.toClone();
                DynamicObjectInvocationHandler<?> invocationHandler =
                        new DynamicObjectInvocationHandler<>(
                                currentExtendHandle.incrementAndGet(),
                                type,
                                v8ValueObject);
                invocationHandlerMap.put(invocationHandler.getHandle(), invocationHandler);
                return invocationHandler.getDynamicObject();
            } catch (Throwable t) {
                logger.logError(t, "Failed to create {0} by a dynamic object.", type.getName());
                JavetResourceUtils.safeClose(v8ValueObject);
            }
        }
        return null;
    }
}
