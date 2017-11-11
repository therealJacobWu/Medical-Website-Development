package edu.ncsu.csc.itrust.unit.action;

import edu.ncsu.csc.itrust.action.AddPrepatientAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;
import org.junit.Test;

public class AddPrepatientActionTest extends TestCase {
    private DAOFactory factory = TestDAOFactory.getTestInstance();
    private TestDataGenerator gen;
    private AddPrepatientAction action;

    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        action = new AddPrepatientAction(factory);
    }

    @Test
    public void testAddPrepatient1() throws Exception {
        PatientBean actual = new PatientBean();
        actual.setFirstName("Joe");
        actual.setLastName("Bar");
        actual.setEmail("joe.bar@gmail.com");

        long mid = action.addPrepatient(actual);

        PatientBean expected = factory.getPatientDAO().getPatient(mid);

        assertEquals(mid, expected.getMID());
        assertEquals(actual.getFirstName(), expected.getFirstName());
        assertEquals(actual.getLastName(), expected.getLastName());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(Role.PRE_PATIENT, factory.getAuthDAO().getUserRole(mid));
    }
}
