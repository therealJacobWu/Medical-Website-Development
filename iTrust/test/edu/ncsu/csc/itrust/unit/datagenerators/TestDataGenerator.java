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

	public void clearAllTables() throws SQLException, FileNotFoundException,
			IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/deleteFromAllTables.sql");
	}

	public void clearHospitalAssignments() throws FileNotFoundException,
			IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/hospitalAssignmentsReset.sql");
	}

	public void er4() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/er6.sql");
	}

	public void patient1000s() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1001.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1002.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1003.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1004.sql");
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1005.sql");
	}

	public void viewAccessLogTestData() throws SQLException,
			FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR
				+ "/referral_sort_testdata.sql");

		// create patients Dare Devil and Devils Advocate
		// Devils Advocate is Dare Devil's Personal Representative
		loadSQLFile("patient23");
		loadSQLFile("patient24");
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
		loadSQLFile("cptCodes");
		loadSQLFile("icd9cmCodes");
		loadSQLFile("ndCodes");
		loadSQLFile("ndCodes1");
		loadSQLFile("ndCodes2");
		loadSQLFile("ndCodes3");
		loadSQLFile("ndCodes4");
		loadSQLFile("ndcodes100"); // NEW
		loadSQLFile("drugInteractions4");
		loadSQLFile("ORCodes");
		loadSQLFile("hospitals0");
		loadSQLFile("hcp0");

		loadSQLFile("lt0");
		loadSQLFile("lt1");
		loadSQLFile("lt2");

		loadSQLFile("hcp3");
		loadSQLFile("hcp7");
		er4();
		loadSQLFile("pha1");
		loadSQLFile("patient1");
		loadSQLFile("patient2");
		loadSQLFile("patient3");
		loadSQLFile("patient4");
		loadSQLFile("patient5");
		loadSQLFile("patient6");
		loadSQLFile("patient7");
		loadSQLFile("patient8");
		loadSQLFile("patient9");
		loadSQLFile("patient10");
		
		//Added so that the black box test plans for Use Case 32 can be run immediately after running TestDataGenerator
		loadSQLFile("hcp1");
		loadSQLFile("hcp2");
		loadSQLFile("patient11");
		loadSQLFile("patient12");
		loadSQLFile("patient13");
		loadSQLFile("patient14");
		
		
		loadSQLFile("patient20");
		loadSQLFile("patient21");
		loadSQLFile("patient22");
		loadSQLFile("patient25");
		loadSQLFile("patient26"); // NEW
		loadSQLFile("patient42");
		loadSQLFile("patient100"); // NEW

		loadSQLFile("admin1");
		loadSQLFile("admin2");
		loadSQLFile("admin3");
		loadSQLFile("uap1");
		loadSQLFile("ov1");

		loadSQLFile("referrals");
		loadSQLFile("message");
		loadSQLFile("tester");
		loadSQLFile("fakeemail");
		loadSQLFile("reportRequests");
		loadSQLFile("loincs");
		loadSQLFile("labprocedures");
		loadSQLFile("appointmentType");
		loadSQLFile("appointment");

		loadSQLFile("transactionLog");
		loadSQLFile("transactionLog2");
		loadSQLFile("transactionLog3");
		loadSQLFile("transactionLog4");

		loadSQLFile("AdverseEventPres");

		loadSQLFile("hcp8");
		loadSQLFile("hcp9"); // NEW
		loadSQLFile("expertHospitals");
		viewAccessLogTestData();
		loadSQLFile("wardmanagementdata");

		loadSQLFile("uc51");
		loadSQLFile("uc52");
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
		loadSQLFile("hcp11");
		
		//Added for UC 86
		loadSQLFile("hcp12");
		
		loadSQLFile("patient27");
		loadSQLFile("patient28");
		loadSQLFile("patient29");
		loadSQLFile("patient30");
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
