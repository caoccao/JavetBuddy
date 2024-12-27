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

package com.caoccao.javet.buddy.ts2java.ast.expr;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTs2JavaAstUnaryExprBang extends BaseTestTs2Java {
    /*
  public bangLogicalCompare(ZII)Z
   L0
    LINENUMBER 29 L0
    ILOAD 1
    IFEQ L1
    ILOAD 2
    ILOAD 3
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
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/ast/expr/TestTs2JavaAstUnaryExprBang; L0 L3 0
    LOCALVARIABLE a Z L0 L3 1
    LOCALVARIABLE b I L0 L3 2
    LOCALVARIABLE c I L0 L3 3
    MAXSTACK = 2
    MAXLOCALS = 4
     */
    public boolean bangLogicalCompare(boolean a, int b, int c) {
        return a && !(b > c);
    }

    @Test
    public void testBangBoolean() throws Exception {
        tsClass = new TsClass("return !true;");
        assertFalse((boolean) tsClass.invoke());
        tsClass = new TsClass("return !false;");
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return !(!true);", boolean.class);
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return !(!false);", boolean.class);
        assertFalse((boolean) tsClass.invoke());
        tsClass = new TsClass("return !a;", boolean.class, TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
        tsClass = new TsClass("return !!a;", boolean.class, TsMethodArgument.of("a", boolean.class));
        assertTrue((boolean) tsClass.invoke(true));
        assertFalse((boolean) tsClass.invoke(false));
    }

    @Test
    public void testBangLogicalCompare() throws Exception {
        assertTrue(bangLogicalCompare(true, 1, 2));
        assertFalse(bangLogicalCompare(true, 2, 1));
        assertFalse(bangLogicalCompare(false, 1, 2));
        tsClass = new TsClass(
                "return a && !(b > c);",
                boolean.class,
                TsMethodArgument.of("a", boolean.class),
                TsMethodArgument.of("b", int.class),
                TsMethodArgument.of("c", int.class));
        assertTrue((boolean) tsClass.invoke(true, 1, 2));
        assertFalse((boolean) tsClass.invoke(true, 2, 1));
        assertFalse((boolean) tsClass.invoke(false, 1, 2));
    }
}
