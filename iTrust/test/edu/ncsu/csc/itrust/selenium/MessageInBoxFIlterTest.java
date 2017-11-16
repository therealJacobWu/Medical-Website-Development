package edu.ncsu.csc.itrust.selenium;
import edu.ncsu.csc.itrust.enums.TransactionType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MessageInBoxFIlterTest extends iTrustSeleniumTest{

    WebDriver driver;
    WebElement element;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    @Test
    public void testValidateAndCreateFilterHCP() throws Exception{
        driver = login("9000000000", "pw"); //log in as an HCP
        assertEquals("iTrust - HCP Home", driver.getTitle());

        //go to messageInbox
        assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
        driver.findElement(By.linkText("Message Inbox")).click();
        assertEquals("iTrust - View My Message ", driver.getTitle());
        assertLogged(TransactionType.INBOX_VIEW, 9000000000L, 0L, "");
        driver.findElement(By.linkText("Edit Filter")).click();
        //test the sender
        WebElement element  = driver.findElement(By.name("sender"));
        element.clear();
        element.sendKeys("Andy Programmer");
        driver.findElement(By.cssSelector("input[value=\"Test Filter\"]")).submit();
        assertTrue(driver.getPageSource().contains("Andy Programmer"));
        assertFalse(driver.getPageSource().contains("Random Person"));

        //test the HASWORDS
        element  = driver.findElement(By.name("hasWords"));
        element.clear();
        element.sendKeys("Appointment");
        driver.findElement(By.cssSelector("input[value=\"Test Filter\"]")).submit();
        assertTrue(driver.getPageSource().contains("Andy Programmer"));
        assertFalse(driver.getPageSource().contains("Baby Programmer"));
    }

    @Test
    public void testValidateAndCreateFilterPatient() throws Exception{
        driver = login("1", "pw"); //log in as an Patient
        assertEquals("iTrust - Patient Home", driver.getTitle());

        //go to messageInbox
        driver.findElement(By.linkText("Message Inbox")).click();
        assertEquals("iTrust - View My Message ", driver.getTitle());
        driver.findElement(By.linkText("Edit Filter")).click();
        //test the sender
        WebElement element  = driver.findElement(By.name("sender"));
        element.clear();
        element.sendKeys("Kelly Doctor");
        driver.findElement(By.cssSelector("input[value=\"Test Filter\"]")).submit();
        assertTrue(driver.getPageSource().contains("Kelly Doctor"));
    }
}
