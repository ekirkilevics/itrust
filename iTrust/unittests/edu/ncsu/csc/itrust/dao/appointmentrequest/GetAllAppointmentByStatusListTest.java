/**
 * 
 */
package edu.ncsu.csc.itrust.dao.appointmentrequest;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * @author Kathryn Lemanski
 *
 */
public class GetAllAppointmentByStatusListTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();

	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointmentRequests();
	}
	
	public void testGetAppointmentRequestList1() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequestsByStatus(2L, "Need Patient Confirm");
		int size = list.size();
		assertEquals(size, 5);
		assertEquals(9000000000L, list.get(0).getRequesterMID());
		assertEquals("need to check the status of your rash.", list.get(0).getReason());
		
		assertEquals(9000000003L, list.get(3).getRequesterMID());
		assertEquals("discuss lab results", list.get(3).getReason());
	}
	
	public void testGetAppointmentRequestList2() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequestsByStatus(2L, "Scheduled");
		int size = list.size();
		assertEquals(size, 0);
	}
	
	public void testGetAppointmentRequestList3() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequestsByStatus(9000000000L, "Scheduled");
		int size = list.size();
		assertEquals(size, 1);
		
		assertEquals(1L, list.get(0).getRequesterMID());
		assertEquals("Routine physical", list.get(0).getReason());
	}
	
	public void testGetAppointmentRequestList4() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequestsByStatus(9000000000L, "Need LHCP Confirm");
		int size = list.size();
		assertEquals(size, 2);
		
		assertEquals(0, list.get(0).getWeeksUntilVisit());  //shown as null in database
	}
	
	public void testGetAppointmentRequestList5() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequestsByStatus(9000000000L, "Rejected");
		int size = list.size();
		assertEquals(size, 1);
	}

	public void testGetAllAppointmentRequestsByStatusFail() throws Exception {
		try {
			appointmentRequestDAO.getAllAppointmentRequestsByStatus(0, null);
			fail("Should throw an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}

	public void testGetAppointmentRequestsHistoryFail() throws Exception {
		try {
			appointmentRequestDAO.getAppointmentRequestHistory(0, null);
			fail("Should throw an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}
}
