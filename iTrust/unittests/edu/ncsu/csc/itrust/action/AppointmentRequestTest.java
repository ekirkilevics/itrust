package edu.ncsu.csc.itrust.action;

import java.text.SimpleDateFormat;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AppointmentRequestTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();
	private AppointmentDAO appointmentDAO = factory.getAppointmentDAO();
	private TestDataGenerator gen;
	private FakeEmailDAO feDAO = factory.getFakeEmailDAO();
	AppointmentRequestAction action;
	AppointmentRequestAction emailAction;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.hcp3();
		gen.transactionLog();
		gen.patient2();
		gen.appointmentRequests();
		gen.appointments();
		action = new AppointmentRequestAction(factory, 2L);
		emailAction = new AppointmentRequestAction(factory, 9000000000L);
	}

	public void testAddAppointmentRequest() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		String d1 = "03/01/2008 13:30:00";
		String d2 = "04/01/2008 14:30:00";
		String format = "MM/dd/yyyy HH:mm:ss";
		bean.setDate1(new SimpleDateFormat(format).parse(d1));
		bean.setDate2(new SimpleDateFormat(format).parse(d2));
		bean.setRequesterMID(2);
		bean.setRequestedMID(9000000000L);
		bean.setHospitalID("8181818181");
		bean.setReason("Testing");
		bean.setStatus(AppointmentRequestBean.NeedLHCPConfirm);
		bean.setWeeksUntilVisit(4);
		long requestID = action.addAppointmentRequest(bean);
		bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		assertEquals(bean.getID(), requestID);
	}
	
	public void testRescheduleAppointmentRequest() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		long requestID = action.rescheduleAppointmentRequest(bean, 1);
		bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		assertEquals(bean.getID(), requestID);
		assertEquals(bean.getStatus(), AppointmentRequestBean.NeedPatientConfirm);
		bean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(bean.getStatus(), AppointmentRequestBean.Rescheduled);
	}

	public void testRejectAppointmentRequest1() throws Exception {
		long requestID = 1;
		action.rejectAppointmentRequest(requestID);
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		assertEquals(bean.getID(), requestID);
		assertEquals(bean.getStatus(), AppointmentRequestBean.Rejected);
		List<Email> list = feDAO.getAllFakeEmails();
		assertEquals("Dear Kelly Doctor, \n The appointment with Andy Programmer has been rejected. \n Additional Comments: need to check the status of your rash.", list.get(0).getBody());
	}

	public void testRejectAppointmentRequest2() throws Exception {
		long requestID = 2;
		action.rejectAppointmentRequest(requestID);
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(requestID);
		assertEquals(bean.getID(), requestID);
		assertEquals(bean.getStatus(), AppointmentRequestBean.Rejected);
		List<Email> list = feDAO.getAllFakeEmails();
		assertEquals("Dear Kelly Doctor, \n The appointment with Andy Programmer has been rejected. \n Additional Comments: ", list.get(0).getBody());
	}

	public void testScheduleAppointmentRequest() throws Exception {
		AppointmentRequestBean reqBean = appointmentRequestDAO.getAppointmentRequest(2);
		AppointmentBean apptBean = new AppointmentBean();
		apptBean.setLHCPMID(reqBean.getRequesterMID());
		apptBean.setPatientMID(reqBean.getRequestedMID());
		apptBean.setAppointmentDate(reqBean.getDate1());
		apptBean.setRequestID(reqBean.getID());
		long appointmentID = action.scheduleAppointment(apptBean, 2);
		apptBean = appointmentDAO.getAppointment(appointmentID);
		assertEquals(apptBean.getID(), appointmentID);
		reqBean = appointmentRequestDAO.getAppointmentRequest(2);
		assertEquals(reqBean.getStatus(), AppointmentRequestBean.Scheduled);
		List<Email> list = feDAO.getAllFakeEmails();
		assertEquals("Dear Kelly Doctor, \n The appointment with Andy Programmer has been scheduled on 07/25/2007 10:00 for 0 minutes.", list.get(0).getBody());
	}
	
	public void testEmailScheduleAppointmentRequest() throws Exception{
		AppointmentRequestBean reqBean = new AppointmentRequestBean();
		reqBean.setMinutes(30);
		reqBean.setRequesterMID(2L);
		reqBean.setRequestedMID(9000000000L);
		reqBean.setDate1String("10/27/2009 00:00:00");
		reqBean.setDate2String("10/28/2009 00:00:00");
		reqBean.setStatus("Scheduled");
		long id = appointmentRequestDAO.addAppointmentRequest(reqBean);
		AppointmentBean apptBean = new AppointmentBean();
		apptBean.setLHCPMID(reqBean.getRequesterMID());
		apptBean.setPatientMID(reqBean.getRequestedMID());
		apptBean.setAppointmentDate(reqBean.getDate1());
		apptBean.setRequestID(reqBean.getID());
		long appointmentID = emailAction.scheduleAppointment(apptBean, id);
		apptBean = appointmentDAO.getAppointment(appointmentID);
		assertEquals(apptBean.getID(), appointmentID);
		reqBean = appointmentRequestDAO.getAppointmentRequest(id);
		assertEquals(reqBean.getStatus(), AppointmentRequestBean.Scheduled);
		List<Email> list = feDAO.getAllFakeEmails();
		assertEquals("Dear Andy Programmer, \n The appointment with Kelly Doctor has been scheduled" +
				" on 10/27/2009 00:00 for 0 minutes.", list.get(0).getBody());
		
	}

}
