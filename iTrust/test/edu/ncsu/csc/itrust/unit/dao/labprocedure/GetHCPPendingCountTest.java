package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GetHCPPendingCountTest extends TestCase {
    private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();
    private TestDataGenerator gen;

    @Override
    protected void setUp() throws Exception {
        gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.loadSQLFile("patient1");
        gen.loadSQLFile("labprocedures");
    }

    public void testGetHCPPendingCount() throws Exception {
        assertEquals(0, lpDAO.getHCPPendingCount(0));
        assertEquals(1, lpDAO.getHCPPendingCount(9000000000L));
    }
}
