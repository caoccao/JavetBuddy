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

package com.caoccao.javet.buddy.ts2java.ast.ts;

import com.caoccao.javet.buddy.ts2java.ast.BaseTs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsEntityName;
import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAstTsType;
import com.caoccao.javet.buddy.ts2java.ast.memo.Ts2JavaMemoFunction;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeRef;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.jar.asm.MethodVisitor;

import java.util.Map;

public class Ts2JavaAstTsTypeRef
        extends BaseTs2JavaAst<Swc4jAstTsTypeRef, Ts2JavaMemoFunction>
        implements ITs2JavaAstTsType<Swc4jAstTsTypeRef, Ts2JavaMemoFunction> {
    protected static final Map<String, TypeDescription> TS_TYPE_REF_MAP = SimpleMap.of(
            "byte", TypeDescription.ForLoadedType.of(byte.class),
            "char", TypeDescription.ForLoadedType.of(char.class),
            "double", TypeDescription.ForLoadedType.of(double.class),
            "float", TypeDescription.ForLoadedType.of(float.class),
            "int", TypeDescription.ForLoadedType.of(int.class),
            "long", TypeDescription.ForLoadedType.of(long.class),
            "short", TypeDescription.ForLoadedType.of(short.class)
    );
    protected ITs2JavaAstTsEntityName<?, ?> typeName;

    protected Ts2JavaAstTsTypeRef(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstTsTypeRef ast,
            Ts2JavaMemoFunction memo) {
        super(parent, ast, memo);
        typeName = ITs2JavaAstTsEntityName.create(this, ast.getTypeName(), memo);
        String entityTypeName = typeName.getTypeName();
        type = TS_TYPE_REF_MAP.get(entityTypeName);
        if (type == null) {
            throw new Ts2JavaAstException(
                    ast,
                    SimpleFreeMarkerFormat.format("Ts type ref type name ${name} is not supported.",
                            SimpleMap.of("name", entityTypeName)));
        }
    }

    public static Ts2JavaAstTsTypeRef create(
            ITs2JavaAst<?, ?> parent,
            Swc4jAstTsTypeRef ast,
            Ts2JavaMemoFunction memo) {
        return new Ts2JavaAstTsTypeRef(parent, ast, memo);
    }

    @Override
    public Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
        visitLineNumber(methodVisitor);
        return Size.ZERO;
    }

    @Override
    public void compile() {
    }

    public ITs2JavaAstTsEntityName<?, ?> getTypeName() {
        return typeName;
    }

    @Override
    public void syncLabels() {
        typeName.syncLabels();
    }
}
