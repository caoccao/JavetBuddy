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
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstBinExpr;
import com.caoccao.javet.swc4j.ast.expr.Swc4jAstIdent;
import com.caoccao.javet.swc4j.ast.expr.lit.Swc4jAstNumber;
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

public final class Ts2JavaAstVarDecl implements ITs2JavaAstStackManipulation<Swc4jAstVarDecl> {
    @Override
    public TypeDescription manipulate(JavaFunctionContext functionContext, Swc4jAstVarDecl ast) {
        TypeDescription returnType = TypeDescription.ForLoadedType.of(void.class);
        for (Swc4jAstVarDeclarator varDeclarator : ast.getDecls()) {
            ISwc4jAstPat pat = varDeclarator.getName();
            switch (pat.getType()) {
                case BindingIdent: {
                    Swc4jAstBindingIdent bindingIdent = pat.as(Swc4jAstBindingIdent.class);
                    String variableName = bindingIdent.getId().getSym();
                    if (bindingIdent.getTypeAnn().isPresent()) {
                        Swc4jAstTsTypeAnn tsTypeAnn = bindingIdent.getTypeAnn().get();
                        TypeDescription variableType = Ts2JavaAstTsTypeAnn.getTypeDescription(tsTypeAnn);
                        if (varDeclarator.getInit().isPresent()) {
                            ISwc4jAstExpr expression = varDeclarator.getInit().get().unParenExpr();
                            TypeDescription valueType;
                            switch (expression.getType()) {
                                case BinExpr: {
                                    valueType = new Ts2JavaAstBinExpr().manipulate(
                                            functionContext, expression.as(Swc4jAstBinExpr.class));
                                    break;
                                }
                                case Number: {
                                    valueType = new Ts2JavaAstNumber(variableType).manipulate(
                                            functionContext, expression.as(Swc4jAstNumber.class));
                                    break;
                                }
                                case Ident: {
                                    valueType = new Ts2JavaAstIdent().manipulate(
                                            functionContext, expression.as(Swc4jAstIdent.class));
                                    break;
                                }
                                default:
                                    throw new Ts2JavaAstException(
                                            expression,
                                            SimpleFreeMarkerFormat.format("VarDecl init type ${type} is not supported.",
                                                    SimpleMap.of("type", expression.getType().name())));
                            }
                            JavaClassCast.getUpCastStackManipulation(valueType, variableType)
                                    .ifPresent(functionContext.getStackManipulations()::add);
                        }
                        JavaLocalVariable localVariable = new JavaLocalVariable(variableName, variableType);
                        MethodVariableAccess methodVariableAccess = MethodVariableAccess.of(variableType);
                        StackManipulation stackManipulation = methodVariableAccess.storeAt(functionContext.getNextOffset());
                        functionContext.getStackManipulations().add(stackManipulation);
                        functionContext.addLocalVariable(localVariable);
                        returnType = localVariable.getType();
                    } else {
                        throw new Ts2JavaAstException(
                                bindingIdent,
                                SimpleFreeMarkerFormat.format("VarDecl name ${name} type annotation is missing.",
                                        SimpleMap.of("name", variableName)));
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
        return returnType;
    }
}
