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

package com.caoccao.javet.buddy.ts2java.ast.enums;

import com.caoccao.javet.swc4j.ast.enums.Swc4jAstAccessibility;
import net.bytebuddy.description.modifier.Visibility;

public final class Ts2JavaAstAccessibility {
    private Ts2JavaAstAccessibility() {
    }

    public static Visibility getVisibility(Swc4jAstAccessibility accessibility) {
        switch (accessibility) {
            case Protected:
                return Visibility.PROTECTED;
            case Private:
                return Visibility.PRIVATE;
            default:
                return Visibility.PUBLIC;
        }
    }
}
