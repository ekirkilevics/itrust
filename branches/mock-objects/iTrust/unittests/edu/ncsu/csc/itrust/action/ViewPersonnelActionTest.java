package edu.ncsu.csc.itrust.action;

import static edu.ncsu.csc.itrust.testutils.JUnitiTrustUtils.assertTransactionOnly;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewPersonnelActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewPersonnelAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient4();
		gen.hcp3();
	}

	public void testViewPersonnel() throws Exception {
		action = new ViewPersonnelAction(factory, 4L);
		PersonnelBean hcp = action.getPersonnel("9000000003");
		assertEquals(9000000003L, hcp.getMID());
		assertTransactionOnly(TransactionType.ENTER_EDIT_DEMOGRAPHICS, 4L, 9000000003L,
				"Patient viewed personnel 9000000003");
	}

	public void testNoPersonnel() throws Exception {
		action = new ViewPersonnelAction(factory, 4L);
		try {
			action.getPersonnel("9000000000");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("No personnel record exists for this MID", e.getMessage());
		}
	}

	public void testNotANumber() throws Exception {
		action = new ViewPersonnelAction(factory, 4L);
		try {
			action.getPersonnel("a");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("MID not a number", e.getMessage());
		}
	}

}
