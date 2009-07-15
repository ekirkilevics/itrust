package edu.ncsu.csc.itrust.dao.reportrequest;

import java.text.SimpleDateFormat;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ReportRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class ReportRequestDAOTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ReportRequestDAO dao = factory.getReportRequestDAO();
	
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.reportRequests();
		
	}
	
	public void testGetAllReportRequests1() throws Exception {
		List<ReportRequestBean> list = dao.getAllReportRequests();
		assertEquals(7, list.size());
	}

	//TODO Rename all of these methods to be actually meaningful. Shame!
	public void testGetReportRequests2() throws Exception {
		try {
			dao.getAllReportRequestsForRequester(0);
			fail("should have thrown an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testGetReportRequests3() throws Exception {
		List<ReportRequestBean> list = dao.getAllReportRequestsForRequester(9000000000L);
		assertEquals(6, list.size());
		assertEquals(ReportRequestBean.Requested, list.get(0).getStatus());
		assertEquals("Forget it", list.get(2).getComment());
		assertEquals(9000000001L, list.get(5).getApproverMID());
	}
	
	public void testGetReportRequests4() throws Exception {
		List<ReportRequestBean> list = dao.getAllReportRequestsForPatient(2L);
		assertEquals(4, list.size());
		assertEquals(ReportRequestBean.Requested, list.get(0).getStatus());
		assertEquals("Forget it", list.get(2).getComment());
		assertEquals(9000000001L, list.get(3).getApproverMID());
	}
	
	public void testGetReportRequest1() throws Exception {
		try {
			dao.getReportRequest(0);
			fail("Should have thrown an exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testGetReportRequest2() throws Exception {
		ReportRequestBean b = dao.getReportRequest(3);
		assertEquals(3, b.getID());
		assertEquals(9000000000L, b.getRequesterMID());
		assertEquals(2, b.getPatientMID());
		assertEquals(9000000001L, b.getApproverMID());
		assertEquals("01/03/2008 12:00", b.getRequestedDateString());
		assertEquals("02/03/2008 12:00", b.getApprovedDateString());
		assertEquals(ReportRequestBean.Rejected, b.getStatus());
		assertEquals("Forget it", b.getComment());
	}

	public void testGetReportRequest3() throws Exception {
		ReportRequestBean b = dao.getReportRequest(4);
		assertEquals(4, b.getID());
		assertEquals(9000000000L, b.getRequesterMID());
		assertEquals(2, b.getPatientMID());
		assertEquals(9000000001L, b.getApproverMID());
		assertEquals("01/04/2008 12:00", b.getRequestedDateString());
		assertEquals("02/04/2008 12:00", b.getApprovedDateString());
		assertEquals("03/04/2008 12:00", b.getViewedDateString());
		assertEquals(ReportRequestBean.Viewed, b.getStatus());
	}
	
	public void testInsertReport1() throws Exception {
		try {
			dao.addReportRequest(0, 0, null);
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testInsertReport2() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals(9000000000L, b2.getRequesterMID());
		assertEquals(2, b2.getPatientMID());
		assertEquals("06/06/2008 13:00", b2.getRequestedDateString());
		assertEquals(ReportRequestBean.Requested, b2.getStatus());
	}
	
	public void testApproveReport1() throws Exception {
		try {
			dao.approveReportRequest(0, 0, null);
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testApproveReport2() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		dao.approveReportRequest(id, 9000000001L, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("07/07/2008 14:00"));
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals(9000000001L, b2.getApproverMID());
		assertEquals("07/07/2008 14:00", b2.getApprovedDateString());
		assertEquals(ReportRequestBean.Approved, b2.getStatus());
	}
	
	public void testRejectReport1() throws Exception {
		try {
			dao.rejectReportRequest(0, 0, null, "");
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testRejectReport2() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		dao.rejectReportRequest(id, 9000000001L, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("07/07/2008 14:00"), "You don't have the proper clearance");
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals(9000000001L, b2.getApproverMID());
		assertEquals("07/07/2008 14:00", b2.getApprovedDateString());
		assertEquals(ReportRequestBean.Rejected, b2.getStatus());
		assertEquals("You don't have the proper clearance", b2.getComment());
	}
	
	public void testSetViewed1() throws Exception {
		try {
			dao.setViewed(0, null);
			fail("Should have throw exception");
		} catch (DBException ex) {
			assertEquals("ID cannot be null", ex.getSQLException().getMessage());
		}
	}
	
	public void testSetViewed2() throws Exception {
		long id = dao.addReportRequest(9000000000L, 2, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("06/06/2008 13:00"));
		dao.approveReportRequest(id, 9000000001L, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("07/07/2008 14:00"));
		dao.setViewed(id, new SimpleDateFormat(ReportRequestBean.dateFormat).parse("08/08/2008 15:00"));
		ReportRequestBean b2 = dao.getReportRequest(id);
		assertEquals("08/08/2008 15:00", b2.getViewedDateString());
		assertEquals(ReportRequestBean.Viewed, b2.getStatus());
	}
}
