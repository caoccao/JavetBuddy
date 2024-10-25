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

package com.caoccao.javet.buddy.ts2java;

import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsEntityName;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsType;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeAnn;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeRef;
import com.caoccao.javet.utils.SimpleMap;

import java.util.Map;

public final class Ts2JavaAstTsTypeAnn {
    private static final Map<String, Class<?>> TYPE_MAP = SimpleMap.of(
            "boolean", boolean.class,
            "byte", byte.class,
            "char", char.class,
            "double", double.class,
            "float", float.class,
            "int", int.class,
            "long", long.class,
            "short", short.class
    );

    private Ts2JavaAstTsTypeAnn() {
    }

    public static Class<?> getClass(Swc4jAstTsTypeAnn ast) {
        ISwc4jAstTsType tsType = ast.getTypeAnn();
        switch (tsType.getType()) {
            case TsTypeRef:
                Swc4jAstTsTypeRef tsTypeRef = tsType.as(Swc4jAstTsTypeRef.class);
                ISwc4jAstTsEntityName tsEntityName = tsTypeRef.getTypeName();
                return TYPE_MAP.get(Ts2JavaAstTsEntityName.getName(tsEntityName));
            default:
                return Object.class;
        }
    }
}
