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

import java.io.IOException;

import one.util.huntbugs.registry.anno.AssertNoWarning;
import one.util.huntbugs.registry.anno.AssertWarning;

/**
 * @author Tagir Valeev
 *
 */
public class TestNaming {
    @AssertWarning(type="BadNameOfField")
    public String Abc;

    @AssertNoWarning(type="BadNameOfField")
    public String abc;
    
    @AssertNoWarning(type="BadNameOfField")
    public final String Empty = "";
    
    @AssertNoWarning(type="BadNameOfMethod")
    public void AB() {
        System.out.println();
    }

    @AssertWarning(type="BadNameOfMethod", minScore=28)
    public void Test() {
        System.out.println();
    }
    
    @AssertWarning(type="BadNameOfMethodSameAsConstructor", minScore=60)
    public void TestNaming() {
        System.out.println();
    }

    @AssertWarning(type="BadNameOfMethodSameAsConstructor", maxScore=50)
    public void TestNaming(String s) {
        throw new UnsupportedOperationException(s);
    }
    
    @AssertWarning(type="BadNameOfMethod", minScore=25, maxScore=28)
    protected void Test2() {
        System.out.println();
    }
    
    @AssertWarning(type="BadNameOfMethod", minScore=22, maxScore=25)
    void Test3() {
        System.out.println();
    }
    
    @AssertWarning(type="BadNameOfMethodMistake", minScore=55)
    public int hashcode() {
        return 31;
    }
    
    @AssertNoWarning(type="BadNameOfMethodMistake")
    public int hashcode(int x) {
        return x;
    }

    @AssertWarning(type="BadNameOfMethodMistake", minScore=55)
    public String tostring() {
        return "MyClass";
    }
    
    @AssertWarning(type="BadNameOfMethodMistake", minScore=55)
    public boolean equal(Object obj) {
        return obj == this;
    }
    
    public static class Class1 {
        @AssertWarning(type="BadNameOfMethod", minScore=15, maxScore=20)
        private void Test4() {
            System.out.println();
        }

        @AssertNoWarning(type="BadNameOfMethodMistake")
        private int hashcode() {
            return 31;
        }
    }
    
    static class Class2 {
        @AssertWarning(type="BadNameOfMethod", minScore=5, maxScore=10)
        private void Test5() {
            System.out.println();
        }

        @AssertNoWarning(type="BadNameOfMethodMistake")
        public static int hashcode() {
            return 31;
        }
    }
    
    @AssertWarning(type="BadNameOfClass", minScore=30)
    public static class cls1 {
        // empty
    }
    
    @AssertWarning(type="BadNameOfClass", maxScore=20)
    protected static class cls2 {
        // empty
    }
    
    @AssertWarning(type="BadNameOfClassException", minScore=40)
    public static class MyException {
        // empty
    }
    
    @AssertNoWarning(type="BadNameOfClassException")
    public static class MyOkException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    @AssertWarning(type="BadNameOfClassSameAsSuperclass")
    public static class File extends java.io.File {
        private static final long serialVersionUID = 1L;

        public File(String pathname) {
            super(pathname);
        }
    }
    
    @AssertWarning(type="BadNameOfClassSameAsInterface")
    public static class Closeable implements Cloneable, java.io.Closeable {
        @Override
        public void close() throws IOException {
            // empty
        }
    }
}
