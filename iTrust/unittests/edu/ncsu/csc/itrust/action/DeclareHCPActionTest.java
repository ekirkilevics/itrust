package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DeclareHCPActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private DeclareHCPAction action;

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.hcp3();
		action = new DeclareHCPAction(factory, 2L);
	}

	public void testGetDeclared() throws Exception {
		List<PersonnelBean> decs = action.getDeclaredHCPS();
		assertEquals(1, decs.size());
		assertEquals(9000000003L, decs.get(0).getMID());
	}

	public void testDeclareNormal() throws Exception {
		assertEquals("HCP successfully declared", action.declareHCP("9000000000"));
		List<PersonnelBean> decs = action.getDeclaredHCPS();
		assertEquals(2, decs.size());
		assertEquals(9000000000L, decs.get(0).getMID());
		// Assert the transaction
		List<TransactionBean> transList = factory.getTransactionDAO().getAllTransactions();
		assertEquals(1, transList.size());
		assertEquals(TransactionType.DECLARE_HCP, transList.get(0).getTranactionType());
		assertEquals(2L, transList.get(0).getLoggedInMID());
		assertEquals(9000000000L, transList.get(0).getSecondaryMID());

	}

	public void testUnDeclareNormal() throws Exception {
		assertEquals("HCP successfully undeclared", action.undeclareHCP("9000000003"));
		List<PersonnelBean> decs = action.getDeclaredHCPS();
		assertEquals(0, decs.size());
		// Assert the transaction
		List<TransactionBean> transList = factory.getTransactionDAO().getAllTransactions();
		assertEquals(1, transList.size());
		assertEquals(TransactionType.DECLARE_HCP, transList.get(0).getTranactionType());
		assertEquals(2L, transList.get(0).getLoggedInMID());
		assertEquals(9000000003L, transList.get(0).getSecondaryMID());
	}

	public void testDeclareAlreadyDeclared() throws Exception {
		try {
			action.declareHCP("9000000003");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("HCP 9000000003 has already been declared for patient 2", e.getMessage());
		}
		List<PersonnelBean> decs = action.getDeclaredHCPS();
		assertEquals(1, decs.size());
		assertEquals(9000000003L, decs.get(0).getMID());
		// Assert the transaction
		List<TransactionBean> transList = factory.getTransactionDAO().getAllTransactions();
		assertEquals(0, transList.size());
	}

	public void testUnDeclareNotDeclared() throws Exception {
		assertEquals("HCP not undeclared", action.undeclareHCP("9000000000"));
		List<PersonnelBean> decs = action.getDeclaredHCPS();
		assertEquals(1, decs.size());
		// Assert the transaction
		List<TransactionBean> transList = factory.getTransactionDAO().getAllTransactions();
		assertEquals(0, transList.size());
	}

	public void testDeclareMalformed() throws Exception {
		try {
			action.declareHCP("not a number");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("HCP's MID not a number", e.getMessage());
		}
	}

	public void testUnDeclareMalformed() throws Exception {
		try {
			action.undeclareHCP("not a number");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("HCP's MID not a number", e.getMessage());
		}
	}

	public void testDeclareAdmin() throws Exception {
		gen.admin1();
		try {
			action.declareHCP("9000000001");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("This user is not a licensed healthcare professional!", e.getMessage());
		}
	}
}
