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

package com.caoccao.javet.buddy.interop.proxy;

import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.utils.StringUtils;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.caoccao.javet.values.reference.V8ValueObject;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * The type Dynamic object extend handler.
 *
 * @param <T> the type parameter
 * @since 0.4.0
 */
public class DynamicObjectExtendHandler<T> extends BaseDynamicObjectHandler<T> {
    /**
     * Instantiates a new Dynamic object extend handler.
     *
     * @param handle        the handle
     * @param type          the type
     * @param v8ValueObject the V8 value object
     * @since 0.4.0
     */
    public DynamicObjectExtendHandler(long handle, Class<T> type, V8ValueObject v8ValueObject) {
        super(handle, type, v8ValueObject);
    }

    @RuntimeType
    @Override
    public void close() throws Exception {
        super.close();
    }

    /**
     * Intercept method call.
     *
     * @param method      the method
     * @param arguments   the arguments
     * @param superObject the super object
     * @param thisObject  the this object
     * @param superCall   the super call
     * @return the object
     * @throws Exception the exception
     * @since 0.1.0
     */
    @RuntimeType
    public Object interceptMethod(
            @Origin Method method,
            @AllArguments Object[] arguments,
            @Super(strategy = Super.Instantiation.UNSAFE, proxyType = TargetType.class) Object superObject,
            @This Object thisObject,
            @SuperCall Callable<T> superCall) throws Exception {
        if (v8ValueObject != null) {
            V8Runtime v8Runtime = v8ValueObject.getV8Runtime();
            try (V8ValueObject v8ValueSuper = v8Runtime.toV8Value(superObject);
                 V8ValueObject v8ValueThis = v8Runtime.toV8Value(thisObject)) {
                v8Runtime.getGlobalObject().set(SUPER, v8ValueSuper);
                String methodName = method.getName();
                final int argumentLength = arguments.length;
                if (v8ValueObject.has(methodName)) {
                    // Function or Property
                    try (V8Value v8ValueProperty = v8ValueObject.get(methodName)) {
                        if (v8ValueProperty instanceof V8ValueFunction) {
                            // Function
                            V8ValueFunction v8ValueFunction = (V8ValueFunction) v8ValueProperty;
                            return v8ValueFunction.callObject(v8ValueThis, arguments);
                        } else if (argumentLength == 0) {
                            // Property
                            return v8Runtime.toObject(v8ValueProperty);
                        }
                    }
                } else if (argumentLength == 0) {
                    // Getter
                    String propertyName = null;
                    if (methodName.startsWith(V8ValueObject.METHOD_PREFIX_IS)) {
                        propertyName = methodName.substring(V8ValueObject.METHOD_PREFIX_IS.length());
                    } else if (methodName.startsWith(V8ValueObject.METHOD_PREFIX_GET)) {
                        propertyName = methodName.substring(V8ValueObject.METHOD_PREFIX_GET.length());
                    }
                    if (StringUtils.isNotEmpty(propertyName)) {
                        propertyName = propertyName.substring(0, 1).toLowerCase(Locale.ROOT)
                                + propertyName.substring(1);
                        if (v8ValueObject.has(propertyName)) {
                            return v8ValueObject.getObject(propertyName);
                        }
                    }
                } else if (argumentLength == 1) {
                    // Setter
                    String propertyName = null;
                    if (methodName.startsWith(V8ValueObject.METHOD_PREFIX_SET)) {
                        propertyName = methodName.substring(V8ValueObject.METHOD_PREFIX_SET.length());
                    }
                    if (StringUtils.isNotEmpty(propertyName)) {
                        propertyName = propertyName.substring(0, 1).toLowerCase(Locale.ROOT)
                                + propertyName.substring(1);
                        if (v8ValueObject.has(propertyName)) {
                            return v8ValueThis.set(propertyName, arguments[0]);
                        }
                    }
                }
            } finally {
                v8Runtime.getGlobalObject().delete(SUPER);
            }
        }
        return superCall.call();
    }
}
