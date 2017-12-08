package edu.ncsu.csc.itrust.unit.dao.patient;

import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GetCauseOfDeathTest extends TestCase{
    private Long hcpid = 9000000003L;
    private Long emptyHcpid = 9000000001L;
    private Date year1970 = new Date(1000L);
    private Date year2000 = new Date(946688461000L);
    private Date year2010 = new Date(1262307661000L);
    private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();
    private TestDataGenerator gen = new TestDataGenerator();

    @Override
    protected void setUp() throws Exception {
        gen.clearAllTables();
        gen.loadSQLFile("icd9cmCodes");
        gen.loadSQLFile("patientDeath");
        gen.patient1000s();
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public void testGetAllPatients() throws Exception {
        List<String> causes = patientDAO.getCauseOfDeath(hcpid, formatDate(year1970), formatDate(year2010));
        assertEquals(2, causes.size());
        assertEquals(0, causes.indexOf("Diabetes with ketoacidosis"));
        assertEquals(1, causes.indexOf("Coxsackie"));
    }

    public void testGetMalePatients() throws Exception {
        List<String> causes = patientDAO.getCauseOfDeath(Gender.Male, hcpid, formatDate(year1970), formatDate(year2010));
        assertEquals(2, causes.size());
        assertEquals(0, causes.indexOf("Diabetes with ketoacidosis"));
        assertEquals(1, causes.indexOf("Coxsackie"));
    }

    public void testGetFemalePatients() throws Exception {
        List<String> causes = patientDAO.getCauseOfDeath(Gender.Female, hcpid, formatDate(year1970), formatDate(year2010));
        assertEquals(2, causes.size());
        assertEquals(1, causes.indexOf("Human Immunodeficiency Virus"));
        assertEquals(0, causes.indexOf("Age-Related Macular Degeneration"));
    }

    public void testGetNonHcpidPatients() throws Exception {
        List<String> causes = patientDAO.getCauseOfDeath(Gender.NotSpecified, emptyHcpid, formatDate(year1970), formatDate(year2010));
        assertEquals(0, causes.size());
    }

    public void testGetSmallerRangePatients() throws Exception {
        List<String> causes = patientDAO.getCauseOfDeath(hcpid, formatDate(year2000), formatDate(year2010));
        assertEquals(2, causes.size());
        assertEquals(0, causes.indexOf("Diabetes with ketoacidosis"));
        assertEquals(1, causes.indexOf("Coxsackie"));
    }
}
