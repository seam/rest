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
package org.jboss.seam.rest.exceptions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.logging.Logger;
import org.jboss.seam.rest.exceptions.ExceptionMapping;
import org.jboss.seam.rest.exceptions.Mapping;
import org.jboss.seam.rest.exceptions.integration.CatchExceptionMapper;
import org.jboss.seam.rest.exceptions.integration.CatchValidationExceptionHandler;
import org.jboss.seam.rest.util.Utils;

public class ExceptionMappingExtension implements Extension
{
   private static final Logger log = Logger.getLogger(ExceptionMappingExtension.class);
   private static final String SEAM_CATCH_NAME = "org.jboss.seam.exception.control.extension.CatchExtension";

   private Set<Mapping> exceptionMappings = new HashSet<Mapping>();
   private boolean catchIntegrationEnabled = false;
   
   public void rergisterExceptionMapping(@Observes BeforeBeanDiscovery event, BeanManager manager)
   {
      catchIntegrationEnabled = Utils.isClassAvailable(SEAM_CATCH_NAME);
      
      // General Exception mapping
      if (!catchIntegrationEnabled)
      {
         event.addAnnotatedType(manager.createAnnotatedType(SeamExceptionMapper.class));
      }
      event.addAnnotatedType(manager.createAnnotatedType(ErrorMessageWrapper.class));
      event.addAnnotatedType(manager.createAnnotatedType(ResponseBuilderProducer.class));
      
      // Seam Catch integration
      if (catchIntegrationEnabled)
      {
         log.info("Catch integration enabled.");
         event.addAnnotatedType(manager.createAnnotatedType(CatchExceptionMapper.class));
         event.addAnnotatedType(manager.createAnnotatedType(CatchValidationExceptionHandler.class));
      }
   }

   public <T> void findExceptionMappingDeclaration(@Observes ProcessAnnotatedType<T> event)
   {
      AnnotatedType<T> type = event.getAnnotatedType();
      
      ExceptionMapping.List mappings = type.getAnnotation(ExceptionMapping.List.class);
      if (mappings != null)
      {
         for (ExceptionMapping mapping : mappings.value())
         {
            addExceptionMapping(mapping);
         }
      }
      
      // also, single @DeclarativeExceptionMapping can be used
      ExceptionMapping mapping = type.getAnnotation(ExceptionMapping.class);
      if (mapping != null)
      {
         addExceptionMapping(mapping);
      }
   }
   
   private void addExceptionMapping(ExceptionMapping mapping)
   {
      exceptionMappings.add(new Mapping(mapping.exceptionType(), mapping.status(), mapping.message(), mapping.useExceptionMessage(), mapping.interpolateMessage(), mapping.useJaxb()));
   }
      
   public Set<Mapping> getExceptionMappings()
   {
      return Collections.unmodifiableSet(exceptionMappings);
   }

   public boolean isCatchIntegrationEnabled()
   {
      return catchIntegrationEnabled;
   }
}
