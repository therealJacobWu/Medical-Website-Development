package edu.ncsu.csc.itrust.selenium;

import edu.ncsu.csc.itrust.enums.TransactionType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PreregisterTest extends iTrustSeleniumTest{

    public static final String ADDRESS = "http://localhost:8080/iTrust/";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    @Test
    public void testRegisteAndDuplicateEmail() throws  Exception {
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ADDRESS);

        // Fill in the prepatient form
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gg.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='streetAddress1']")).sendKeys("testAddr1");
        driver.findElement(By.xpath("//input[@name='streetAddress2']")).sendKeys("testAddr2");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("5005005000");
        driver.findElement(By.xpath("//input[@name='icName']")).sendKeys("testicName");
        driver.findElement(By.xpath("//input[@name='icAddress1']")).sendKeys("testicAddr1");
        driver.findElement(By.xpath("//input[@name='icAddress2']")).sendKeys("testicAddr2");
        driver.findElement(By.xpath("//input[@name='icPhone']")).sendKeys("6006006000");
        driver.findElement(By.xpath("//input[@name='height']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@name='weight']")).sendKeys("321");
        driver.findElement(By.id("smoker1Choice")).click();
        driver.findElement(By.id("preregistSubmit")).click();
        // Preregister test
        assertTrue(driver.getPageSource().contains("You have pre-registered."));

        driver.get(ADDRESS);

        // Fill in the prepatient form with the same email address
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName~");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName~");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gg.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='streetAddress1']")).sendKeys("testAddr1~");
        driver.findElement(By.xpath("//input[@name='streetAddress2']")).sendKeys("testAddr2~");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("50050050000");
        driver.findElement(By.xpath("//input[@name='icName']")).sendKeys("testicName~");
        driver.findElement(By.xpath("//input[@name='icAddress1']")).sendKeys("testicAddr1~");
        driver.findElement(By.xpath("//input[@name='icAddress2']")).sendKeys("testicAddr2~");
        driver.findElement(By.xpath("//input[@name='icPhone']")).sendKeys("60060060000");
        driver.findElement(By.xpath("//input[@name='height']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@name='weight']")).sendKeys("321");
        driver.findElement(By.id("smoker1Choice")).click();
        driver.findElement(By.id("preregistSubmit")).click();
        // Duplicate Email test
        assertTrue(driver.getPageSource().contains("A login already exists with that email"));
    }

    @Test
    public void testPasswordMatch() throws Exception {
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ADDRESS);

        // Fill in the prepatient form with different password
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gg.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@name='streetAddress1']")).sendKeys("testAddr1");
        driver.findElement(By.xpath("//input[@name='streetAddress2']")).sendKeys("testAddr2");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("5005005000");
        driver.findElement(By.xpath("//input[@name='icName']")).sendKeys("testicName");
        driver.findElement(By.xpath("//input[@name='icAddress1']")).sendKeys("testicAddr1");
        driver.findElement(By.xpath("//input[@name='icAddress2']")).sendKeys("testicAddr2");
        driver.findElement(By.xpath("//input[@name='icPhone']")).sendKeys("6006006000");
        driver.findElement(By.xpath("//input[@name='height']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@name='weight']")).sendKeys("321");
        driver.findElement(By.id("smoker1Choice")).click();
        driver.findElement(By.id("preregistSubmit")).click();
        // Password Match test
        assertTrue(driver.getPageSource().contains("Passwords do not match. Please try again."));
    }

    @Test
    public void testResetPassword() throws Exception {
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ADDRESS);

        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gg.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='streetAddress1']")).sendKeys("testAddr1");
        driver.findElement(By.xpath("//input[@name='streetAddress2']")).sendKeys("testAddr2");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("5005005000");
        driver.findElement(By.xpath("//input[@name='icName']")).sendKeys("testicName");
        driver.findElement(By.xpath("//input[@name='icAddress1']")).sendKeys("testicAddr1");
        driver.findElement(By.xpath("//input[@name='icAddress2']")).sendKeys("testicAddr2");
        driver.findElement(By.xpath("//input[@name='icPhone']")).sendKeys("6006006000");
        driver.findElement(By.xpath("//input[@name='height']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@name='weight']")).sendKeys("321");
        driver.findElement(By.id("smoker1Choice")).click();
        driver.findElement(By.id("preregistSubmit")).click();
        assertTrue(driver.getPageSource().contains("You have pre-registered."));

        driver = login("413", "1234");

        // Wait until redirected to page
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.titleIs("iTrust - Pre PatientHome"));

        // Verify Logging
        assertLogged(TransactionType.LOGIN_SUCCESS, 413L, 413L, "");

        //User goes to change password
        List<WebElement> links = driver.findElements(By.tagName("a"));

        int count = 0;

        for(int i = 0; i < links.size(); i++) {
            if(links.get(i).getAttribute("href").contains("changePassword"))
            {
                count = i;
                break;
            }
        }

        links.get(count).click();

        driver.findElement(By.xpath("//input[@name='oldPass']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='newPass']")).sendKeys("testpw1234");
        driver.findElement(By.xpath("//input[@name='confirmPass']")).sendKeys("testpw1234");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        assertTrue(driver.getPageSource().contains("Password Changed."));
    }

    @Test
    public void testOptionalFieldsFills() throws Exception {

    }
}
