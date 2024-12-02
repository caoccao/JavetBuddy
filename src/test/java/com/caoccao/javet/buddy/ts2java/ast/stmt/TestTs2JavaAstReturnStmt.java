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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTs2JavaAstReturnStmt extends BaseTestTs2Java {
    @Test
    public void testBoolean() throws Exception {
        tsClass = new TsClass("return true;", boolean.class);
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return true;");
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return 1;", int.class);
        assertEquals(1, tsClass.invoke());
    }

    @Test
    public void testFloat() throws Exception {
        tsClass = new TsClass("return 1.1;", float.class);
        assertEquals(1.1F, (float) tsClass.invoke(), 0.001F);
        tsClass = new TsClass("return 1.1;");
        assertEquals(1.1F, (float) tsClass.invoke(), 0.001F);
    }

    @Test
    public void testInt() throws Exception {
        tsClass = new TsClass("return 1;", int.class);
        assertEquals(1, tsClass.invoke());
        tsClass = new TsClass("return 1;");
        assertEquals(1, tsClass.invoke());
    }

    @Test
    public void testLong() throws Exception {
        tsClass = new TsClass("return 8589934592;", long.class);
        assertEquals(8589934592L, (long) tsClass.invoke());
        tsClass = new TsClass("return 8589934592;");
        assertEquals(8589934592L, (long) tsClass.invoke());
    }
}
