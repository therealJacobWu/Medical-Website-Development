package edu.ncsu.csc.itrust.selenium;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ActivatePrePatientTest extends iTrustSeleniumTest {
    private HtmlUnitDriver driver;
    private PatientDAO patientDao;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
        gen.prepatient2000s();
        patientDao = TestDAOFactory.getTestInstance().getPatientDAO();
    }

    public void testViewPrePatient() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");
        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
        driver.findElement(By.linkText("All Pre-registered Patients")).click();
        assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/viewPrePatient.jsp"));

        WebElement elem;

        for (PatientBean prePatient: patientDao.getPrePatient()) {
            elem = driver.findElement(By.id("MID_" + prePatient.getMID()));
            assertTrue(elem != null);
            elem = driver.findElement(By.id("ACT_" + prePatient.getMID()));
            assertTrue(elem != null);
            elem = driver.findElement(By.id("DEA_" + prePatient.getMID()));
            assertTrue(elem != null);
        }
    }

    public void testActivateDeativatePrePatient() throws Exception {

        driver = (HtmlUnitDriver)login("9000000003", "pw");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
        driver.findElement(By.linkText("All Pre-registered Patients")).click();
        assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/viewPrePatient.jsp"));

        WebElement elem = null;

        PatientBean activatedPrePatient = patientDao.getPrePatient().get(0);
        PatientBean deactivatedPrePatient = patientDao.getPrePatient().get(1);

        driver.findElement(By.id("ACT_" + activatedPrePatient.getMID())).click();
        driver.findElement(By.id("DEA_" + deactivatedPrePatient.getMID())).click();

        try {
            elem = driver.findElement(By.id("MID_" + activatedPrePatient.getMID()));
            fail("Element should not be there");
        } catch (NoSuchElementException d) {
            assertNull(elem);
        }

        try {
            elem = driver.findElement(By.id("MID_" + deactivatedPrePatient.getMID()));
            fail("Element should not be there");
        } catch (NoSuchElementException d) {
            assertNull(elem);
        }
    }

    public void testEditPrePatient() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("h2.panel-title")).click();
        driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
        driver.findElement(By.linkText("All Pre-registered Patients")).click();
        assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/viewPrePatient.jsp"));


        PatientBean beforePrePatient = patientDao.getPrePatient().get(0);

        driver.findElement(By.id("MID_" + beforePrePatient.getMID())).click();

        assertTrue(driver.getCurrentUrl().startsWith(ADDRESS + "auth/hcp-uap/editPHR.jsp"));
        driver.findElement(By.xpath("//input[@value='Edit']")).click();

        assertTrue(driver.getCurrentUrl().startsWith(ADDRESS + "auth/hcp-uap/editPatient.jsp"));

        driver.findElement(By.xpath("//input[@name='firstName']")).clear();
        driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("Some");
        driver.findElement(By.xpath("//input[@name='action']")).click();

        assertTrue(driver.getPageSource().contains("Information Successfully Updated"));

        PatientBean afterPrePatient = patientDao.getPrePatient().stream().filter(patientBean -> patientBean.getMID() == beforePrePatient.getMID()).collect(Collectors.toList()).get(0);

        assert(afterPrePatient.getFirstName().equals("Some"));
    }
}
