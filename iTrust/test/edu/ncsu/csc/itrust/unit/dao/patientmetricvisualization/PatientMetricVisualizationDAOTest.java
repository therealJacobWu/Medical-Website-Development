package edu.ncsu.csc.itrust.unit.dao.patientmetricvisualization;

import edu.ncsu.csc.itrust.dao.mysql.PatientMetricVisualizationDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * Tests {@link PatientMetricVisualizationDAO}
 */
public class PatientMetricVisualizationDAOTest extends TestCase {
    private TestDataGenerator gen = new TestDataGenerator();
    private PatientMetricVisualizationDAO pmvDAO=
            TestDAOFactory.getTestInstance().getPatientMetricVisualizationDAO();

    @Override
	protected void setUp() throws Exception {
        gen.clearAllTables();
        gen.standardData();
    }

    @Test
    public void testGetAllPatientWithAttr() throws Exception{
        assertEquals(8, pmvDAO.getAllPatientWithAttr("age", "22").size());
        assertEquals(0, pmvDAO.getAllPatientWithAttr("number of visits", "9999").size());
        assertEquals(1, pmvDAO.getAllPatientWithAttr("CauseOfDeath", "Diabetes with ketoacidosis").size());
        assertEquals(24, pmvDAO.getAllPatientWithAttr("icState", "AK").size());
        assertEquals(57, pmvDAO.getAllPatientWithAttr("Religion", "None").size());
    }

    @Test
    public void testGetAllPatientAges() throws Exception {
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("4" ,  1);
        actual.put("6" ,  1);
        actual.put("7" ,  2);
        actual.put("8" ,  2);
        actual.put("9" ,  3);
        actual.put("11" , 1);
        actual.put("15" , 1);
        actual.put("22" , 5);
        actual.put("24" , 4);
        actual.put("27" , 1);
        actual.put("28" , 1);
        actual.put("29" , 1);
        actual.put("31" , 1);
        actual.put("32" , 5);
        actual.put("33" , 1);
        actual.put("37" , 4);
        actual.put("40" , 1);
        actual.put("41" , 4);
        actual.put("42" , 2);
        actual.put("62" , 1);
        actual.put("67" , 8);

        Map<String, Integer> res = pmvDAO.getAllPatientsAges();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsVisits() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("1",10);
        actual.put("2", 3);
        actual.put("3", 6);
        actual.put("4", 2);
        actual.put("5", 3);
        actual.put("7", 1);
        actual.put("10",1);
        actual.put("12",2);
        actual.put("13",1);
        actual.put("17",1);
        actual.put("24",1);
        actual.put("28",1);
        actual.put("32",1);
        Map<String, Integer> res = pmvDAO.getAllPatientsVisits();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsDeath() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("Diabetes with ketoacidosis",1);
        Map<String, Integer> res = pmvDAO.getAllPatientsDeath();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsStates() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("AK",24);
        actual.put("GA",1);
        actual.put("NC",17);
        actual.put("PA",15);
        Map<String, Integer> res = pmvDAO.getAllPatientsStates();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsBlood() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("A+",2);
        actual.put("A-",1);
        actual.put("AB+",23);
        actual.put("B+",2);
        actual.put("B-",1);
        actual.put("O+",2);
        actual.put("O-",8);
        Map<String, Integer> res = pmvDAO.getAllPatientsBlood();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsReligion() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("None",57);
        Map<String, Integer> res = pmvDAO.getAllPatientsReligion();
        assertEquals(actual,res);
    }

    @Test
    public void testGetAllPatientsLanguage() throws Exception{
        Map<String, Integer> actual = new TreeMap<>();
        actual.put("None",57);
        Map<String, Integer> res = pmvDAO.getAllPatientsLanguage();
        assertEquals(actual,res);
    }
}

