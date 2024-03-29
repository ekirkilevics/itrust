package edu.ncsu.csc.itrust.dao.patient;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.Ethnicity;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditPatientTest extends TestCase {
	PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
	}

	public void testGetPatient2() throws Exception {
		PatientBean p = patientDAO.getPatient(2);
		assertNotNull(p);
		assertIsPatient2(p);
	}

	public void testEditPatient2() throws Exception {
		PatientBean p = patientDAO.getPatient(2);
		p.setFirstName("Person1");
		p.setEmail("another email");
		p.setEmergencyName("another emergency person");
		p.setTopicalNotes("some topical notes");
		p.setDateOfBirthStr("05/20/1984");
		patientDAO.editPatient(p);
		
		p = patientDAO.getPatient(2);
		assertEquals("Person1", p.getFirstName());
		assertEquals("Programmer", p.getLastName());
		assertEquals("another email", p.getEmail());
		assertEquals("another emergency person", p.getEmergencyName());
		assertEquals("some topical notes", p.getTopicalNotes());
		assertEquals("05/20/1984", p.getDateOfBirthStr());
		assertEquals("250.10", p.getCauseOfDeath());
		assertEquals("344 Bob Street", p.getStreetAddress1());
		assertEquals("", p.getStreetAddress2());
		assertEquals("Raleigh", p.getCity());
		assertEquals("NC", p.getState());
		assertEquals("27607", p.getZip());
		assertEquals("555-555-5555", p.getPhone());
		assertEquals("555-555-5551", p.getEmergencyPhone());
		assertEquals("IC", p.getIcName());
		assertEquals("Street1", p.getIcAddress1());
		assertEquals("Street2", p.getIcAddress2());
		assertEquals("City", p.getIcCity());
		assertEquals("PA", p.getIcState());
		assertEquals("19003-2715", p.getIcZip());
		assertEquals("555-555-5555", p.getIcPhone());
		assertEquals("1", p.getIcID());
		assertEquals("1", p.getMotherMID());
		assertEquals("0", p.getFatherMID());
		assertEquals("O-", p.getBloodType().getName());
		assertEquals(Ethnicity.Caucasian, p.getEthnicity());
		assertEquals(Gender.Male, p.getGender());
	}

	public void testGetEmpty() throws Exception {
		assertNull(patientDAO.getPatient(0L));
	}

	private void assertIsPatient2(PatientBean p) {
		assertEquals(2L, p.getMID());
		assertEquals("Andy", p.getFirstName());
		assertEquals("Programmer", p.getLastName());
		assertEquals("05/19/1984", p.getDateOfBirthStr());
		assertEquals("250.10", p.getCauseOfDeath());
		assertEquals("andy.programmer@gmail.com", p.getEmail());
		assertEquals("344 Bob Street", p.getStreetAddress1());
		assertEquals("", p.getStreetAddress2());
		assertEquals("Raleigh", p.getCity());
		assertEquals("NC", p.getState());
		assertEquals("27607", p.getZip());
		assertEquals("555-555-5555", p.getPhone());
		assertEquals("Mr Emergency", p.getEmergencyName());
		assertEquals("555-555-5551", p.getEmergencyPhone());
		assertEquals("IC", p.getIcName());
		assertEquals("Street1", p.getIcAddress1());
		assertEquals("Street2", p.getIcAddress2());
		assertEquals("City", p.getIcCity());
		assertEquals("PA", p.getIcState());
		assertEquals("19003-2715", p.getIcZip());
		assertEquals("555-555-5555", p.getIcPhone());
		assertEquals("1", p.getIcID());
		assertEquals("1", p.getMotherMID());
		assertEquals("0", p.getFatherMID());
		assertEquals("O-", p.getBloodType().getName());
		assertEquals(Ethnicity.Caucasian, p.getEthnicity());
		assertEquals(Gender.Male, p.getGender());
		assertEquals("This person is absolutely crazy. Do not touch them.", p.getTopicalNotes());
	}
}
