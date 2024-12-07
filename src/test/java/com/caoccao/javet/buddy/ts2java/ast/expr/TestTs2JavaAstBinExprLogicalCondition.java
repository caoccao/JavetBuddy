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
  public logicalAnd_II_Z(II)Z
   L0
    LINENUMBER 86 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPNE L1
    ILOAD 1
    ICONST_1
    IF_ICMPLE L1
    ICONST_1
    GOTO L2
   L1
   FRAME SAME
    ICONST_0
   L2
   FRAME SAME1 I
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/TestLogicalOperations; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalAnd_II_Z(int a, int b) {
        return a == b && a > 1;
    }

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
  public logicalNot_Or_II_Z(II)Z
   L0
    LINENUMBER 85 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPEQ L1
    ILOAD 1
    ICONST_1
    IF_ICMPGT L1
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
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public boolean logicalNot_Or_II_Z(int a, int b) {
        return !(a == b || a > 1);
    }

    /*
  public logicalOr_ZZ_Z(ZZ)Z
   L0
    LINENUMBER 81 L0
    ILOAD 1
    IFNE L1
    ILOAD 2
    IFEQ L2
   L1
   FRAME SAME
    ICONST_1
    GOTO L3
   L2
   FRAME SAME
    ICONST_0
   L3
   FRAME SAME1 I
    IRETURN
   L4
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstBinExprLogicalCondition; L0 L4 0
    LOCALVARIABLE a Z L0 L4 1
    LOCALVARIABLE b Z L0 L4 2
    MAXSTACK = 1
    MAXLOCALS = 3
     */
    public boolean logicalOr_ZZ_Z(boolean a, boolean b) {
        return a || b;
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
    public void testLogicalAnd_II_Z() throws Exception {
        assertTrue(logicalAnd_II_Z(2, 2));
        assertFalse(logicalAnd_II_Z(2, 3));
        assertFalse(logicalAnd_II_Z(1, 1));
        tsClass = new TsClass(
                "return a == b && a > 1;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertTrue((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(2, 3));
        assertFalse((boolean) tsClass.invoke(1, 1));
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

//    @Test
    public void testLogicalNot_Or_II_Z() throws Exception {
        assertFalse(logicalNot_Or_II_Z(2, 2));
        assertFalse(logicalNot_Or_II_Z(2, 3));
        assertFalse(logicalNot_Or_II_Z(1, 1));
        assertTrue(logicalNot_Or_II_Z(0, 1));
        enableLogging();
        tsClass = new TsClass(
                "return !(a == b || a > 1);",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(2, 2));
        assertFalse((boolean) tsClass.invoke(2, 3));
        assertFalse((boolean) tsClass.invoke(1, 1));
        assertTrue((boolean) tsClass.invoke(0, 1));
    }

    @Test
    public void testLogicalOr_ZZ_Z() throws Exception {
        assertTrue(logicalOr_ZZ_Z(true, true));
        assertTrue(logicalOr_ZZ_Z(true, false));
        assertTrue(logicalOr_ZZ_Z(false, true));
        assertFalse(logicalOr_ZZ_Z(false, false));
        tsClass = new TsClass(
                "return a || b;",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", boolean.class));
        assertTrue((boolean) tsClass.invoke(true, true));
        assertTrue((boolean) tsClass.invoke(true, false));
        assertTrue((boolean) tsClass.invoke(false, true));
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
