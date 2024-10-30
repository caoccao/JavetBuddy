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

import com.caoccao.javet.buddy.ts2java.compiler.JavaClassCast;
import com.caoccao.javet.buddy.ts2java.compiler.JavaFunctionContext;
import com.caoccao.javet.buddy.ts2java.compiler.JavaLocalVariable;
import com.caoccao.javet.buddy.ts2java.exceptions.Ts2JavaAstException;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstExpr;
import com.caoccao.javet.swc4j.ast.interfaces.ISwc4jAstPat;
import com.caoccao.javet.swc4j.ast.pat.Swc4jAstBindingIdent;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDecl;
import com.caoccao.javet.swc4j.ast.stmt.Swc4jAstVarDeclarator;
import com.caoccao.javet.swc4j.ast.ts.Swc4jAstTsTypeAnn;
import com.caoccao.javet.utils.SimpleFreeMarkerFormat;
import com.caoccao.javet.utils.SimpleMap;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;

import java.util.Optional;

public final class Ts2JavaAstVarDecl implements ITs2JavaAstStackManipulation<Swc4jAstVarDecl> {
    @Override
    public Optional<TypeDescription> manipulate(JavaFunctionContext functionContext, Swc4jAstVarDecl ast) {
        for (Swc4jAstVarDeclarator varDeclarator : ast.getDecls()) {
            Optional<TypeDescription> optionalFromType = Optional.empty();
            if (varDeclarator.getInit().isPresent()) {
                ISwc4jAstExpr expression = varDeclarator.getInit().get();
                switch (expression.getType()) {
                    case Ident: {
                        String name = expression.as(Swc4jAstIdent.class).getSym();
                        JavaLocalVariable localVariable = functionContext.getLocalVariable(name);
                        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(localVariable.getType());
                        StackManipulation stackManipulation = methodVariableAccess.loadFrom(localVariable.getOffset());
                        functionContext.addStackManipulation(stackManipulation);
                        optionalFromType = Optional.of(localVariable.getType());
                        break;
                    }
                    default:
                        throw new Ts2JavaAstException(
                                expression,
                                SimpleFreeMarkerFormat.format("VarDecl init type ${type} is not supported.",
                                        SimpleMap.of("type", expression.getType().name())));
                }
            }
            ISwc4jAstPat pat = varDeclarator.getName();
            switch (pat.getType()) {
                case BindingIdent: {
                    Swc4jAstBindingIdent bindingIdent = pat.as(Swc4jAstBindingIdent.class);
                    String name = bindingIdent.getId().getSym();
                    if (bindingIdent.getTypeAnn().isPresent()) {
                        Swc4jAstTsTypeAnn tsTypeAnn = bindingIdent.getTypeAnn().get();
                        TypeDescription toType = Ts2JavaAstTsTypeAnn.getTypeDescription(tsTypeAnn);
                        optionalFromType.ifPresent(fromType ->
                                JavaClassCast.upCast(fromType, toType, functionContext::addStackManipulation));
                        JavaLocalVariable localVariable = new JavaLocalVariable(name, toType);
                        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(localVariable.getType());
                        StackManipulation stackManipulation = methodVariableAccess.storeAt(functionContext.getNextOffset());
                        functionContext.addStackManipulation(stackManipulation);
                        functionContext.addLocalVariable(localVariable);
                    } else {
                        throw new Ts2JavaAstException(
                                bindingIdent,
                                SimpleFreeMarkerFormat.format("VarDecl name ${name} type annotation is missing.",
                                        SimpleMap.of("name", name)));
                    }
                    break;
                }
                default:
                    throw new Ts2JavaAstException(
                            pat,
                            SimpleFreeMarkerFormat.format("VarDecl name type ${type} is not supported.",
                                    SimpleMap.of("type", pat.getType().name())));
            }
        }
        return Optional.empty();
    }
}
