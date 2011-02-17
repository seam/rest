package org.jboss.seam.rest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.solder.el.Expressions;

@ApplicationScoped
public class Interpolator
{
   public static final Pattern elPattern = Pattern.compile("(#\\{.*?\\})");
   private Expressions el;
   
   /**
    * Workaround for GlassFish
    */
   @Inject
   public void init(Instance<Expressions> expressions)
   {
      el = expressions.get();
   }

   /**
    * Evaluates EL expressions contained in the input String.
    */
   public String interpolate(String input)
   {
      Matcher matcher = elPattern.matcher(input);
      StringBuffer buffer = new StringBuffer();

      while (matcher.find())
      {
         matcher.appendReplacement(buffer, String.valueOf(el.evaluateValueExpression(matcher.group())));
      }
      matcher.appendTail(buffer);

      return buffer.toString();
   }
}