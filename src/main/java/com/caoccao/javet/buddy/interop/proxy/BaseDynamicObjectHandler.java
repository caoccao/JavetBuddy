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

import com.caoccao.javet.utils.JavetResourceUtils;
import com.caoccao.javet.values.reference.V8ValueObject;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Objects;

/**
 * The type Base dynamic object handler.
 *
 * @param <T> the type parameter
 * @since 0.4.0
 */
public abstract class BaseDynamicObjectHandler<T> implements AutoCloseable {
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
     * The Handle.
     *
     * @since 0.4.0
     */
    protected long handle;
    /**
     * The Type.
     *
     * @since 0.4.0
     */
    protected Class<T> type;
    /**
     * The V8 value object.
     *
     * @since 0.4.0
     */
    protected V8ValueObject v8ValueObject;

    /**
     * Instantiates a new Base dynamic object handler.
     *
     * @param handle        the handle
     * @param type          the type
     * @param v8ValueObject the V8 value object
     * @since 0.4.0
     */
    public BaseDynamicObjectHandler(long handle, Class<T> type, V8ValueObject v8ValueObject) {
        this.handle = handle;
        this.type = Objects.requireNonNull(type);
        this.v8ValueObject = Objects.requireNonNull(v8ValueObject);
    }

    @Override
    public void close() throws Exception {
        if (v8ValueObject != null) {
            JavetResourceUtils.safeClose(v8ValueObject);
            v8ValueObject = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    /**
     * Gets handle.
     *
     * @return the handle
     * @since 0.4.0
     */
    public long getHandle() {
        return handle;
    }

    /**
     * Gets object class.
     *
     * @return the object class
     * @since 0.3.0
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getObjectClass() {
        DynamicType.Builder<T> builder = new ByteBuddy().subclass(type, CONSTRUCTOR_STRATEGY);
        if (!AutoCloseable.class.isAssignableFrom(type)) {
            builder = builder.implement(AutoCloseable.class);
        }
        try (DynamicType.Unloaded<T> unloadedType = builder
                .method(ElementMatchers.isPublic())
                .intercept(MethodDelegation.to(this))
                .make()) {
            return (Class<T>) unloadedType.load(getClass().getClassLoader()).getLoaded();
        }
    }

    /**
     * Gets type.
     *
     * @return the type
     * @since 0.4.0
     */
    public Class<T> getType() {
        return type;
    }
}
