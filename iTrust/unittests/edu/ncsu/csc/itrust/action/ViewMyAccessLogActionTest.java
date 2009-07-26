package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewMyAccessLogActionTest extends TestCase {
	ViewMyAccessLogAction action;

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.patient2();
		action = new ViewMyAccessLogAction(TestDAOFactory.getTestInstance(), 2L);
	}

	public void testNoProblems() throws Exception {
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		List<TransactionBean> accesses = action.getAccesses(today, today);
		assertEquals(0, accesses.size());
	}
	
	public void testGetAccessesBadData() throws Exception {
		new TestDataGenerator().transactionLog();
		List<TransactionBean> accesses = action.getAccesses(null, null);
		assertEquals(5, accesses.size());
		for (TransactionBean t : accesses) {
			assertEquals(9000000000L, t.getLoggedInMID());
			assertEquals(2L, t.getSecondaryMID());
			assertEquals("Viewed patient records", t.getAddedInfo());
		}
		// note: the actual bounding is not done here, see the DAO test
		try {
			action.getAccesses("", "");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Enter dates in MM/dd/yyyy", e.getErrorList().get(0));
		}
	}

	public void testDefaultNoList() throws Exception {
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		assertEquals(today, action.getDefaultStart(new ArrayList<TransactionBean>()));
		assertEquals(today, action.getDefaultEnd(new ArrayList<TransactionBean>()));
	}

	public void testDefaultWithList() throws Exception {
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		ArrayList<TransactionBean> list = new ArrayList<TransactionBean>();
		TransactionBean t = new TransactionBean();
		t.setTimeLogged(new Timestamp(System.currentTimeMillis()));
		list.add(t);
		assertEquals(today, action.getDefaultStart(list));
		assertEquals(today, action.getDefaultEnd(list));
	}
}
