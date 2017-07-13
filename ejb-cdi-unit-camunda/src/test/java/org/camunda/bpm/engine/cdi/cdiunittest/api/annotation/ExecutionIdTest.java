/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.cdi.cdiunittest.api.annotation;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import com.oneandone.ejbcdiunit.EjbUnitRunner;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.annotation.ExecutionIdLiteral;
import org.camunda.bpm.engine.cdi.cdiunittest.CdiProcessEngineTestCase;
import org.camunda.bpm.engine.test.Deployment;;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Daniel Meyer
 */
@RunWith(EjbUnitRunner.class)
public class ExecutionIdTest extends CdiProcessEngineTestCase {
  
  @Test
  @Deployment
  public void testExecutionIdInjectableByName() {
    getBeanInstance(BusinessProcess.class).startProcessByKey("keyOfTheProcess");
    String processInstanceId = (String) getBeanInstance("processInstanceId");
    Assert.assertNotNull(processInstanceId);
    String executionId = (String) getBeanInstance("executionId");
    Assert.assertNotNull(executionId);
    
    assertEquals(processInstanceId, executionId);
  }
  
  @Test
  @Deployment
  public void testExecutionIdInjectableByQualifier() {
    getBeanInstance(BusinessProcess.class).startProcessByKey("keyOfTheProcess");
    
    Set<Bean<?>> beans = beanManager.getBeans(String.class, new ExecutionIdLiteral());    
    Bean<String> bean = (Bean<java.lang.String>) beanManager.resolve(beans);
    
    CreationalContext<String> ctx = beanManager.createCreationalContext(bean);
    String executionId = (String) beanManager.getReference(bean, String.class, ctx);   
    Assert.assertNotNull(executionId);
    
    String processInstanceId = (String) getBeanInstance("processInstanceId");
    Assert.assertNotNull(processInstanceId);
    
    assertEquals(processInstanceId, executionId);
  }
  
}
