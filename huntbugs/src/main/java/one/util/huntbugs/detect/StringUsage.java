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
package one.util.huntbugs.detect;

import com.strobel.assembler.metadata.MethodReference;
import com.strobel.decompiler.ast.AstCode;
import com.strobel.decompiler.ast.Expression;

import one.util.huntbugs.registry.MethodContext;
import one.util.huntbugs.registry.anno.AstNodes;
import one.util.huntbugs.registry.anno.AstVisitor;
import one.util.huntbugs.registry.anno.WarningDefinition;
import one.util.huntbugs.warning.WarningAnnotation;

/**
 * @author Tagir Valeev
 *
 */
@WarningDefinition(category="Performance", name="StringConstructor", maxScore=50)
@WarningDefinition(category="Performance", name="StringConstructorEmpty", maxScore=55)
@WarningDefinition(category="RedundantCode", name="StringToString", maxScore=40)
public class StringUsage {
    @AstVisitor(nodes=AstNodes.EXPRESSIONS)
    public void visit(Expression node, MethodContext mc) {
        if(node.getCode() == AstCode.InitObject) {
            MethodReference mr = (MethodReference) node.getOperand();
            if(mr.getDeclaringType().getInternalName().equals("java/lang/String")) {
                if(mr.getSignature().equals("()V")) {
                    mc.report("StringConstructorEmpty", 0, node);
                } else if(mr.getSignature().equals("(Ljava/lang/String;)V")) {
                    mc.report("StringConstructor", 0, node);
                }
            }
        } else if(node.getCode() == AstCode.InvokeVirtual) {
            MethodReference mr = (MethodReference) node.getOperand();
            if(mr.getDeclaringType().getInternalName().equals("java/lang/String") && mr.getName().equals("toString")) {
                mc.report("StringToString", 0, node.getArguments().get(0), WarningAnnotation.forMember("CALLED_METHOD", mr));
            }
        }
    }
}
