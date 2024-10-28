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

package com.caoccao.javet.buddy.ts2java;

import org.junit.jupiter.api.Test;

public class TestAssignments extends BaseTestTs2Java {
    protected static Class<?> clazz = null;

    public TestAssignments() {
        super();
        init();
    }

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
     */
    public double assignAndCast(int a, long b) {
        long c = a;
        long d = b;
        return c + d;
    }

    protected void init() {
    }

    /*
  public sum(I)I
   L0
    LINENUMBER 42 L0
    ICONST_0
    ISTORE 2
   L1
    LINENUMBER 43 L1
    ICONST_0
    ISTORE 3
   L2
   FRAME APPEND [I I]
    ILOAD 3
    ILOAD 1
    IF_ICMPGE L3
   L4
    LINENUMBER 44 L4
    ILOAD 2
    ILOAD 3
    IADD
    ISTORE 2
   L5
    LINENUMBER 43 L5
    IINC 3 1
    GOTO L2
   L3
    LINENUMBER 46 L3
   FRAME CHOP 1
    ILOAD 2
    IRETURN
     */
    public int sum(int a) {
        int sum = 0;
        for (int i = 0; i < a; i++) {
            sum += i;
        }
        return sum;
    }

    @Test
    public void testAssignAndCast() throws Exception {
    }
}
