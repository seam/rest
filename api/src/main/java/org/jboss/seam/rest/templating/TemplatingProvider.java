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
package org.jboss.seam.rest.templating;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Implementations of this interface represent integration points for various
 * templating engines.
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
public interface TemplatingProvider
{
   /**
    * Initialization callback. The method is guaranteed to be invoked before calling the writeTo() method.
    * @param servletContext
    */
   void init(ServletContext servletContext);
   
   /**
    * Produces the response.
    * @param result object returned by the JAX-RS method
    * @param annotation @ResponseTemplate annotation instance
    * @param annotations annotations available on the JAX-RS method
    * @param mediaType requested media type
    * @param httpHeaders request HTTP headers
    * @param os HTTP response output stream
    * @throws IOException
    */
   void writeTo(Object result, ResponseTemplate annotation, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException;
}
