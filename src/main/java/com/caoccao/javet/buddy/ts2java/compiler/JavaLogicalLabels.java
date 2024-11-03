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

package com.caoccao.javet.buddy.ts2java.compiler;

import net.bytebuddy.jar.asm.Label;

import java.util.ArrayList;
import java.util.List;

public final class JavaLogicalLabels {
    private final List<Label> labels; // 0: LabelEnd, 1: LabelFalse, 2: LabelTrue

    public JavaLogicalLabels() {
        labels = new ArrayList<>();
        reset();
    }

    public Label append() {
        Label label = new Label();
        labels.add(label);
        return label;
    }

    public Label get(int index) {
        return labels.get(index);
    }

    public Label getLabelEnd() {
        return labels.get(0);
    }

    public Label getLabelFalse() {
        return labels.get(1);
    }

    public Label getLabelTrue() {
        return labels.get(2);
    }

    public Label getLastLabel() {
        return labels.get(labels.size() - 1);
    }

    public boolean hasLabelTrue() {
        return labels.size() > 2;
    }

    public void reset() {
        labels.clear();
        labels.add(new Label()); // Label for Return
        labels.add(new Label()); // Label for False
    }
}
