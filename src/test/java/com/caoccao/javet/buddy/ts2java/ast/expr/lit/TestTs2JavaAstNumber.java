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

package com.caoccao.javet.buddy.ts2java.ast.expr.lit;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTs2JavaAstNumber extends BaseTestTs2Java {
    @Test
    public void testDouble() throws Exception {
        tsClass = new TsClass("return 1.23;", double.class);
        assertEquals(1.23D, (double) tsClass.invoke(), 0.001D);
        tsClass = new TsClass("return (1.23);", double.class);
        assertEquals(1.23D, (double) tsClass.invoke(), 0.001D);
    }

    @Test
    public void testFloat() throws Exception {
        tsClass = new TsClass("return 1.23;", float.class);
        assertEquals(1.23F, (float) tsClass.invoke(), 0.001F);
        tsClass = new TsClass("return (1.23);", float.class);
        assertEquals(1.23F, (float) tsClass.invoke(), 0.001F);
    }

    @Test
    public void testInt() throws Exception {
        TsClass tsClass = new TsClass("return 1;", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClass("return (1);", int.class);
        assertEquals(1, tsClass.invoke());
    }

    @Test
    public void testLong() throws Exception {
        TsClass tsClass = new TsClass("return 1;", long.class);
        assertEquals(1L, tsClass.invoke());
        tsClass = new TsClass("return (1);", long.class);
        assertEquals(1L, tsClass.invoke());
    }
}
