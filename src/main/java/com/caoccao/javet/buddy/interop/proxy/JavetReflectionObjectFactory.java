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

/**
 * The type Javet dynamic object factory.
 *
 * @since 0.1.0
 */
public final class JavetReflectionObjectFactory implements IJavetReflectionObjectFactory {
    private static final JavetReflectionObjectFactory instance = new JavetReflectionObjectFactory();
    private final IJavetLogger logger;

    private JavetReflectionObjectFactory() {
        logger = new JavetDefaultLogger(getClass().getName());
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

    @Override
    public Object toObject(Class<?> type, V8Value v8Value) {
        if (v8Value instanceof V8ValueObject) {
            V8ValueObject v8ValueObject = null;
            try {
                DynamicObjectAutoCloseableInvocationHandler invocationHandler;
                v8ValueObject = v8Value.toClone();
                if (AutoCloseable.class.isAssignableFrom(type)) {
                    invocationHandler = new DynamicObjectAutoCloseableInvocationHandler(type, v8ValueObject);
                } else {
                    invocationHandler = new DynamicObjectForceCloseableInvocationHandler(type, v8ValueObject);
                }
                invocationHandler.initialize();
                return invocationHandler.getDynamicObject();
            } catch (Throwable t) {
                logger.logError(t, "Failed to create dynamic object for {0}.", type.getName());
                JavetResourceUtils.safeClose(v8ValueObject);
            }
        }
        return null;
    }
}
