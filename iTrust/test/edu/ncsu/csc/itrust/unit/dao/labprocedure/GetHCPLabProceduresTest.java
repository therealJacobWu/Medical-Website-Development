package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetHCPLabProceduresTest extends TestCase {
    private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();
    private TestDataGenerator gen;

    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.loadSQLFile("labprocedures");
        gen.loadSQLFile("ov5");
        gen.loadSQLFile("labprocedures2");
    }

    public void testGetHCPLabProcedures() throws Exception {
        assertEquals(1, lpDAO.getHCPLabProcedures(9000000003L).size());
        assertEquals("13495-7", lpDAO.getHCPLabProcedures(9000000003L).get(0).getLoinc());
    }

    public void testFailGetHCPLabProcedures() throws Exception {
        try {
            lpDAO.getHCPLabProcedures(0);
            fail("Exception should have been thrown");
        } catch (DBException e) {
            assertEquals("HCP id cannot be null", e.getExtendedMessage());
        }
    }
}
