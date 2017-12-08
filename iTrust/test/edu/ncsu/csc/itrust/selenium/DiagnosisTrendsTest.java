package edu.ncsu.csc.itrust.selenium;


import edu.ncsu.csc.itrust.enums.TransactionType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Tests that you can add new office visits as a UAP and HCP
 */
public class DiagnosisTrendsTest extends iTrustSeleniumTest {

	/**
	 * set up standard test data
	 */
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();

		gen.loadSQLFile("patient_hcp_visits");
		gen.loadSQLFile("hcp_diagnosis_data");
	}
	
	public void testChartExists() throws Exception {
		//login PHA
		WebDriver driver = navigateToTrends();
        new Select(driver.findElement(By.name("icdCode"))).selectByValue("487.00");
        driver.findElement(By.name("zipCode")).sendKeys("27607");
        driver.findElement(By.name("endDate")).sendKeys("09/18/2011");
        driver.findElement(By.id("select_diagnosis")).click();

        // check that chart exists
		driver.findElement(By.id("container1"));
	}

	public void testValidation() throws Exception {
		WebDriver driver = navigateToTrends();

		new Select(driver.findElement(By.name("icdCode"))).selectByValue("487.00");
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("endDate")).sendKeys("09/18/");
		driver.findElement(By.id("select_diagnosis")).click();

		// check that we get a validation error
        driver.findElement(By.xpath("//*[contains(text(), 'Information not valid')]"));
	}

	private WebDriver navigateToTrends() throws Exception {
		//login PHA
		WebDriver driver = new HtmlUnitDriver();
		driver = login("7000000001", "pw");

		//make sure we are on the home page for PHA's
		assertEquals("iTrust - PHA Home",driver.getTitle());

		//select to view diagnosis trends
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertEquals("iTrust - View Diagnosis Statistics",driver.getTitle());

		//select "Trends"
		new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
		driver.findElement(By.id("select_View")).click();
		return driver;
	}
}
