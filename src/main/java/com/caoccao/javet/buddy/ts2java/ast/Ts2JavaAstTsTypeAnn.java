/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsEntityName;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstTsType;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsKeywordType;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeAnn;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeRef;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;

import java.util.Map;

public final class Ts2JavaAstTsTypeAnn {
    private static final Map<String, TypeDescription> TS_KEYWORD_TYPE_MAP = SimpleMap.of(
            "boolean", TypeDescription.ForLoadedType.of(boolean.class),
            "string", TypeDescription.ForLoadedType.of(String.class));
    private static final Map<String, TypeDescription> TS_TYPE_REF_MAP = SimpleMap.of(
            "byte", TypeDescription.ForLoadedType.of(byte.class),
            "char", TypeDescription.ForLoadedType.of(char.class),
            "double", TypeDescription.ForLoadedType.of(double.class),
            "float", TypeDescription.ForLoadedType.of(float.class),
            "int", TypeDescription.ForLoadedType.of(int.class),
            "long", TypeDescription.ForLoadedType.of(long.class),
            "short", TypeDescription.ForLoadedType.of(short.class)
    );

    private Ts2JavaAstTsTypeAnn() {
    }

    public static TypeDescription getTypeDescription(Swc4jAstTsTypeAnn ast) {
        TypeDescription type = null;
        ISwc4jAstTsType tsType = ast.getTypeAnn();
        switch (tsType.getType()) {
            case TsKeywordType:
                Swc4jAstTsKeywordType tsKeywordType = tsType.as(Swc4jAstTsKeywordType.class);
                type = TS_KEYWORD_TYPE_MAP.get(tsKeywordType.getKind().getName());
                break;
            case TsTypeRef:
                Swc4jAstTsTypeRef tsTypeRef = tsType.as(Swc4jAstTsTypeRef.class);
                ISwc4jAstTsEntityName tsEntityName = tsTypeRef.getTypeName();
                type = TS_TYPE_REF_MAP.get(Ts2JavaAstTsEntityName.getName(tsEntityName));
                break;
            default:
                break;
        }
        if (type == null) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Unsupported ts type ann ${typeAnn}.",
                            SimpleMap.of("typeAnn", tsType.getType().name())));
        }
        return type;
    }
}
