package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddApptRequestActionTest extends TestCase {

	private AddApptRequestAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.apptRequestConflicts();
		action = new AddApptRequestAction(TestDAOFactory.getTestInstance());
	}

	public void testAddApptRequest() throws Exception {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(9000000000L);
		b.setPatient(2L);
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2012, 7, 20, 13, 00, 0);
		b.setDate(new Timestamp(cal.getTimeInMillis()));
		ApptRequestBean req = new ApptRequestBean();
		req.setRequestedAppt(b);
		String expected = "The appointment you requested conflicts with other existing appointments.";
		assertEquals(expected, action.addApptRequest(req));
		expected = "Your appointment request has been saved and is pending.";
		cal.set(2012, 7, 20, 18, 45, 0);
		req.getRequestedAppt().setDate(new Timestamp(cal.getTimeInMillis()));
		assertEquals(expected, action.addApptRequest(req));
	}

	public void testGetNextAvailableAppts() throws SQLException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(9000000000L);
		b.setPatient(2L);
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2012, 7, 20, 13, 00, 0);
		b.setDate(new Timestamp(cal.getTimeInMillis()));
		List<ApptBean> next = action.getNextAvailableAppts(3, b);
		assertEquals(3, next.size());
		cal.set(2012, 7, 20, 15, 30, 0);
		Timestamp e1 = new Timestamp(cal.getTimeInMillis());
		cal.set(2012, 7, 20, 17, 30, 0);
		Timestamp e2 = new Timestamp(cal.getTimeInMillis());
		cal.set(2012, 7, 20, 18, 15, 0);
		Timestamp e3 = new Timestamp(cal.getTimeInMillis());
		assertEquals(e1, next.get(0).getDate());
		assertEquals(e2, next.get(1).getDate());
		assertEquals(e3, next.get(2).getDate());
	}

}
