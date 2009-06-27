package edu.ncsu.csc.itrust.datagenerators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class TestDataForCauseOfDeath {
	private String DIR = "sql/data";
	private DAOFactory factory;

	public TestDataForCauseOfDeath() {
		this.factory = TestDAOFactory.getTestInstance();
	}

	public static void main(String[] args) throws IOException, SQLException {
		new TestDataForCauseOfDeath().insertAllData();
	}

	public void insertAllData() throws FileNotFoundException, IOException, SQLException {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hospitals();
		gen.hcp0();
		icdcodes();
		patients();
		hcps();
		ovs();
		System.out.println("Operation completed.");
	}

	public void icdcodes() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/icd9cmCodes.sql");
	}

	public void patients() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/codpatients.sql");
	}

	public void hcps() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/codhcps.sql");
	}

	public void ovs() throws SQLException, FileNotFoundException, IOException {
		new DBBuilder(factory).executeSQLFile(DIR + "/codOfficeVisits.sql");
	}
}
