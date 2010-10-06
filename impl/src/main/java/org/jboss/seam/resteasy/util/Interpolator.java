package org.jboss.seam.resteasy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.weld.extensions.el.Expressions;

@ApplicationScoped
public class Interpolator
{
   public static final Pattern elPattern = Pattern.compile("(#\\{.*?\\})");
   @Inject
   private Expressions el;

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
