package edu.ncsu.csc.itrust.dao.transaction;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class LogTransactionTest extends TestCase {
	private TransactionDAO tranDAO = TestDAOFactory.getTestInstance().getTransactionDAO();

	private TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
	}

	public void testGetAllTransactions() throws Exception {
		List<TransactionBean> list = tranDAO.getAllTransactions();
		assertEquals(8, list.size());
		// that last one inserted should be last because it was backdated
		assertEquals(1L, list.get(3).getLoggedInMID());
		assertEquals(TransactionType.VIEW_ACCESS_LOG, list.get(3).getTranactionType());
	}
	
	public void testLogSimple() throws Exception {
		tranDAO.logTransaction(TransactionType.PATIENT_REMINDERS, 9000000000L);
		List<TransactionBean> list = tranDAO.getAllTransactions();
		assertEquals(9, list.size());
		assertEquals(9000000000L, list.get(0).getLoggedInMID());
		assertEquals(0L, list.get(0).getSecondaryMID());
		assertEquals("", list.get(0).getAddedInfo());
		assertEquals(TransactionType.PATIENT_REMINDERS, list.get(0).getTranactionType());
	}
	
	public void testLogFull() throws Exception {
		tranDAO.logTransaction(TransactionType.DOCUMENT_OFFICE_VISIT, 9000000000L, 1L, "added information");
		List<TransactionBean> list = tranDAO.getAllTransactions();
		assertEquals(9, list.size());
		assertEquals(9000000000L, list.get(0).getLoggedInMID());
		assertEquals(1L, list.get(0).getSecondaryMID());
		assertEquals("added information", list.get(0).getAddedInfo());
		assertEquals(TransactionType.DOCUMENT_OFFICE_VISIT, list.get(0).getTranactionType());
	}
	
	/**
	 * Tests to see if the right MID number shows up in the secondaryMID column in the transactionLog.
	 * @throws Exception
	 */
	public void testSecondaryMIDHCP() throws Exception{
		tranDAO.logTransaction(TransactionType.CREATE_DISABLE_PATIENT_HCP, 9000000000L, 98L, "added information");
		List<TransactionBean> list = tranDAO.getAllTransactions();

		assertEquals(9000000000L, list.get(0).getLoggedInMID());
		assertEquals(98L, list.get(0).getSecondaryMID());
	}
	
	public void testSecondaryMIDPatient() throws Exception{
		tranDAO.logTransaction(TransactionType.CREATE_DISABLE_PATIENT_HCP, 1L, 98L, "added information");
		List<TransactionBean> list = tranDAO.getAllTransactions();

		assertEquals(1L, list.get(0).getLoggedInMID());
		assertEquals(98L, list.get(0).getSecondaryMID());
	}

	public void testSecondaryMIDUAP() throws Exception{
		tranDAO.logTransaction(TransactionType.CREATE_DISABLE_PATIENT_HCP, 9000000001L, 98L, "added information");
		List<TransactionBean> list = tranDAO.getAllTransactions();

		assertEquals(9000000001L, list.get(0).getLoggedInMID());
		assertEquals(98L, list.get(0).getSecondaryMID());
	}
}
