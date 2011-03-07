package org.jboss.seam.rest.example.jaxrs.ftest;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for a JAX-RS Exceptions example
 * 
 * @author Martin Gencur
 * 
 */
public class ExceptionHandlingTest extends AbstractTestCase
{
   protected XpathLocator ILLEGAL_ACCESS_EXCEPTION = xp("//a[contains(@href,'IllegalAccessException')]");
   protected XpathLocator NULL_POINTER_EXCEPTION = xp("//a[contains(@href,'NullPointerException')]");
   protected XpathLocator RUNTIME_EXCEPTION = xp("//a[contains(text(),'RuntimeException')]");
   protected XpathLocator STRING_INDEX_EXCEPTION = xp("//a[contains(@href,'StringIndexOutOfBoundsException')]");
   protected XpathLocator ARRAY_INDEX_EXCEPTION = xp("//a[contains(@href,'ArrayIndexOutOfBoundsException')]");
   protected XpathLocator INDEX_EXCEPTION = xp("//a[contains(@href,'java.lang.IndexOutOfBoundsException')]");
   protected XpathLocator ARITHMETIC_EXCEPTION = xp("//a[contains(@href,'ArithmeticException')]");

   @BeforeMethod
   public void openStartUrl() throws MalformedURLException
   {
      selenium.open(new URL(contextPath.toString()));
   }

   @Test
   public void testAnnotationBasedConfiguration()
   {
      waitHttp(selenium).click(ILLEGAL_ACCESS_EXCEPTION);
      assertTrue(selenium.isTextPresent("HTTP Status 403"), "Status code 403 should be displayed");
      assertTrue(selenium.isTextPresent("Access to the specified resource () has been forbidden."), "A status message should be displayed");
      selenium.goBack();
      String urlSuffix = "api/exceptions?exception=java.lang.NullPointerException";
      String text = "<error><message>NullPointerException was thrown.</message></error>";
      int expectedStatus = 500;
      assertTrue(checkResponse(urlSuffix, text, expectedStatus), "The resulting XML file should contain 'NullPointerException was thrown'");
      urlSuffix = "api/exceptions?exception=java.lang.Object";
      text = "<error><message>java.lang.Object is not an exception</message></error>";
      assertTrue(checkResponse(urlSuffix, text, expectedStatus), "The resulting XML file should contain 'java.lang.Object is not an exception'");
   }

   @Test
   public void testXmlBasedConfiguration()
   {
      waitHttp(selenium).click(STRING_INDEX_EXCEPTION);
      assertTrue(selenium.isTextPresent("HTTP Status 500"), "Status code 500 should be displayed");
      assertTrue(selenium.isTextPresent("The server encountered an internal error () that prevented it from fulfilling this request."), "A status message should be displayed");
      selenium.goBack();
      String url = "api/exceptions?exception=java.lang.ArrayIndexOutOfBoundsException";
      String text = "<error><message>ArrayIndexOutOfBoundsException was thrown.</message></error>";
      int expectedStatus = 500;
      assertTrue(checkResponse(url, text, expectedStatus), "The resulting XML file should contain 'ArrayIndexOutOfBoundsException was thrown.'");
      url = "api/exceptions?exception=java.lang.IndexOutOfBoundsException";
      text = "<error><message>An exception was thrown. The date is:";
      assertTrue(checkResponse(url, text, expectedStatus), "The resulting XML file should contain 'An exception was thrown. The date is: ...'");
   }

   @Test
   public void testProgrammaticHandling()
   {
      waitHttp(selenium).click(ARITHMETIC_EXCEPTION);
      assertTrue(selenium.isTextPresent("Cannot divide by zero. Want to divide by two instead?"), "An error message should be displayed");
   }

   private boolean checkResponse(String urlSuffix, String textToFind, int expectedStatus)
   {
      try
      {
         URL completeURL = new URL(contextPath.toString() + urlSuffix);
         HttpURLConnection httpCon = (HttpURLConnection) completeURL.openConnection();
         httpCon.connect();
         if (httpCon.getResponseCode() != expectedStatus)
         {
            return false;
         }
         BufferedReader r = new BufferedReader(new InputStreamReader(httpCon.getErrorStream()));
         String str;
         StringBuffer sb = new StringBuffer();
         while ((str = r.readLine()) != null)
         {
            sb.append(str);
         }
         httpCon.disconnect();
         return sb.toString().contains(textToFind);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         return false;
      }
   }
}
