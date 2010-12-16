/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.rest;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.exceptions.CatchJaxrsBridge;
import org.jboss.seam.rest.exceptions.ErrorMessageWrapper;
import org.jboss.seam.rest.exceptions.ResponseBuilderProducer;
import org.jboss.seam.rest.exceptions.SeamExceptionHandler;
import org.jboss.seam.rest.util.ExpressionLanguageInterpolator;
import org.jboss.seam.rest.util.Interpolator;
import org.jboss.seam.rest.validation.ValidationExceptionHandler;
import org.jboss.seam.rest.validation.ValidationInterceptor;
import org.jboss.seam.rest.validation.ValidationMetadata;

/**
 * Registers Seam REST components.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public class SeamRestExtension implements Extension
{
   private static final Logger log = Logger.getLogger(SeamRestExtension.class);
   
   /**
    * The following components are registered:
    * <ul>
    * <li>Bean Validation integration components</li>
    * <li>Exception mapping components</li>
    * <li>Utilities</li>
    * </ul>
    */
   void registerSeamRest(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      log.info("Seam REST Extension starting...");
      event.addAnnotatedType(manager.createAnnotatedType(Interpolator.class));
      event.addAnnotatedType(manager.createAnnotatedType(ExpressionLanguageInterpolator.class));
      
      // Bean Validation integration
      event.addAnnotatedType(manager.createAnnotatedType(ValidationExceptionHandler.class));
      event.addAnnotatedType(manager.createAnnotatedType(ValidationInterceptor.class));
      event.addAnnotatedType(manager.createAnnotatedType(ValidationMetadata.class));
      
      // Catch integration
      event.addAnnotatedType(manager.createAnnotatedType(CatchJaxrsBridge.class));
      event.addAnnotatedType(manager.createAnnotatedType(ErrorMessageWrapper.class));
      event.addAnnotatedType(manager.createAnnotatedType(ResponseBuilderProducer.class));
      event.addAnnotatedType(manager.createAnnotatedType(SeamExceptionHandler.class));
   }
}
