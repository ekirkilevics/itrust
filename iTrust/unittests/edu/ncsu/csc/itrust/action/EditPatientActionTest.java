package edu.ncsu.csc.itrust.action;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditPatientActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPatientAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		action = new EditPatientAction(factory, 0L, "2");
	}

	public void testConstructNormal() throws Exception {
		assertEquals(2L, action.getPid());
	}

	public void testNonExistent() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, "200");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Patient does not exist", e.getMessage());
		}
	}

	public void testEditRepresentatives() throws Exception {
		action = new EditPatientAction(factory, 2l, "2");
		PatientDAO po = new PatientDAO(factory);
		PatientBean pb = po.getPatient(2);
		assertEquals("Andy", pb.getFirstName());
		assertEquals("Programmer", pb.getLastName());
		assertEquals("0", pb.getFatherMID());
		pb.setFatherMID("1");
		assertEquals("1", pb.getFatherMID());
		action.updateInformation(pb);
		PatientBean pb2 = po.getPatient(2);
		assertEquals("Andy", pb2.getFirstName());
		assertEquals("Programmer", pb2.getLastName());
		assertEquals("1", pb2.getFatherMID());

	}

	public void testEditCOD() throws Exception {
		gen.patient1();
		action = new EditPatientAction(factory, 1L, "1");
		PatientDAO po = TestDAOFactory.getTestInstance().getPatientDAO();
		PatientBean pb = po.getPatient(1l);
		assertEquals("Random", pb.getFirstName());
		assertEquals("", pb.getCauseOfDeath());
		assertEquals("", pb.getDateOfDeathStr());
		pb.setCauseOfDeath("79.1");
		pb.setDateOfDeathStr("01/03/2006");
		action.updateInformation(pb);
		PatientBean pb2 = po.getPatient(1l);
		assertEquals("Random", pb2.getFirstName());
		assertEquals("79.1", pb2.getCauseOfDeath());
		assertEquals("01/03/2006", pb2.getDateOfDeathStr());

	}

	public void testWrongFormat() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, "hello!");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Patient ID is not a number: hello!", e.getMessage());
		}
	}

	public void testNull() throws Exception {
		try {
			action = new EditPatientAction(factory, 0L, null);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Patient ID is not a number: null", e.getMessage());
		}
	}
	
	public void testGetPatientLogged() throws Exception {
		PatientBean patient = action.getPatient();
		assertEquals(2L, patient.getMID());
		List<TransactionBean> transList = factory.getTransactionDAO().getAllTransactions();
		assertEquals(1, transList.size());
		assertEquals(TransactionType.ENTER_EDIT_DEMOGRAPHICS, transList.get(0).getTranactionType());
	}
}
