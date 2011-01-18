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
package org.jboss.seam.rest.test;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.inject.Inject;

import org.jboss.seam.solder.el.Expressions;

@Mock
public class MockExpressions extends Expressions
{
   @Inject
   private University university;
   
   public MockExpressions()
   {
      super(new ElContextImpl(true), new ExpressionFactoryImpl(true));
   }

   @SuppressWarnings("unchecked")
   @Override
   public <T> T evaluateValueExpression(String expression)
   {
      if ("#{fox.color}".equals(expression))
      {
         return (T) "brown";
      }
      else if ("#{fox.count == 1 ? 'fox' : 'foxes'}".equals(expression))
      {
         return (T) "fox";
      }
      else if ("#{service.host}".equals(expression))
      {
         return (T) "example.com";
      }
      else if ("#{service.context.path}".equals(expression))
      {
         return (T) "service";
      }
      else if ("#{university}".equals(expression))
      {
         return (T) university;
      }
      else return (T) expression;
   }
   
   public static class ElContextImpl extends ELContext
   {
      public ElContextImpl(boolean foo)
      {
         super();
      }

      @Override
      public ELResolver getELResolver()
      {
         return null;
      }

      @Override
      public FunctionMapper getFunctionMapper()
      {
         return null;
      }

      @Override
      public VariableMapper getVariableMapper()
      {
         return null;
      }
   }
   
   public static class ExpressionFactoryImpl extends ExpressionFactory
   {
      public ExpressionFactoryImpl(boolean foo)
      {
         super();
      }
      
      @Override
      public ValueExpression createValueExpression(ELContext context, String expression, Class<?> expectedType)
      {
         return null;
      }

      @Override
      public ValueExpression createValueExpression(Object instance, Class<?> expectedType)
      {
         return null;
      }

      @Override
      public MethodExpression createMethodExpression(ELContext context, String expression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes)
      {
         return null;
      }

      @Override
      public Object coerceToType(Object obj, Class<?> targetType)
      {
         return null;
      }
   }
}
