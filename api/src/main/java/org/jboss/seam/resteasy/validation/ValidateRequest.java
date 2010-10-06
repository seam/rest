package org.jboss.seam.resteasy.validation;

import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import javax.validation.groups.Default;

/**
 * Triggers validation of incomming HTTP requests.
 * The {@link Default} group is validated if not specified otherwise. By default, the message body parameter
 * and {@link org.jboss.resteasy.annotations.Form} parameters are validated. This behaviour can be altered
 * using {@link #validateMessageBody()} and {@link #validateFormParameters()} attributes. 
 * 
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 *
 */
@Target( { TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@Inherited
@InterceptorBinding
public @interface ValidateRequest
{
   /**
    * Validation group that will be used during validation process.
    */
   @Nonbinding
   Class<?>[] groups() default Default.class;
   
   /**
    * If set to false, the message body parameter will not be validated. 
    */
   @Nonbinding
   boolean validateMessageBody() default true;

   /**
    * If set to false, {@link org.jboss.resteasy.annotations.Form} parameters will not be validated. 
    * @return
    */
   @Nonbinding
   boolean validateFormParameters() default true;

   /**
    * Annotation literal for {@link ValidateRequest} interceptor binding.
    */
   static class ValidateLiteral extends AnnotationLiteral<ValidateRequest> implements ValidateRequest
   {
      private static final long serialVersionUID = 6404662043744038090L;
      
      private final Class<?>[] groups;
      private final boolean validateFormParameters;
      private final boolean validateMessageBody;
      
      public ValidateLiteral(Class<?>[] groups, boolean validateFormParameters, boolean validateMessageBody)
      {
         this.groups = groups;
         this.validateFormParameters = validateFormParameters;
         this.validateMessageBody = validateMessageBody;
      }

      public Class<?>[] groups()
      {
         return groups;
      }

      public boolean validateFormParameters()
      {
         return validateFormParameters;
      }

      public boolean validateMessageBody()
      {
         return validateMessageBody;
      }
   }
}
