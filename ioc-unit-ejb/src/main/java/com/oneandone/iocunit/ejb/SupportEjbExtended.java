/*
 *    Copyright 2014 Bryn Cooke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneandone.iocunit.ejb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.oneandone.iocunit.analyzer.annotations.TestClasses;
import com.oneandone.iocunit.ejb.jms.JmsMocksFactory;
import com.oneandone.iocunit.ejb.jms.JmsProducers;
import com.oneandone.iocunit.ejb.resourcesimulators.MessageContextSimulation;
import com.oneandone.iocunit.ejb.resourcesimulators.TimerServiceSimulator;
import com.oneandone.iocunit.ejb.resourcesimulators.WebServiceContextSimulation;


/**
 * Enable support for Ejb
 * 
 * @author aschoerk
 *
 */
@TestClasses({ EjbExtensionExtended.class, TransactionalInterceptor.class,
        AsynchronousMethodInterceptor.class, JmsMocksFactory.class, JmsProducers.class, SessionContextFactory.class,
        MessageContextSimulation.class, WebServiceContextSimulation.class,
        TimerServiceSimulator.class, EjbUnitBeanInitializerClass.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportEjbExtended {

}
