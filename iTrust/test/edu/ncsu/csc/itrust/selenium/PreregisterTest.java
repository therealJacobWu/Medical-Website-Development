package edu.ncsu.csc.itrust.selenium;

import edu.ncsu.csc.itrust.enums.TransactionType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PreregisterTest extends iTrustSeleniumTest{

    public static final String ADDRESS = "http://localhost:8080/iTrust/";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    public void testPreregister() throws Exception {
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ADDRESS);

        // Fill in the prepatient form
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test2@gg.com");
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

        // Sbumit form
        driver.findElement(By.id("preregistSubmit")).click();
        assertTrue(driver.getPageSource().contains("You have pre-registered."));
    }

    public void testPrepatientLogin() throws Exception {
        WebDriver driver = login("411", "1234");
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.titleIs("iTrust - Patient Home"));
        assertLogged(TransactionType.LOGIN_SUCCESS, 411L, 0L, "");
    }

}
