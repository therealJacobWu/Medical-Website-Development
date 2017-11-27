package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.ViewCauseOfDeathStatisticsAction;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewCauseOfDeathStatisticsActionTest extends TestCase{
    private Long hcpid = 9000000003L;
    private Long emptyHcpid = 9000000000L;
    private Date year1970 = new Date(1000L);
    private Date year2010 = new Date(1262307661000L);
    private ViewCauseOfDeathStatisticsAction action = new ViewCauseOfDeathStatisticsAction(TestDAOFactory.getTestInstance());
    private TestDataGenerator gen = new TestDataGenerator();

    @Override
    protected void setUp() throws Exception {
        gen.clearAllTables();
        gen.icd9cmCodes();
        gen.patient2();
        gen.patient1000s();
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public void testValidQueryAll() throws Exception {
        List<String> causes = action.getCauseOfDeathStatistics(formatDate(year1970), formatDate(year2010), hcpid, "ALL");
        assertEquals(2, causes.size());
        assertTrue(causes.contains("Diabetes with ketoacidosis"));
        assertTrue(causes.contains("Coxsackie"));
    }

    public void testValidQueryMale() throws Exception {
        List<String> causes = action.getCauseOfDeathStatistics(formatDate(year1970), formatDate(year2010), hcpid, "Male");
        assertEquals(2, causes.size());
        assertTrue(causes.contains("Diabetes with ketoacidosis"));
        assertTrue(causes.contains("Coxsackie"));
    }

    public void testInvalidQueryGender() throws Exception {
        List<String> causes = action.getCauseOfDeathStatistics(formatDate(year1970), formatDate(year2010), hcpid, "NotSpecified");
        assertEquals(0, causes.size());
    }

    public void testInvalidQueryStartDate() throws Exception {
        try {
            action.getCauseOfDeathStatistics("somethingInvalid", formatDate(year2010), hcpid, "NotSpecified");
            fail("Exception not thrown due to validation failure");
        } catch (FormValidationException e) {
            assertTrue("Exception should be thrown", true);
        }
    }

    public void testInvalidQueryEndDate() throws Exception {
        try {
            action.getCauseOfDeathStatistics(formatDate(year1970), "somethingInvalid", hcpid, "NotSpecified");
            fail("Exception not thrown due to validation failure");
        } catch (FormValidationException e) {
            assertTrue("Exception should be thrown", true);
        }
    }

    public void testValidQueryEmptyHcpid() throws Exception {
        List<String> causes = action.getCauseOfDeathStatistics(formatDate(year1970), formatDate(year2010), emptyHcpid, "NotSpecified");
        assertEquals(0, causes.size());
    }
}
