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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTs2JavaAstUnaryExprMinus extends BaseTestTs2Java {
    @Test
    public void testMinus() throws Exception {
        tsClass = new TsClass("return -1;");
        assertEquals(-1, tsClass.invoke());
        tsClass = new TsClass("return -(1);");
        assertEquals(-1, tsClass.invoke());
        tsClass = new TsClass("return -(-1);", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClass(
                "return -a;",
                long.class,
                TsMethodArgument.of("a", long.class));
        assertEquals(-2L, tsClass.invoke(2L));
        tsClass = new TsClass(
                "return -(a + b);",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(-5, tsClass.invoke(3, 2));
        tsClass = new TsClass(
                "return -(a + (-1));",
                int.class,
                TsMethodArgument.of("a", int.class));
        assertEquals(-2, tsClass.invoke(3));
        tsClass = new TsClass(
                "return -(-(a + b));",
                int.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", int.class));
        assertEquals(5, tsClass.invoke(3, 2));
    }
}
