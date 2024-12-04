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

public class TestTs2JavaAstBinExprLogicalCompare extends BaseTestTs2Java {
    @Test
    public void testLogicalEQEQ_DD_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", double.class),
                TsMethodArgument.of("b", double.class));
        assertFalse((boolean) tsClass.invoke(1D, 2D));
        assertFalse((boolean) tsClass.invoke(2D, 1D));
        assertTrue((boolean) tsClass.invoke(1D, 1D));
        assertTrue((boolean) tsClass.invoke(1.23D, 1.23D));
    }

    @Test
    public void testLogicalEQEQ_FF_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", float.class),
                TsMethodArgument.of("b", float.class));
        assertFalse((boolean) tsClass.invoke(1F, 2F));
        assertFalse((boolean) tsClass.invoke(2F, 1F));
        assertTrue((boolean) tsClass.invoke(1F, 1F));
        assertTrue((boolean) tsClass.invoke(1.23F, 1.23F));
    }

    @Test
    public void testLogicalEQEQ_II_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertFalse((boolean) tsClass.invoke(1, 2));
        assertFalse((boolean) tsClass.invoke(2, 1));
        assertTrue((boolean) tsClass.invoke(1, 1));
    }

    @Test
    public void testLogicalEQEQ_IJ_Z() throws Exception {
        TsClass tsClass = new TsClass(
                "return a === b;",
                boolean.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertFalse((boolean) tsClass.invoke(1, 2L));
        assertFalse((boolean) tsClass.invoke(2, 1L));
        assertTrue((boolean) tsClass.invoke(1, 1L));
    }
}
