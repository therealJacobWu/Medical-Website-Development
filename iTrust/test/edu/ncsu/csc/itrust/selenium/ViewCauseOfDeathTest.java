package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;


public class ViewCauseOfDeathTest extends iTrustSeleniumTest {

    private HtmlUnitDriver driver;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
        gen.icd9cmCodes();
        gen.patient2();
        gen.patient1000s();
    }

    public void testAllGender() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");
        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
        driver.findElement(By.linkText("Cause Of Death")).click();
        assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp/viewCauseOfDeath.jsp"));
        WebElement elem = null;
        try {
            elem = driver.findElement(By.id("causeOfDeathStatisticsTable"));
            fail("Element should not be visible");
        } catch (NoSuchElementException d) {
            assertNull(elem);
        }
        //assertFalse(isElementPresent(By.id("diagnosisStatisticsTable")));
        //new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
        //driver.findElement(By.id("select_View")).click();
        new Select(driver.findElement(By.name("gender"))).selectByVisibleText("All");

        driver.findElement(By.name("startDate")).clear();
        driver.findElement(By.name("startDate")).sendKeys("01/01/1970");
        driver.findElement(By.name("endDate")).clear();
        driver.findElement(By.name("endDate")).sendKeys("01/01/2017");

        driver.findElement(By.id("select")).click();
        try {
            elem = driver.findElement(By.id("causeOfDeathStatisticsTable"));
            assertTrue(elem != null);
        } catch (NoSuchElementException d) {
            fail("Element should be present");
        }
    }

    public void testInvalidDate() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");
        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
        driver.findElement(By.linkText("Cause Of Death")).click();
        assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp/viewCauseOfDeath.jsp"));
        WebElement elem = null;
        try {
            elem = driver.findElement(By.id("causeOfDeathStatisticsTable"));
            fail("Element should not be visible");
        } catch (NoSuchElementException d) {
            assertNull(elem);
        }
        //assertFalse(isElementPresent(By.id("diagnosisStatisticsTable")));
        //new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
        //driver.findElement(By.id("select_View")).click();
        new Select(driver.findElement(By.name("gender"))).selectByVisibleText("Male");

        driver.findElement(By.name("startDate")).clear();
        driver.findElement(By.name("startDate")).sendKeys("somethingInvalid");
        driver.findElement(By.name("endDate")).clear();
        driver.findElement(By.name("endDate")).sendKeys("somethingInvalid");

        driver.findElement(By.id("select")).click();
        try {
            elem = driver.findElement(By.id("causeOfDeathStatisticsTable"));
            fail("Element should not be visible");
        } catch (NoSuchElementException d) {
            assertNull(elem);
        }
    }
}
