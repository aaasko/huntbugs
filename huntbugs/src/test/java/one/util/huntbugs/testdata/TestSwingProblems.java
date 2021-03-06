/*
 * Copyright 2016 HuntBugs contributors
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
package one.util.huntbugs.testdata;

import javax.swing.JPanel;

import one.util.huntbugs.registry.anno.AssertNoWarning;
import one.util.huntbugs.registry.anno.AssertWarning;

/**
 * @author Tagir Valeev
 *
 */
public class TestSwingProblems {
    @AssertNoWarning(type="SwingMethodNotInSwingThread")
    private static void run() {
        JPanel panel = new JPanel();
        panel.setVisible(true);
    }
    
    @AssertNoWarning(type="SwingMethodNotInSwingThread")
    public static void main(String[] args, boolean x) {
        JPanel panel = new JPanel();
        panel.setVisible(x && args.length > 0);
    }
    
    @AssertWarning(type="SwingMethodNotInSwingThread")
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        panel.setVisible(true);
    }
}
