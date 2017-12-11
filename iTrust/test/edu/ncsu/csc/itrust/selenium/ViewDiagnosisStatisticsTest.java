package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test Diagnosis Trends / Epidemics page
 */
public class ViewDiagnosisStatisticsTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver;
	/**
	 * Sets up the test. Clears the tables then adds necessary data
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
		gen.loadSQLFile("patient_hcp_visits");
		gen.loadSQLFile("hcp_diagnosis_data");
	}

	/*
	 * Authenticate PHA
	 * MID 7000000001
	 * Password: pw
	 * Choose "Epidemics"
	 * Enter Fields:
	 * Diagnosis: 84.50 Malaria
	 * ZipCode: 12345
	 * StartDate: 1/23/12
	 * Threshold: [leave blank]
	 */
	/**
	 * testViewDiagnosisTrendsEpidemic_InvalidThreshold
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrendsEpidemic_InvalidThreshold()
			throws Exception {
		driver = (HtmlUnitDriver)login("7000000001", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("12345");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("01/23/2012");

		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 7000000001L, 0L,	"");
	}

	/**
	 * viewDiagnosisEpidemics_NoEpidemicRecords
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemicRecords() throws Exception {
		driver = (HtmlUnitDriver)login("9000000000", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("38201");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/02/2010");
		driver.findElement(By.name("threshold")).clear();
		driver.findElement(By.name("threshold")).sendKeys("110");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000000L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("There is no epidemic occurring in the region."));
	}
	
	/**
	 * viewDiagnosisEpidemics_YesEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_YesEpidemic() throws Exception {
		gen.loadSQLFile("influenzaEpidemic");
		
		driver = (HtmlUnitDriver)login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("11/02/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertFalse(driver.getPageSource().contains("THERE IS AN EPIDEMIC OCCURRING IN THIS REGION!"));
	}
	
	/**
	 * viewDiagnosisEpidemics_NoEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemic() throws Exception {
		gen.loadSQLFile("influenzaEpidemic");
		driver = (HtmlUnitDriver)login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/15/2010");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertTrue(driver.getPageSource().contains("There is no epidemic occurring in the region."));
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

	public void testTrendsValidation() throws Exception {
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