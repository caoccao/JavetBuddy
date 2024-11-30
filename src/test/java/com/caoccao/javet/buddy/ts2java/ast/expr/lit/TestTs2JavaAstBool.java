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

package com.caoccao.javet.buddy.ts2java.ast.expr.lit;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTs2JavaAstBool extends BaseTestTs2Java {
    @Test
    public void testBool() throws Exception {
        tsClass = new TsClass("return true;", boolean.class);
        assertTrue((boolean) tsClass.invoke());
        tsClass = new TsClass("return false;", boolean.class);
        assertFalse((boolean) tsClass.invoke());
    }
}
