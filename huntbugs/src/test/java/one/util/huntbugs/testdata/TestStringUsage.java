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
public class TestStringUsage {
    @AssertWarning(type="StringConstructorEmpty")
    public String testStringCtor() {
        return new String();
    }
    
    @AssertWarning(type="StringConstructor")
    public String testStringCtor2() {
        return new String("test");
    }

    @AssertNoWarning(type="*")
    public String testStringCtor3() {
        return new String(new char[] {'c'});
    }
    
    @AssertWarning(type="StringToString")
    public String testStringToString(String s) {
        return s.toString();
    }
    
    @AssertNoWarning(type="*")
    public String testObjectToString(Object s) {
        if(s instanceof String)
            return s.toString();
        return null;
    }
}
