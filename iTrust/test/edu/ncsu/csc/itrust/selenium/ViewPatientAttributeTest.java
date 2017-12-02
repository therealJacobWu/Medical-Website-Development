package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewPatientAttributeTest extends iTrustSeleniumTest {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
    }

    @Test
    public void testClickingOnBarAndVerifyResults() throws Exception {
        WebDriver driver = login("9000000000", "pw");

        driver.findElement(By.linkText("Visualize Patient Metrics")).click();
        assertEquals("iTrust - Patient Visualization", driver.getTitle());

        driver.findElement(By.id("icState")).click();
        System.out.println(driver.findElement(By.id("icState")).isSelected());

        driver.findElement(By.id("view_visualize")).click();

        assertEquals("iTrust - Patient Visualization", driver.getTitle());

        assertNotNull(driver.findElement(By.id("charts")));
    }

    @Test
    public void testViewPatientAttribute() throws Exception {
        WebDriver driver = login("9000000000", "pw");
        driver.get("http://localhost:8080/iTrust/auth/hcp/viewPatientAttribute.jsp?attribute=icState&value=AK");

        assertEquals("iTrust - View Patient Attributes", driver.getTitle());

        assertEquals(25, driver.findElements(By.tagName("tr")).size());
    }
}
