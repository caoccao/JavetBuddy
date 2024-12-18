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

package com.caoccao.javet.buddy.ts2java.ast.stmt;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTs2JavaAstIfStmt extends BaseTestTs2Java {
    /*
  public booleanCons(Z)I
   L0
    LINENUMBER 28 L0
    ILOAD 1
    IFEQ L1
   L2
    LINENUMBER 29 L2
    ICONST_1
    IRETURN
   L1
    LINENUMBER 31 L1
   FRAME SAME
    ICONST_0
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/stmt/TestTs2JavaAstIfStmt; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    MAXSTACK = 1
    MAXLOCALS = 2
     */
    public int booleanCons(boolean a) {
        if (a) {
            return 1;
        }
        return 0;
    }

    /*
  public booleanConsAlt(Z)I
   L0
    LINENUMBER 28 L0
    ILOAD 1
    IFEQ L1
   L2
    LINENUMBER 29 L2
    ICONST_1
    IRETURN
   L1
    LINENUMBER 31 L1
   FRAME SAME
    ICONST_0
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/stmt/TestTs2JavaAstIfStmt; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    MAXSTACK = 1
    MAXLOCALS = 2
     */
    public int booleanConsAlt(boolean a) {
        if (a) {
            return 1;
        } else {
            return 0;
        }
    }

    /*
  public logicalCompareCons(II)I
   L0
    LINENUMBER 85 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPLE L1
   L2
    LINENUMBER 86 L2
    ILOAD 1
    IRETURN
   L1
    LINENUMBER 88 L1
   FRAME SAME
    ILOAD 2
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/stmt/TestTs2JavaAstIfStmt; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public int logicalCompareCons(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    /*
  public logicalCompareConsAlt(II)I
   L0
    LINENUMBER 85 L0
    ILOAD 1
    ILOAD 2
    IF_ICMPLE L1
   L2
    LINENUMBER 86 L2
    ILOAD 1
    IRETURN
   L1
    LINENUMBER 88 L1
   FRAME SAME
    ILOAD 2
    IRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/stmt/TestTs2JavaAstIfStmt; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    MAXSTACK = 2
    MAXLOCALS = 3
     */
    public int logicalCompareConsAlt(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    @Test
    public void testBooleanCons() throws Exception {
        assertEquals(1, booleanCons(true));
        assertEquals(0, booleanCons(false));
        tsClass = new TsClass(
                "if (a) { return 1; } return 0;",
                TsMethodArgument.of("a", boolean.class));
        assertEquals(1, tsClass.invoke(true));
        assertEquals(0, tsClass.invoke(false));
        tsClass = new TsClass("if (true) { return 1; } return 0;", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClass("if (false) { return 1; } return 0;", int.class);
        assertEquals(0, tsClass.invoke());
    }

    @Test
    public void testBooleanConsAlt() throws Exception {
        assertEquals(1, booleanConsAlt(true));
        assertEquals(0, booleanConsAlt(false));
        tsClass = new TsClass(
                "if (a) { return 1; } else { return 0; }",
                TsMethodArgument.of("a", boolean.class));
        assertEquals(1, tsClass.invoke(true));
        assertEquals(0, tsClass.invoke(false));
        tsClass = new TsClass("if (true) { return 1; } else { return 0; };", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClass("if (false) { return 1; } else { return 0; };", int.class);
        assertEquals(0, tsClass.invoke());
    }

    @Test
    public void testLogicalCompareCons() throws Exception {
        assertEquals(1, logicalCompareCons(1, 0));
        assertEquals(2, logicalCompareCons(1, 2));
        tsClass = new TsClass(
                "if (a > b) { return a; } return b;",
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1, tsClass.invoke(1, 0));
        assertEquals(2, tsClass.invoke(1, 2));
    }

    @Test
    public void testLogicalCompareConsAlt() throws Exception {
        assertEquals(1, logicalCompareConsAlt(1, 0));
        assertEquals(2, logicalCompareConsAlt(1, 2));
        tsClass = new TsClass(
                "if (a > b) { return a; } else { return b; }",
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(1, tsClass.invoke(1, 0));
        assertEquals(2, tsClass.invoke(1, 2));
    }
}
