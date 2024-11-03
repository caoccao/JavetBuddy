/*
 * Copyright (c) 2024-2024. caoccao.com Sam Cao
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

package com.caoccao.javet.buddy.ts2java.ast;

import com.caoccao.javet.buddy.ts2java.BaseTestTs2Java;
import com.caoccao.javet.buddy.ts2java.TsClass;
import com.caoccao.javet.buddy.ts2java.TsMethodArgument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAssignments extends BaseTestTs2Java {
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

    /*
  public assignConst(IJ)J
   L0
    LINENUMBER 97 L0
    BIPUSH 100
    ISTORE 4
   L1
    LINENUMBER 98 L1
    LDC 2
    LSTORE 5
   L2
    LINENUMBER 99 L2
    ILOAD 1
    I2L
    LLOAD 2
    LADD
    ILOAD 4
    I2L
    LADD
    LLOAD 5
    LADD
    LRETURN
   L3
    LOCALVARIABLE this Lcom/caoccao/javet/buddy/ts2java/TestAssignments; L0 L3 0
    LOCALVARIABLE a I L0 L3 1
    LOCALVARIABLE b J L0 L3 2
    LOCALVARIABLE c I L1 L3 4
    LOCALVARIABLE d J L2 L3 5
    MAXSTACK = 4
    MAXLOCALS = 7
     */
    public long assignConst(int a, long b) {
        int c = 100;
        long d = 2;
        return a + b + c + d;
    }

    @Test
    public void testAssignAndCast() throws Exception {
        assertEquals(3.0D, assignAndCast(1, 2L), 0.001D);
        TsClass tsClass = new TsClass(
                "let c: long = a;\n" +
                        "let d: long = b;\n" +
                        "return c + d;",
                double.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(3.0D, (double) tsClass.invoke(1, 2L), 0.001D);
    }

    @Test
    public void testAssignConst() throws Exception {
        assertEquals(105L, assignConst(1, 2L));
        TsClass tsClass = new TsClass(
                "const c: int = 100;\n" +
                        "const d: long = 2;\n" +
                        "return a + b + c + d;",
                long.class,
                TsMethodArgument.of("a", int.class),
                TsMethodArgument.of("b", long.class));
        assertEquals(105L, tsClass.invoke(1, 2L));
    }
}
