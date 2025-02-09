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

package com.caoccao.javet.buddy.ts2java.ast.interfaces.abilities;

import com.caoccao.javet.buddy.ts2java.ast.interfaces.ITs2JavaAst;

import java.util.Optional;

public interface ITs2JavaMinusFlippable {
    static void flipMinus(ITs2JavaAst<?, ?> ast) {
        if (isMinusFlippable(ast)) {
            ast.as(ITs2JavaMinusFlippable.class).flipMinus();
        }
    }

    static boolean isMinusFlippable(ITs2JavaAst<?, ?> ast) {
        return Optional.of(ast instanceof ITs2JavaMinusFlippable)
                .filter(b -> b)
                .map(b -> ast.as(ITs2JavaMinusFlippable.class).isMinusFlippable())
                .orElse(false);
    }

    void flipMinus();

    boolean isMinusFlippable();
}
