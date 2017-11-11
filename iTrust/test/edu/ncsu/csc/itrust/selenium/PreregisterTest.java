package edu.ncsu.csc.itrust.selenium;

import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
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

    private DAOFactory factory = TestDAOFactory.getTestInstance();
    public static final String ADDRESS = "http://localhost:8080/iTrust/";

    @Override
    protected void setUp() throws Exception {
        DBBuilder testRebuild = new DBBuilder();
        testRebuild.main(null);
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    /*
     * Set up a Preregister instance.
     */
    protected WebDriver setUp2() {
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
        return driver;
    }

    @Test
    public void testDuplicateEmail() throws  Exception {
        WebDriver driver = setUp2();

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
        WebDriver driver = setUp2();

        driver = login("411", "1234");

        // Wait until redirected to page
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.titleIs("iTrust - Pre PatientHome"));

        // Verify Logging
        assertLogged(TransactionType.LOGIN_SUCCESS, 411L, 411L, "");

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
    public void testOptionalFieldsFill() throws Exception {
        WebDriver driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ADDRESS);

        // Fill in the prepatient form with only required fields.
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("testFirstName");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("testLastName");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gg.com");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("1234");
        driver.findElement(By.id("preregistSubmit")).click();
        assertTrue(driver.getPageSource().contains("You have pre-registered."));

        PatientDAO testPatientDao = new PatientDAO(factory);
        PatientBean testPatientBean = testPatientDao.getPatient(411L);
        assertEquals(testPatientBean.getMID(), 411L);
        assertEquals(testPatientBean.getFirstName(), "testFirstName");
        assertEquals(testPatientBean.getLastName(), "testLastName");
        assertEquals(testPatientBean.getEmail(), "test@gg.com");
        assertEquals(testPatientBean.getStreetAddress1(), "");
        assertEquals(testPatientBean.getStreetAddress2(), "");
        assertEquals(testPatientBean.getPhone(), "");
        assertEquals(testPatientBean.getIcName(), "");
        assertEquals(testPatientBean.getIcAddress1(), "");
        assertEquals(testPatientBean.getStreetAddress2(), "");
        assertEquals(testPatientBean.getIcPhone(), "");

        HealthRecordsDAO testHealthRecordDao = new HealthRecordsDAO(factory);
        HealthRecord testHealthRecod = testHealthRecordDao.getAllHealthRecords(411).get(0);
        assertEquals(testHealthRecod.getHeight(), 0.0);
        assertEquals(testHealthRecod.getWeight(), 0.0);
        assertEquals(testHealthRecod.getSmokingStatus(), 9);
    }
}
