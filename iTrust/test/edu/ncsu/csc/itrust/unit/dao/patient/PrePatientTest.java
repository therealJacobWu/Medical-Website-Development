package edu.ncsu.csc.itrust.unit.dao.patient;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.List;

public class PrePatientTest extends TestCase{


    private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();
    private TestDataGenerator gen = new TestDataGenerator();

    @Override
    protected void setUp() throws Exception {
        gen.clearAllTables();
        gen.prepatient2000s();
    }

    public void testGetPrePatients() throws Exception {
        List<PatientBean> prepatients = patientDAO.getPrePatient();
        assertEquals("There should be three prepatients", 3, prepatients.size());
    }

    public void testActivatePrePatient() throws Exception {
        List<PatientBean> prepatientsBeforeActivate = patientDAO.getPrePatient();
        patientDAO.activatePrePatient(prepatientsBeforeActivate.get(0).getMID());
        List<PatientBean> prepatientsAfterActivate = patientDAO.getPrePatient();
        assertEquals("There should be one less prepatient", prepatientsBeforeActivate.size(), prepatientsAfterActivate.size() + 1);
    }

    public void testDeactivatePrePatient() throws Exception {
        List<PatientBean> prepatientsBeforeActivate = patientDAO.getPrePatient();
        List<PatientBean> patientsBeforeActivate = patientDAO.getAllPatients();
        patientDAO.deactivatePrePatient(prepatientsBeforeActivate.get(0).getMID());
        List<PatientBean> prepatientsAfterActivate = patientDAO.getPrePatient();
        List<PatientBean> patientsAfterActivate = patientDAO.getAllPatients();
        assertEquals("There should be one less prepatient", prepatientsBeforeActivate.size(), prepatientsAfterActivate.size() + 1);
        assertEquals("There should be no change in patient", patientsBeforeActivate.size(), patientsAfterActivate.size() + 1);
    }
}
