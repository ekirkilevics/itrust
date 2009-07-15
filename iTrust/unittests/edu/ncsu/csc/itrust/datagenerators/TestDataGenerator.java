package edu.ncsu.csc.itrust.datagenerators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class TestDataGenerator {
	public static void main(String[] args) throws IOException, SQLException {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
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

	public void additionalOfficeVisits() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ovAdditional.sql");
	}

	public void admin1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin1.sql");
	}

	public void admin2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/admin2.sql");
	}

	public void clearAllTables() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/deleteFromAllTables.sql");
	}

	public void clearFakeEmail() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearFakeemail.sql");
	}

	public void clearHospitalAssignments() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitalAssignmentsReset.sql");
	}
	public void foreignKeyTest() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/foreignKeyTest.sql");
	}

	public void clearLoginFailures() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loginFailures.sql");
	}

	public void clearTransactionLog() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/clearTransactionLog.sql");
	}

	public void cptCodes() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/cptCodes.sql");
	}

	public void epidemic() throws FileNotFoundException, SQLException, IOException {
		hospitals();
		hcp0();
		icd9cmCodes();
		new DBBuilder(factory).executeSQLFile(DIR + "/epidemic.sql");
	}

	public void epidemicInfluenza() throws FileNotFoundException, SQLException, IOException {
		hospitals();
		hcp0();
		icd9cmCodes();
		new DBBuilder(factory).executeSQLFile(DIR + "/epidemicInfluenza.sql");
	}

	public void epidemicMalaria() throws FileNotFoundException, SQLException, IOException {
		hospitals();
		hcp0();
		icd9cmCodes();
		new DBBuilder(factory).executeSQLFile(DIR + "/epidemicMalaria.sql");
	}

	public void fakeEmail() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/fakeemail.sql");
	}

	public void family() throws FileNotFoundException, SQLException, IOException {
		hospitals();
		hcp0();
		icd9cmCodes();
		new DBBuilder(factory).executeSQLFile(DIR + "/family.sql");
	}

	public void er4() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/er6.sql");
	}
	
	public void hcp0() throws SQLException, FileNotFoundException, IOException {
		hospitals();
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp0.sql");
	}
	
	public void hcp1() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp1.sql");
	}
	
	public void hcp2() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp2.sql");
	}

	public void hcp3() throws FileNotFoundException, SQLException, IOException {
		hospitals();
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp3.sql");
	}

	public void hospitals() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/hospitals0.sql");
	}

	public void icd9cmCodes() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/icd9cmCodes.sql");
	}

	public void labProcedures() throws SQLException, FileNotFoundException, IOException {
		patient1();
		patient2();
		patient3();
		patient4();
		//TODO StandardData loads this twice - take these four calls out and make it work
		new DBBuilder(factory).executeSQLFile(DIR + "/labprocedures.sql");
	}

	public void loincs() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/loincs.sql");
	}

	public void ndCodes() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/ndCodes.sql");
	}

	public void officeVisit1() throws SQLException, FileNotFoundException, IOException {
		hospitals();
		hcp0();
		new DBBuilder(factory).executeSQLFile(DIR + "/ov1.sql");
	}

	public void operationalProfile() throws FileNotFoundException, IOException, SQLException {
		patient1();
		patient2();
		hcp0();
		new DBBuilder(factory).executeSQLFile(DIR + "/operationalProfile.sql");
	}

	public void patient1() throws FileNotFoundException, IOException, SQLException {
		icd9cmCodes();
		ndCodes();
		hospitals();
		hcp0();
		hcp3();
		new DBBuilder(factory).executeSQLFile(DIR + "/patient1.sql");
	}

	public void patient2() throws FileNotFoundException, IOException, SQLException {
		
		cptCodes();
		patient1();
		new DBBuilder(factory).executeSQLFile(DIR + "/patient2.sql");
	}

	public void patient3() throws FileNotFoundException, IOException, SQLException {
		icd9cmCodes();
		new DBBuilder(factory).executeSQLFile(DIR + "/patient3.sql");
	}

	public void patient4() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient4.sql");
	}
	
	public void patient5() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient5.sql");
	}

	public void patient6() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient6.sql");
	}

	public void patient7() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient7.sql");
	}

	public void patient8() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient8.sql");
	}
	
	public void patient9() throws FileNotFoundException, SQLException, IOException {
		hcp0();
		hcp1();
		hcp2();
		new DBBuilder(factory).executeSQLFile(DIR + "/patient9.sql");
	}
	
	public void patient10() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient10.sql");
	}
	
	public void patient11() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient11.sql");
	}
	
	public void patient12() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient12.sql");
	}
	
	public void patient13() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient13.sql");
	}
	
	public void patient14() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient14.sql");
	}
	
	public void patient15() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient15.sql");
	}
	
	public void UC32Acceptance() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/UC32Acceptance.sql");
	}
	
	public void patient20() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient20.sql");
	}
	
	public void patient21() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient21.sql");
	}

	public void reportRequests() throws FileNotFoundException, SQLException, IOException {
		patient1();
		patient2();
		patient3();
		admin2();
		hcp0();
		new DBBuilder(factory).executeSQLFile(DIR + "/reportRequests.sql");
	}
	public void surveyResults() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/surveyResults.sql");
	}

	public void tester() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/tester.sql");
	}

	public void timeout() throws FileNotFoundException, SQLException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/timeout.sql");
	}

	public void transactionLog() throws FileNotFoundException, SQLException, IOException {
		hcp0();
		admin1();
		patient1();
		patient2();
		new DBBuilder(factory).executeSQLFile(DIR + "/transactionLog.sql");
	}

	public void uap1() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/uap1.sql");
	}
	
	public void patient_hcp_vists() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/patient_hcp_visits.sql");
	}
	
	public void hcp_diagnosis_data() throws FileNotFoundException, IOException, SQLException {
		hcp0();
		hcp1();
		hcp3();
		new DBBuilder(factory).executeSQLFile(DIR + "/hcp_diagnosis_data.sql");
	}
	
	public void immunization_data() throws FileNotFoundException, IOException, SQLException {
		new DBBuilder(factory).executeSQLFile(DIR + "/immunization.sql");
	}
	
	public void standardData() throws FileNotFoundException, IOException, SQLException {
		cptCodes();
		icd9cmCodes();
		ndCodes();
		hospitals();
		hcp0();
		
		hcp3();
		er4();
		patient1();
		patient2();
		patient3();
		patient4();
		patient5();
		patient6();
		patient7();
		patient8();

		patient20();
		patient21();
		admin1();
		admin2();
		uap1();
		officeVisit1();
		tester();
		fakeEmail();
		reportRequests();
		loincs();
		labProcedures();
		System.out.println("Operation completed.");
	}
	
	public void extraData() throws FileNotFoundException, IOException, SQLException {
		hcp1();
		hcp2();
		
		patient9();
		patient10();
		patient11();
		patient12();
		patient13();
		patient14();
		patient15();
		UC32Acceptance();
		patient_hcp_vists();
		hcp_diagnosis_data();
	}
}
