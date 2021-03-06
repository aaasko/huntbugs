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

import one.util.huntbugs.registry.anno.AssertNoWarning;
import one.util.huntbugs.registry.anno.AssertWarning;

/**
 * @author Tagir Valeev
 *
 */
public class TestStartInConstructor {
    @AssertWarning(type="StartInConstructor", minScore=45, maxScore=55)
    public TestStartInConstructor() {
        new Thread(() -> System.out.println()).start();
        System.out.println("Started!");
    }
    
    @AssertWarning(type="StartInConstructor", minScore=45, maxScore=55)
    public TestStartInConstructor(String s, int x) {
        new Thread() {
            @Override
            public void run() {
                System.out.println("Thread!");
            }
        }.start();
        System.out.println("Started!");
    }
    
    @AssertWarning(type="StartInConstructor", minScore=35, maxScore=45)
    public TestStartInConstructor(int x) {
        new Thread(() -> System.out.println()).start();
    }
    
    @AssertNoWarning(type="StartInConstructor")
    private TestStartInConstructor(String s) {
        new Thread(() -> System.out.println()).start();
    }
    
    public class SubClass extends TestStartInConstructor {
        @AssertWarning(type="StartInConstructor", minScore=25, maxScore=35)
        public SubClass(int x) {
            new Thread(() -> System.out.println()).start();
        }
    }
}
