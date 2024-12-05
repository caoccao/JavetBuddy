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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTs2JavaAstBinExprLogicalCondition extends BaseTestTs2Java {
    /*
  public logicalAnd_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 54 L0
    ILOAD 1
    IFEQ L1
    ILOAD 2
    IFEQ L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b Z L0 L3 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean logicalAnd_ZZ_Z(boolean a, boolean b) {
        return a && b;
    }

    /*
  public not_LogicalAnd_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 56 L0
    ILOAD 1
    IFNE L1
    ILOAD 2
    IFNE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b Z L0 L3 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean not_LogicalAnd_ZZ_Z(boolean a, boolean b) {
        return !(a || b);
    }

    @Test
    public void testLogicalAnd_ZZ_Z() throws Exception {
        assertTrue(logicalAnd_ZZ_Z(true, true));
        assertFalse(logicalAnd_ZZ_Z(true, false));
        assertFalse(logicalAnd_ZZ_Z(false, false));
        tsClass = new TsClass(
                "return a && b;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true));
        assertFalse((boolean) tsClass.invoke(true, false));
        assertFalse((boolean) tsClass.invoke(false, false));
    }

    @Test
    public void testNot_LogicalOr_ZZ_Z() throws Exception {
        assertFalse(not_LogicalAnd_ZZ_Z(true, true));
        assertFalse(not_LogicalAnd_ZZ_Z(true, false));
        assertFalse(not_LogicalAnd_ZZ_Z(false, true));
        assertTrue(not_LogicalAnd_ZZ_Z(false, false));
        tsClass = new TsClass(
                "return !(a || b);",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertFalse((boolean) tsClass.invoke(true, true));
        assertFalse((boolean) tsClass.invoke(true, false));
        assertFalse((boolean) tsClass.invoke(false, true));
        assertTrue((boolean) tsClass.invoke(false, false));
    }
}
