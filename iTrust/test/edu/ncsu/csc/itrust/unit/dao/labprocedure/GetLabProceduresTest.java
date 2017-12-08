package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

import java.util.List;

public class GetLabProceduresTest extends TestCase {
    private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();
    private TestDataGenerator gen;

    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.labProcedures("labProcedures");
        gen.patient1("patient1");
    }

    public void testGetLabProcedures() throws Exception {
        List<LabProcedureBean> patient1 = lpDAO.getLabProcedures(9000000000L, 1);
        assertEquals(3, patient1.size());
        assertEquals("10763-1", patient1.get(0).getLoinc());
        assertEquals("10666-6", patient1.get(1).getLoinc());
        assertEquals("10640-1", patient1.get(2).getLoinc());
    }

    public void testFailGetLabProcedures() throws Exception {
        try {
            lpDAO.getLabProcedures(0, 0);
            fail("Exception should have been thrown");
        } catch (DBException e) {
            assertEquals("HCP id cannot be null", e.getExtendedMessage());
        }

        assertEquals(0, lpDAO.getLabProcedures(9000000000L, -1).size());
    }
}
