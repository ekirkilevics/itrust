package edu.ncsu.csc.itrust.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.*;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.DBUtil;

public class ForeignKeyTest extends TestCase {
	TestDataGenerator gen = new TestDataGenerator();
	DBBuilder dbb = new DBBuilder();
	Connection conn = null;
	PreparedStatement ps = null;
	
	@Override
	protected void setUp() throws Exception {
		
		conn = TestDAOFactory.getTestInstance().getConnection();

		dbb.dropTables();
		dbb.createTables();
		gen.hospitals();
		gen.hcp0();
		gen.patient1();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.officeVisit1(); // create visitID 1
		
		

	}
	
	public void tearDown() throws Exception {
		DBUtil.closeConnection(conn, ps);
	}

	public void testBadOfficeVisit() {
	
		try {
			String statement = "INSERT INTO OfficeVisits(id, hcpid, HospitalID)" + 
				"VALUES (5001, 9000000000, '9191919000');"; // Hospital ID does not exist in hospitals table
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("OV with bad HospitalID should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}

	}
	
	public void testGoodOfficeVisit() {
		
		try {
			String statement = "INSERT INTO OfficeVisits(id, hcpid, HospitalID)" + 
				"VALUES (5001, 9000000000, '9191919191');"; // Hospital ID exists in hospitals table
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail("OV with good HospitalID threw an exception");
			
		}

	}
	
	
	public void testBadDiagnosis() {
		
		try {
			String statement = "INSERT INTO OVDiagnosis(id, VisitID, ICDCode)" + 
				"VALUES (5001, 1, '16.00');"; // Non-existent ICDCode
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("Diagnosis with bad ICDCode should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}
		
		
		try {
			String statement = "INSERT INTO OVDiagnosis(id, VisitID, ICDCode)" + 
				"VALUES (5001, 2, '15.00');"; // Non-existent VisitID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("Diagnosis with bad VisitID should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}

	}
	
	public void testGoodDiagnosis() {
		
		try {
			String statement = "INSERT INTO OVDiagnosis(id, VisitID, ICDCode)" + 
			"VALUES (5001, 1, '15.00');"; // Proper ICDCode and VisitID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail("OV with good HospitalID threw an exception");
			
		}

	}

	
	public void testBadMedication() {
		
		try {
			String statement = "INSERT INTO OVMedication(id, VisitID, NDCode)" + 
				"VALUES (5001, 1, '009042400');"; // Non-existent NDCode
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("Medication with bad NDCode should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}
		
		
		try {
			String statement = "INSERT INTO OVDiagnosis(id, VisitID, ICDCode)" + 
				"VALUES (5001, 2, '009042407');"; // Non-existent VisitID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("Medication with bad visitid should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}

	}
	
	public void testGoodMedication() {
		
		try {
			String statement = "INSERT INTO OVMedication(id, VisitID, NDCode)" + 
			"VALUES (5001, 1, '009042407');"; // Proper NDCode and VisitID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail("OV with good HospitalID threw an exception");
			
		}

	}
	
	
public void testBadDeclaredHCP() {
		
		try {
			String statement = "INSERT INTO DeclaredHCP(PatientID, HCPID)" + 
				"VALUES (1, 5000000000);"; // Non-existent HCPID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("DeclaredHCP with bad HCPID should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}
		
		
		try {
			String statement = "INSERT INTO DeclaredHCP(PatientID, HCPID)" + 
				"VALUES (29, 9000000000);"; // Non-existent PatientID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			fail("DeclaredHCP with bad PatientID should have thrown exception");
			
		} catch (SQLException e) {
			System.out.println("Good job. Exception was: " + e.getMessage());
		}

	}
	
	public void testGoodDeclaredHCP() {
		
		try {
			String statement = "INSERT INTO DeclaredHCP(PatientID, HCPID)" + 
			"VALUES (1, 9000000000);"; // Proper PatientID and HCPID
			
			ps = conn.prepareStatement(statement);
			ps.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail("DeclaredHCP with good PatientID and HCPID threw exception");
			
		}

	}


}
