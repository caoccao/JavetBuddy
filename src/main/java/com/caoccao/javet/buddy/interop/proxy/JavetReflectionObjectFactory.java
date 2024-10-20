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
import com.caoccao.javet.interop.proxy.IJavetReflectionObjectFactory;
import com.caoccao.javet.utils.JavetDefaultLogger;
import com.caoccao.javet.utils.JavetResourceUtils;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Javet dynamic object factory.
 *
 * @since 0.1.0
 */
public final class JavetReflectionObjectFactory implements IJavetReflectionObjectFactory {
    private static final JavetReflectionObjectFactory instance = new JavetReflectionObjectFactory();
    private final Map<Long, DynamicObjectExtendHandler<?>> extendHandlerMap;
    private final IJavetLogger logger;
    private long currentExtendHandle;

    private JavetReflectionObjectFactory() {
        logger = new JavetDefaultLogger(getClass().getName());
        currentExtendHandle = 0;
        extendHandlerMap = new HashMap<>();
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
        final int count = extendHandlerMap.size();
        if (count > 0) {
            for (DynamicObjectExtendHandler<?> handler : extendHandlerMap.values()) {
                handler.close();
            }
            extendHandlerMap.clear();
        }
        return count;
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
                ++currentExtendHandle;
                DynamicObjectExtendHandler<T> extendHandler =
                        new DynamicObjectExtendHandler<>(currentExtendHandle, type, v8ValueObject);
                extendHandlerMap.put(currentExtendHandle, extendHandler);
                return extendHandler.getObjectClass();
            } catch (Throwable t) {
                logger.logError(t, "Failed to extend {0} by a dynamic object.", type.getName());
                JavetResourceUtils.safeClose(v8ValueObject);
            }
        }
        return null;
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

    @Override
    public Object toObject(Class<?> type, V8Value v8Value) {
        if (v8Value instanceof V8ValueObject) {
            V8ValueObject v8ValueObject = null;
            try {
                v8ValueObject = v8Value.toClone();
                DynamicObjectInvocationHandler<?> invocationHandler =
                        new DynamicObjectInvocationHandler<>(type, v8ValueObject);
                return invocationHandler.getDynamicObject();
            } catch (Throwable t) {
                logger.logError(t, "Failed to create {0} by a dynamic object.", type.getName());
                JavetResourceUtils.safeClose(v8ValueObject);
            }
        }
        return null;
    }
}
