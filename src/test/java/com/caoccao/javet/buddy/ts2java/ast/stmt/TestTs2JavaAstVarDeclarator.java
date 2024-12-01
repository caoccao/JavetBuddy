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

public class TestTs2JavaAstVarDeclarator extends BaseTestTs2Java {
    /*
  public assignAndCast(IJ)D
   L0
    LINENUMBER 30 L0
    ILOAD 1
    I2L
    LSTORE 4
   L1
    LINENUMBER 31 L1
    LLOAD 2
    LSTORE 6
   L2
    LINENUMBER 32 L2
    LLOAD 4
    LLOAD 6
    LADD
    L2D
    DRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestAssignments; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b J L0 L3 2
    LOCALVARIABLE c J L1 L3 4
    LOCALVARIABLE d J L2 L3 6
    MAXSTACK = 4
    MAXLOCALS = 8
     */
    public double assignAndCast(int a, long b) {
        long c = a;
        long d = b;
        return c + d;
    }

//    @Test
    public void testAssignAndCast() throws Exception {
        assertEquals(3.0D, assignAndCast(1, 2L), 0.001D);
        enableLogging();
        tsClass = new TsClass(
                "let c: long = a;\n" +
                        "let d: long = b;\n" +
                        "return c + d;",
                double.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(3.0D, (double) tsClass.invoke(1, 2L), 0.001D);
    }
}
