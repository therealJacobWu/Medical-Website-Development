package edu.ncsu.csc.itrust.unit.datagenerators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * This TestDataGenerator class is in charge of centralizing all of the test
 * data calls. Most of the SQL is in the sql/something.sql files. A few design
 * conventions:
 * 
 * <ul>
 * <li>Any time you're using this class, be sure to run the "clearAllTables"
 * first. This is not a very slow method (it's actually quite fast) but it
 * clears all of the tables so that no data from a previous test can affect your
 * current test.</li>
 * <li>We do not recommend having one test method call another test method
 * (except "standardData" or other intentionally "meta" methods). For example,
 * loincs() should not call patient1() first. Instead, put BOTH patient1() and
 * loincs() in your test case. If we keep this convention, then every time you
 * call a method, you know that ONLY your sql file is called and nothing else.
 * The alternative is a lot of unexpected, extraneous calls to some test methods
 * like patient1().</li>
 * </ul>
 * 
 * 
 * 
 */
public class TestDataGenerator {
	public static void main(String[] args) throws IOException, SQLException {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		// Put it here so that actual iTrust runs can see cause of death data of patients
		gen.patient1000s();
	}

	private String DIR = "sql/data";

	private DAOFactory factory;

	public TestDataGenerator() {
		this.factory = TestDAOFactory.getTestInstance();
	}

	public TestDataGenerator(String projectHome, DAOFactory factory) {
		this.DIR = projectHome + "/sql/data";
		this.factory = factory;
	}

	public void additionalOfficeVisits() throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovAdditional.sql");
	}

	public void loadSQLFile(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/" + a + ".sql");
	}

//	public void loadSQLFile(String a) throws SQLException, FileNotFoundException, IOException {
//		new DBBuilder(factory).executeSQLFile(DIR + "/pha0.sql");
//	}

	public void admin2(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin2.sql");
	}

	public void aliveRecurringPatients(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/aliveRecurringPatients.sql");
	}

	public void appointmentCase1(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentCase1.sql");
	}

	public void appointmentCase2(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentCase2.sql");
	}

	public void clearAllTables() throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/deleteFromAllTables.sql");
	}

	public void clearAppointments(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearAppointments.sql");
	}

	public void clearFakeEmail(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearFakeemail.sql");
	}

	public void clearMessages(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearMessages.sql");
	}

	public void clearHospitalAssignments() throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/hospitalAssignmentsReset.sql");
	}

	public void deadRecurringPatients(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/deadRecurringPatients.sql");
	}

	public void diagnosedPatient_OldAndNewVisit(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/diagnosedPatient_OldAndNewVisit.sql");
	}

	public void foreignKeyTest(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/foreignKeyTest.sql");
	}

	public void clearLoginFailures(String a) throws FileNotFoundException,
			SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loginFailures.sql");
	}

	public void clearTransactionLog(String a) throws FileNotFoundException,
			SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearTransactionLog.sql");
	}

	public void cptCodes(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/cptCodes.sql");
	}

	public void ovMed(String a) throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovMed.sql");
	}

	public void ovReactionOverride(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovReactionOverrides.sql");
	}

	public void ovImmune() throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovImmune.sql");
	}

	public void drugInteractions(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions.sql");
	}

	public void drugInteractions2(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions2.sql");
	}

	public void drugInteractions3(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions3.sql");
	}

	public void drugInteractions4(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/drugInteractions4.sql");
	}

	public void fakeEmail(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/fakeemail.sql");
	}

	public void family(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/family.sql");
	}

	public void er4() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/er6.sql");
	}

	public void hcp0(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp0.sql");
	}

	public void hcp1(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp1.sql");
	}

	public void hcp2(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp2.sql");
	}

	public void hcp3(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp3.sql");
	}

	public void hcp4(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp4.sql");
	}

	public void hcp5(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp5.sql");
	}

	public void hcp7(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp7.sql");
	}

	/**
	 * Adds HCP Curious George for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param sqlFile
	 */
	public void hcp8(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp8.sql");
	}

	/**
	 * Adds HCP John Zoidberg for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param sqlFile
	 */
	public void hcp9(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp9.sql");
	} // NEW

	public void hcp10(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp10.sql");
	}

	/**
	 * Adds HCP Brooke Tran with Specialty Optometrist.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param sqlFile
	 */
	public void hcp11(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp11.sql");
	}
	
	/**
	 * Adds HCP Lamar Bridges with Specialty Ophthalmologist.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param sqlFile
	 */
	public void hcp12(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp12.sql");
	}
	
	public void healthData(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/healthdata.sql");
	}

	public void hospitals(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitals0.sql");
	}

	public void hospitals1(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitals1.sql");
	}

	public void icd9cmCodes(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/icd9cmCodes.sql");
	}

	public void labProcedures(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/labprocedures.sql");
	}

	public void labProcedures2(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/labprocedures2.sql");
	}

	public void loincs(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loincs.sql");
	}

	public void messages(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/message.sql");
	}

	public void messages6(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/messageCase6.sql");
	}

	public void ndCodes(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes.sql");
	}

	public void ORCodes(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ORCodes.sql");
	}

	public void ndCodes1(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes1.sql");
	}

	public void ndCodes2(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes2.sql");
	}

	public void ndCodes3(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes3.sql");
	}

	public void ndCodes4(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes4.sql");
	}

	/**
	 * Adds drugs Midichlominene and Midichlomaxene for UC10 and UC37 testing
	 * purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void ndCodes100(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndcodes100.sql");
	} // NEW

	public void officeVisit0Multiple() throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov0Multiple.sql");
	}
	
	/**
	 * Set up the outcome of Acceptance Scenario 1 of UC83, which results in the creation of an Ophthalmology office visit for Brody Franco.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param a
	 */
	public void ophthalmologyScenario1(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ophthalmologyScenario1.sql");
	}
	
	/**
	 * Set up the outcome of Acceptance Scenario 2 of UC83, which results in the creation of an Ophthalmology office visit for Freya Chandler.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param a
	 */
	public void ophthalmologyScenario2(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ophthalmologyScenario2.sql");
	}
	
	/**
	 * Set up the preconditions for Acceptance Scenario 3 of UC84, which results in the creation of an Ophthalmology office visit for dependent Brittany Franco.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param a
	 */
	public void ophthalmologyScenario3(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ophthalmologyScenario3.sql");
	}
	
	/**
	 * Set up the preconditions for Acceptance Scenario 2 of UC88, which results in the creation of an Ophthalmology Office Visit Request for Brody Franco.
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param a
	 */
	public void ophthalmologyScenario4(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ophthalmologyScenario4.sql");
	}
	
	public void officeVisit1(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov1.sql");
	}

	public void officeVisit2(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov2.sql");
	}

	public void officeVisit3(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov3.sql");
	}

	public void officeVisit4(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov4.sql");
	}

	public void officeVisit5(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov5.sql");
	}

	public void officeVisit6(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov6.sql");
	}

	public void officeVisit7(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov7.sql");
	}

	public void officeVisit8(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ov8.sql");
	}

	public void officeVisits(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/officeVisits.sql");
	}

	public void operationalProfile(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/operationalProfile.sql");
	}

	public void pre_patient1(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/pre_patient1.sql");
	}

	public void patient1(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1.sql");
	}

	public void clearProfilePhotos() throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearphotos.sql");
	}

	public void patient2(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient2.sql");
	}

	public void patientDeath(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patientDeath.sql");
	}

	public void patient3(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient3.sql");
	}

	public void patient4(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient4.sql");
	}

	public void patient5(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient5.sql");
	}

	public void patient6(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient6.sql");
	}

	public void patient7(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient7.sql");
	}

	public void patient8(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient8.sql");
	}

	public void patient9(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient9.sql");
	}

	public void patient10(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient10.sql");
	}

	public void patient11(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient11.sql");
	}

	public void patient12(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient12.sql");
	}

	public void patient13(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient13.sql");
	}

	public void patient14(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient14.sql");
	}

	public void patient15() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient15.sql");
	}

	public void patientDeactivate(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patientDeactivate.sql");
	}

	public void UC32Acceptance(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/UC32Acceptance.sql");
	}

	public void patient20(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient20.sql");
	}

	public void patient21(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient21.sql");
	}

	public void patient22(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient22.sql");
	}

	public void patient1000s() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1001.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1002.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1003.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1004.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1005.sql");
	}

	/**
	 * Adds patient Dare Devil for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient23(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient23.sql");
	}

	/**
	 * Adds patient Devils Advocate for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient24(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient24.sql");
	}

	/**
	 * Adds patient Trend Setter for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient25(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient25.sql");
	}

	/**
	 * Adds patient Philip Fry for testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient26(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient26.sql");
	} // NEW
	
	/**
	 * Adds patient Brody Franco, used in the testing of UC83.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient27(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient27.sql");
	} 
	
	/**
	 * Adds patient Freya Chandler, used in the testing of UC83.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient28(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient28.sql");
	} 
	
	/**
	 * Adds patient Brittany Franco, used in the testing of UC84.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient29(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient29.sql");
	} 
	
	/**
	 * Adds patient James Franco, used in the testing of UC84.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient30(String a) throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient30.sql");
	} 

	public void patient42(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient42.sql");
	}

	/**
	 * Adds patient Anakin Skywalker for UC10 and UC37 testing purposes.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @param a
	 */
	public void patient100(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient100.sql");
	} // NEW

	public void patientLabProc(String a) throws FileNotFoundException,
			SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patientLabProc.sql");
	}

	public void pendingAppointmentAlert(String a) throws FileNotFoundException,
			SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/pendingAppointmentAlert.sql");
	}

	public void pendingAppointmentConflict(String a) throws FileNotFoundException,
			SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/pendingAppointmentConflict.sql");
	}

	public void reportRequests(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/reportRequests.sql");
	}

	public void surveyResults(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/surveyResults.sql");
	}

	public void tester(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/tester.sql");
	}

	public void timeout(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/timeout.sql");
	}

	public void transactionLog(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog.sql");
	}

	public void transactionLog2(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog2.sql");
	}

	public void transactionLog3(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog3.sql");
	}

	public void transactionLog4(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog4.sql");
	}

	public void transactionLog5(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog5.sql");
	}

	public void transactionLog6(String a) throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog6.sql");
	}

	public void uap1(String a) throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uap1.sql");
	}

	public void patient_hcp_vists(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient_hcp_visits.sql");
	}

	public void hcp_diagnosis_data(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp_diagnosis_data.sql");
	}

	public void immunization_data() throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/immunization.sql");
	}

	public void remoteMonitoring1(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring1.sql");
	}

	public void remoteMonitoring2(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring2.sql");
	}

	public void remoteMonitoring3(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring3.sql");
	}

	public void remoteMonitoring4(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring4.sql");
	}

	public void remoteMonitoring5(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring5.sql");
	}

	public void remoteMonitoring6(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring6.sql");
	}

	public void remoteMonitoring7(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring7.sql");
	}

	public void remoteMonitoring8(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoring8.sql");
	}

	public void remoteMonitoringUAP(String a) throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/remoteMonitoringUAP.sql");
	}

	public void remoteMonitoringAdditional() throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/remoteMonitoringAdditional.sql");
	}

	public void remoteMonitoringPresentation() throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/remoteMonitoringPresentation.sql");
	}

	public void adverseEvent1(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent1.sql");
	}

	public void adverseEvent2(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent2.sql");
	}

	public void adverseEvent3(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/adverseEvent3.sql");
	}

	public void adverseEventPresentation() throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/adverseEventPresentation.sql");
	}

	public void pha1(String a) throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/pha1.sql");
	}

	public void adverseEventPres(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/AdverseEventPres.sql");
	}

	public void appointment(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointment.sql");
	}

	public void appointmentCase3(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentCase3.sql");
	}

	public void appointmentType(String a) throws FileNotFoundException, IOException,
			SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/appointmentType.sql");
	}

	public void admin3(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin3.sql");
	}

	public void uc44_acceptance_scenario_2(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/uc44_acceptance_scenario_2.sql");
	}

	public void uc44_acceptance_scenario_3(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/uc44_acceptance_scenario_3.sql");
	}

	public void uc44_acceptance_scenario_5(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/uc44_acceptance_scenario_5.sql");
	}

	public void lt0(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt0.sql");
	}

	public void lt1(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt1.sql");
	}

	public void lt2(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/lt2.sql");
	}

	public void referrals(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/referrals.sql");
	}

	public void referral_query_testdata(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/referral_query_testdata.sql");
	}

	public void referral_sort_testdata(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/referral_sort_testdata.sql");
	}

	public void malariaEpidemic(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/malariaEpidemic.sql");
	}

	public void influenzaEpidemic(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/influenzaEpidemic.sql");
	}

	public void UC22(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/UC22.sql");
	}

	public void testExpertSearch() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/testExpertSearch.sql");
	}

	public void apptRequestConflicts(String a) throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory)
				.executeSQLFile(DIR + "/apptRequestConflicts.sql");
	}

	public void expertHospitals(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/expertHospitals.sql");
	}

	public void viewAccessLogTestData() throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/referral_sort_testdata.sql");

		// create patients Dare Devil and Devils Advocate
		// Devils Advocate is Dare Devil's Personal Representative
		patient23("patient23");
		patient24("patient24");
	}

	/**
	 * Adds additional DLHCPs to certain patients.
	 * 
	 * MID DLHCPs --- ------ 1 9000000000, 9000000003
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param a
	 */
	public void messagingCcs(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/messagingCcs.sql");
	}

	public void wardmanagementdata(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/wardmanagementdata.sql");

	}

	/**
	 * Generate test data for uc51 acceptance scenarios
	 * @param a
	 */
	public void uc51(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc51.sql");
	}

	/**
	 * Generate test data for uc52 acceptance scenarios
	 * @param a
	 */
	public void uc52(String a) throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc52.sql");
	}

	/**
	 * Generate test data for cdc health statistics
	 * @param a
	 */
	public void cdcStatistics(String a) throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/cdcStatistics.sql");
	}

	public void standardData() throws FileNotFoundException, IOException,
			SQLException {
		cptCodes("cptCodes");
		icd9cmCodes("icd9cmCodes");
		ndCodes("ndCodes");
		ndCodes1("ndCodes1");
		ndCodes2("ndCodes2");
		ndCodes3("ndCodes3");
		ndCodes4("ndCodes4");
		ndCodes100("ndCodes100"); // NEW
		drugInteractions4("drugInteractions4");
		ORCodes("ORCodes'");
		hospitals("hospitals");
		hcp0("hcp0");

		lt0("lt0");
		lt1("lt1");
		lt2("lt2");

		hcp3("hcp3");
		hcp7("hcp7");
		er4();
		pha1("pha1");
		patient1("patient1");
		patient2("patient2");
		patient3("patient3");
		patient4("patient4");
		patient5("patient5");
		patient6("patient6");
		patient7("patient7");
		patient8("patient8");
		patient9("patient9");
		patient10("patient10");
		
		//Added so that the black box test plans for Use Case 32 can be run immediately after running TestDataGenerator
		hcp1("hcp1");
		hcp2("hcp2");
		patient11("patient11");
		patient12("patient12");
		patient13("patient13");
		patient14("patient14");
		
		
		patient20("patient20");
		patient21("patient21");
		patient22("patient22");
		patient25("patient25");
		patient26("patient26"); // NEW
		patient42("patient42");
		patient100("patient100"); // NEW

		loadSQLFile("admin1");
		admin2("admin2");
		admin3("admin3");
		uap1("uap1");
		officeVisit1("ov1");

		referrals("referrals");
		messages("messages");
		tester("tester");
		fakeEmail("fakeEmail");
		reportRequests("reportRequests");
		loincs("loincs");
		labProcedures("labProcedures");
		appointmentType("appointmentType");
		appointment("appointment");

		transactionLog("transactionLog");
		transactionLog2("transactionLog2");
		transactionLog3("transactionLog3");
		transactionLog4("transactionLog4");

		adverseEventPres("adverseEventPres");

		hcp8("hcp8");
		hcp9("hcp9"); // NEW
		expertHospitals("expertHospitals");
		viewAccessLogTestData();
		wardmanagementdata("wardmanagementdata");

		uc51("uc51");
		uc52("uc52");
		cdcStatistics("cdcStatistics");
		uc53SetUp();

		uc63(); // NEW
		uc55();
		uc56();
		if (!checkIfZipsExists()) {
			zipCodes();
		}
		uc68(); // NEW Spring 15
		uc70(); // NEW Spring 15
		uc71(); // NEW Spring 15

		designatedNutritionist(); // NEW Spring 15

		uc78(); // NEW Spring 15
		uc81(); // NEW Spring 15
		
		//Added for UC83
		hcp11("hcp11");
		
		//Added for UC 86
		hcp12("hcp12");
		
		patient27("patient27");
		patient28("patient28");
		patient29("patient29");
		patient30("patient30");
	}

	public void uc47SetUp() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc47SetUp.sql");
	}

	public void zipCodes() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/zipCodes.sql");
	}

	public void uc47TearDown() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc47TearDown.sql");
	}

	public void uc53SetUp() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc53.sql");
	}

	public void uc55() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc55.sql");
	}

	/**
	 * 
	 * Generate records release data for uc56 acceptance scenarios Includes
	 * recordsrelease table data and UAP-HCP relations
	 */
	public void uc56() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc56.sql");
	}

	/**
	 * Generate dependency data for uc58 acceptance. Create a dependent user, a
	 * representative user, and establish representative relationship between
	 * the two.
	 */
	public void uc58() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc58.sql");
	}

	/**
	 * Generate dependency data for uc59 acceptance. Create a dependent user and
	 * a representative user
	 */
	public void uc59() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc59.sql");
	}

	/**
	 * Generate dependency data for uc60 acceptance. Create a Patient and a HCP
	 * to bill the patient. Create a billed office visit the patient can view.
	 */
	public void uc60() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc60.sql");
	}

	/**
	 * Generate dependency data for uc63 acceptance. Create two HCPs who are
	 * OB/GYNs Create seven new patients Give one patient a past pregnancy
	 */
	public void uc63() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc63.sql");
	}

	/**
	 * Generate dependency data for uc68 and uc69 acceptance. Create 3 patients
	 * and one hcp nutrionist. Patient Derek Morgan has no prior food diary
	 * entries. Patient Jennifer Jareau has 2 prior food diary entries. Patient
	 * Aaron Hotchner as 2 prior food diary entries. HCP Spencer Reid is a
	 * nutrition specialist. Give 2 of the 3 patients food diary information
	 */
	public void uc68() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc68.sql");
	}

	/**
	 * Inserts the user Emily Prentiss into the system and gives her one food
	 * diary entry.
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc71() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc71.sql");
	}

	/**
	 * Inserts the user Emily Prentiss into the system and gives her one food
	 * diary entry.
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc70() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc70.sql");
	}

	/**
	 * Generate dependency data for uc63 acceptance. NOTE: also executes the
	 * above method because the patients created above are also required for
	 * this use case. Give three patients initial obstetrics records.
	 */
	public void uc64() throws SQLException, FileNotFoundException, IOException {
		uc63();
		new DBBuilder(factory).executeSQLFile(DIR + "/uc64.sql");
	}

	/**
	 * Generate a list of reviews for 2 HCP's by 3 diff patients.
	 */
	public void uc61reviews() throws FileNotFoundException, SQLException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/reviews.sql");
	}

	/**
	 * generate the dependency of Baby Programmer on Andy Programmer
	 */
	public void doBaby() throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/programmerReps.sql");
	}

	/**
	 * generate the dependency of Baby Programmer on Andy Programmer
	 */
	public void reviews() throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/reviews.sql");
	}

	/**
	 * Generates extra hcps with specialty of nutritionist
	 */
	public void designatedNutritionist() throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/designatedNutritionist.sql");
	}

	/**
	 * Inserts some food entries for Random Person into the system.
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc76() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc76.sql");
	}

	/**
	 * Inserts some sleep entries for Random Person into the system.
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc78() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc78.sql");
	}

	/**
	 * Inserts a fitness HCP (Duyu Ivanlyft) and some exercise entries for
	 * Random Person into the system.
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc81() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uc81.sql");
	}

	/**
	 * Inserts a optometristVisit into the system
	 * 
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void uc85() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ophthalmologydiagnosis.sql");
	}
	
	/**
	 * Do we have zipcodes?
	 * 
	 * @return Whether or not we have zipcodes.
	 * @throws SQLException
	 */
	private boolean checkIfZipsExists() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			ps = conn
					.prepareStatement("SELECT * FROM zipcodes WHERE zip='27614'");
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;

			}
		} catch (SQLException e) {
			return false;
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					return false;
				}
		}

		return false;
	}
	
	
}
