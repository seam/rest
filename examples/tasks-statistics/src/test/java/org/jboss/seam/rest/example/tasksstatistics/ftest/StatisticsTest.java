package org.jboss.seam.rest.example.tasksstatistics.ftest;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for a Tasks Statistics example
 * 
 * @author Martin Gencur
 * 
 */
public class StatisticsTest extends AbstractTestCase
{
   protected XpathLocator CALCULATE_BUTTON = xp("//input[contains(@value,'Calculate statistics')]");
   protected XpathLocator SEARCH_ZIP_BUTTON = xp("//input[contains(@value,'Search zip code')]");
   protected XpathLocator SEARCH_ZIP_INPUT = xp("//input[@type='text']");
   protected XpathLocator BACK_LINK = xp("//a[contains(text(),'Back')]");

   @BeforeMethod
   public void openStartUrl() throws MalformedURLException
   {
      selenium.setSpeed(100);
      selenium.open(new URL(contextPath.toString()));
   }

   @Test
   public void testHomePage()
   {
      assertTrue(selenium.isTextPresent("The Seam Tasks Statistics application demostrates use of @RestClient injection. "),
         "The home page should be displayed correctly");
      assertTrue(selenium.isElementPresent(CALCULATE_BUTTON), "A 'Calculate Statistics' button should be present on the page");
      assertTrue(selenium.isElementPresent(SEARCH_ZIP_INPUT), "A zip input area should be present on the page");
      assertTrue(selenium.isElementPresent(SEARCH_ZIP_BUTTON), "A 'Search zip code' button should be present on the page");
   }

   @Test(dependsOnMethods={"testHomePage"})
   public void testCalculateStatistics()
   {
      waitHttp(selenium).click(CALCULATE_BUTTON);
      assertTrue(selenium.isTextPresent("Number of unresolved tasks"), "Number of unresolved tasks should be present");
      assertTrue(selenium.isTextPresent("Tasks per category"), "Tasks per category should be present");
      assertTrue(selenium.isTextPresent("Number of resolved tasks"), "Number of resolved tasks should be present");
      assertTrue(selenium.isTextPresent("The oldest unresolved task"), "The oldest unresolved task should be present");
      assertTrue(selenium.isTextPresent("Build the Turing machine"), "The oldest unresolved task should be present");
      assertTrue(selenium.isTextPresent("Number of categories"), "Number of categories should be present");
      assertTrue(selenium.isTextPresent("Most often used word"), "Most often used word should be present");
      assertTrue(selenium.isTextPresent("Buy"), "Most often used word should be present");
      waitHttp(selenium).click(BACK_LINK);
   }
   
   @Test(dependsOnMethods={"testHomePage"})
   public void testSearchZipCode()
   {
      selenium.type(SEARCH_ZIP_INPUT, "73911");
      waitHttp(selenium).click(SEARCH_ZIP_BUTTON);
      assertTrue(selenium.isTextPresent("Found 7 locations."), "7 locations should have been found");
      assertTrue(selenium.isTextPresent("Frýdlant nad Ostravicí"), "The found location should be present");
      assertTrue(selenium.isTextPresent("49.58333"), "A latitude should be present");
      assertTrue(selenium.isTextPresent("18.35"), "A longitude should be present");
      assertTrue(selenium.isTextPresent("Nová Ves"), "Another location should be present");
      assertTrue(selenium.isTextPresent("18.36667"), "Another longitude should be present");
      assertTrue(selenium.isTextPresent("CZ"), "A country abbreviation should be present");
      waitHttp(selenium).click(BACK_LINK);
   }
}
