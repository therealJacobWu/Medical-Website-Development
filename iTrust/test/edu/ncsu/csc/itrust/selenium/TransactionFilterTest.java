package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class TransactionFilterTest extends iTrustSeleniumTest {

    WebDriver driver;
    WebElement element;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    @Test
    public void testTransactionFilterAdmin() throws Exception {
        driver = login("9000000001", "pw"); //log in as Admin

        assertEquals("iTrust - Admin Home", driver.getTitle());

        //go to the page
        element = driver.findElement(By.linkText("Admin Transaction Log"));
        element.click();

        assertEquals("FOR TESTING PURPOSES ONLY", driver.getTitle());

        int oldTransactions = driver.findElements(By.className("transaction_row")).size();

        driver.findElement(By.cssSelector("input[value='View']")).submit();

        // Page should show table with same # of entries
        assertEquals(oldTransactions, driver.findElements(By.className("transaction_row")).size());
    }

    @Test
    public void testTransactionFilterAdminGraph() throws Exception {
        driver = login("9000000001", "pw"); //log in as Admin

        assertEquals("iTrust - Admin Home", driver.getTitle());

        //go to the page
        element = driver.findElement(By.linkText("Admin Transaction Log"));
        element.click();

        assertEquals("FOR TESTING PURPOSES ONLY", driver.getTitle());

        driver.findElement(By.cssSelector("input[value='Summarize']")).submit();

        assertNotNull(driver.findElement(By.id("container1")));
        assertNotNull(driver.findElement(By.id("container2")));
        assertNotNull(driver.findElement(By.id("container3")));
    }

    @Test
    public void testTransactionFilteringValid1() throws Exception {
        driver = login("9000000001", "pw"); //log in as Admin

        assertEquals("iTrust - Admin Home", driver.getTitle());

        //go to the page
        element = driver.findElement(By.linkText("Admin Transaction Log"));
        element.click();

        assertEquals("FOR TESTING PURPOSES ONLY", driver.getTitle());

        Select loggedInUserField = new Select(driver.findElement(By.name("loggedInUserField")));
        loggedInUserField.selectByValue("hcp");

        Select secondaryUserField = new Select(driver.findElement(By.name("secondaryUserField")));
        secondaryUserField.selectByValue("patient");

        driver.findElement(By.name("startDate")).sendKeys("01/01/2007");
        driver.findElement(By.name("endDate")).sendKeys("12/31/2008");

        Select transactionType = new Select(driver.findElement(By.name("transactionType")));
        transactionType.selectByValue("-1");

        driver.findElement(By.cssSelector("input[value='View']")).submit();
        assertEquals(34, driver.findElements(By.className("transaction_row")).size());
    }
}
