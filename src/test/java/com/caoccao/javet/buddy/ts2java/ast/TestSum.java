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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSum extends BaseTestTs2Java {
    /*
  public sum(I)I
   L0
    ICONST_0
    ISTORE 2
   L1
    ICONST_0
    ISTORE 3
   L2
   FRAME APPEND [I I]
    ILOAD 3
    ILOAD 1
    IF_ICMPGE L3
   L4
    ILOAD 2
    ILOAD 3
    IADD
    ISTORE 2
   L5
    IINC 3 1
    GOTO L2
   L3
   FRAME CHOP 1
    ILOAD 2
    IRETURN
     */
    public int sum(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        return sum;
    }

    @Test
    public void testSum() {
        assertEquals(45, sum(10));
    }
}
