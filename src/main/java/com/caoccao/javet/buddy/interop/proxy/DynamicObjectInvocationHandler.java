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

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.utils.JavetResourceUtils;
import com.caoccao.javet.utils.StringUtils;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueArray;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.caoccao.javet.values.reference.V8ValueObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * The type Dynamic object auto closeable invocation handler.
 *
 * @since 0.1.0
 */
public class DynamicObjectInvocationHandler implements AutoCloseable {
    /**
     * The constant ARGS for constructor.
     *
     * @since 0.3.0
     */
    protected static final String ARGS = "$";
    /**
     * The constant CONSTRUCTOR_STRATEGY.
     *
     * @since 0.1.0
     */
    protected static final ConstructorStrategy CONSTRUCTOR_STRATEGY =
            ConstructorStrategy.Default.IMITATE_SUPER_CLASS;
    /**
     * The constant METHOD_CLOSE.
     *
     * @since 0.1.0
     */
    protected static final String METHOD_CLOSE = "close";
    /**
     * The constant SUPER.
     *
     * @since 0.4.0
     */
    protected static final String SUPER = "$super";
    /**
     * The Dynamic object.
     *
     * @since 0.1.0
     */
    protected Object dynamicObject;
    /**
     * The Type.
     *
     * @since 0.1.0
     */
    protected Class<?> type;
    /**
     * The V8 value object.
     *
     * @since 0.1.0
     */
    protected V8ValueObject v8ValueObject;

    /**
     * Instantiates a new Dynamic object auto closeable invocation handler.
     *
     * @param type          the type
     * @param v8ValueObject the V8 value object
     * @since 0.1.0
     */
    public DynamicObjectInvocationHandler(Class<?> type, V8ValueObject v8ValueObject) {
        this.type = Objects.requireNonNull(type);
        this.v8ValueObject = v8ValueObject;
    }

    @RuntimeType
    @Override
    public void close() throws Exception {
        if (v8ValueObject != null) {
            JavetResourceUtils.safeClose(v8ValueObject);
            dynamicObject = null;
            v8ValueObject = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    /**
     * Gets dynamic object.
     *
     * @return the dynamic object
     * @since 0.1.0
     */
    public Object getDynamicObject() {
        return dynamicObject;
    }

    /**
     * Gets object class.
     *
     * @return the object class
     * @since 0.3.0
     */
    protected Class<?> getObjectClass() {
        DynamicType.Builder<?> builder = new ByteBuddy().subclass(type, CONSTRUCTOR_STRATEGY);
        if (!AutoCloseable.class.isAssignableFrom(type)) {
            builder = builder.implement(AutoCloseable.class);
        }
        try (DynamicType.Unloaded<?> unloadedType = builder
                .method(ElementMatchers.isPublic())
                .intercept(MethodDelegation.to(this))
                .make()) {
            return unloadedType.load(getClass().getClassLoader()).getLoaded();
        }
    }

    /**
     * Initialize.
     *
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws JavetException            the javet exception
     * @since 0.1.0
     */
    public void initialize()
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, JavetException {
        dynamicObject = null;
        if (v8ValueObject.has(ARGS)) {
            try (V8Value v8Value = v8ValueObject.get(ARGS)) {
                if (v8Value instanceof V8ValueArray) {
                    List<Object> args = new ArrayList<>();
                    V8ValueArray v8ValueArray = (V8ValueArray) v8Value;
                    V8Runtime v8Runtime = v8ValueObject.getV8Runtime();
                    v8ValueArray.forEach(value -> args.add(v8Runtime.toObject(value)));
                    Class<?>[] argClasses = args.stream().map(Object::getClass).toArray(Class[]::new);
                    dynamicObject = getObjectClass().getConstructor(argClasses).newInstance(args.toArray());
                }
            }
        }
        if (dynamicObject == null) {
            dynamicObject = getObjectClass().getConstructor().newInstance();
        }
    }

    /**
     * Intercept object.
     *
     * @param method      the method
     * @param arguments   the arguments
     * @param superObject the super object
     * @param superCall   the super call
     * @return the object
     * @throws Exception the exception
     * @since 0.1.0
     */
    @RuntimeType
    public Object intercept(
            @Origin Method method,
            @AllArguments Object[] arguments,
            @Super(strategy = Super.Instantiation.UNSAFE, proxyType = TargetType.class) Object superObject,
            @SuperCall Callable<Object> superCall) throws Exception {
        if (v8ValueObject != null) {
            V8Runtime v8Runtime = v8ValueObject.getV8Runtime();
            try (V8Value v8ValueSuper = v8Runtime.toV8Value(superObject)) {
                v8Runtime.getGlobalObject().set(SUPER, v8ValueSuper);
                String methodName = method.getName();
                final int argumentLength = arguments.length;
                if (METHOD_CLOSE.equals(methodName) && argumentLength == 0) {
                    close();
                } else if (v8ValueObject.has(methodName)) {
                    // Function or Property
                    try (V8Value v8ValueProperty = v8ValueObject.get(methodName)) {
                        if (v8ValueProperty instanceof V8ValueFunction) {
                            // Function
                            V8ValueFunction v8ValueFunction = (V8ValueFunction) v8ValueProperty;
                            return v8ValueFunction.callObject(v8ValueObject, arguments);
                        } else if (argumentLength == 0) {
                            // Property
                            return v8ValueObject.getV8Runtime().toObject(v8ValueProperty);
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
                            return v8ValueObject.set(propertyName, arguments[0]);
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
