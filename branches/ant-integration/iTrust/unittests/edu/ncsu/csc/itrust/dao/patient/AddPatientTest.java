package edu.ncsu.csc.itrust.dao.patient;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddPatientTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	public void testAddEmptyPatient() throws Exception {
		long pid = patientDAO.addEmptyPatient();
		assertEquals(" ", patientDAO.getName(pid));
	}

	public void testGetEmptyPatient() throws Exception {
		try {
			patientDAO.getName(0L);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("User does not exist", e.getMessage());
		}
	}

	public void testInsertDeath() throws Exception {
		gen.patient1();
		PatientBean p = patientDAO.getPatient(1l);
		assertEquals("Random", p.getFirstName());
		assertEquals("", p.getCauseOfDeath());
		assertEquals("", p.getDateOfDeathStr());
		p.setDateOfDeathStr("09/12/2007");
		p.setCauseOfDeath("79.3");
		patientDAO.editPatient(p);
		PatientBean p2 = patientDAO.getPatient(1l);
		assertEquals("79.3", p2.getCauseOfDeath());
		assertEquals("09/12/2007", p2.getDateOfDeathStr());
	}

	public void testEmergencyContactInfo() throws Exception {
		long pid = patientDAO.addEmptyPatient();
		PatientBean p = patientDAO.getPatient(pid);
		p.setFirstName("Lola");
		p.setLastName("Schaefer");
		p.setEmail("l@cox.net");
		p.setCity("Raleigh");
		p.setState("NC");
		p.setZip1("27602");
		p.setPhone1("222");
		p.setPhone2("222");
		p.setPhone3("333");
		p.setSecurityQuestion("What is the best team in the acc?");
		p.setSecurityAnswer("NCSU");
		p.setIcName("Blue Cross");
		p.setIcAddress1("222 Blue Rd");
		p.setIcCity("Raleigh");
		p.setIcState("NC");
		p.setIcZip1("27607");
		p.setIcPhone1("222");
		p.setIcPhone2("333");
		p.setIcPhone3("4444");
		p.setIcID("2343");
		p.setEmergencyName("Joy Jones");
		p.setEmergencyPhone1("012");
		p.setEmergencyPhone2("345");
		p.setEmergencyPhone3("6789");
		patientDAO.editPatient(p);
		assertEquals("Joy Jones", patientDAO.getPatient(pid).getEmergencyName());
		assertEquals("012", patientDAO.getPatient(pid).getEmergencyPhone1());
		assertEquals("345", patientDAO.getPatient(pid).getEmergencyPhone2());
		assertEquals("6789", patientDAO.getPatient(pid).getEmergencyPhone3());
	}

}
