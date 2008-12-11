package edu.ncsu.csc.itrust.dao.appointmentrequest;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class UpdateAppointmentRequestTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.appointmentRequests();
	}
	
	public void testUpdateAppointmentRequestStatus1() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		bean.setStatus(AppointmentRequestBean.NeedLHCPConfirm);
		appointmentRequestDAO.updateAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(AppointmentRequestBean.NeedLHCPConfirm, newBean.getStatus());
	}

	public void testUpdateAppointmentRequestStatus2() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		bean.setStatus(AppointmentRequestBean.NeedPatientConfirm);
		appointmentRequestDAO.updateAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(AppointmentRequestBean.NeedPatientConfirm, newBean.getStatus());
	}

	public void testUpdateAppointmentRequestStatus3() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		bean.setStatus(AppointmentRequestBean.Scheduled);
		appointmentRequestDAO.updateAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(AppointmentRequestBean.Scheduled, newBean.getStatus());
	}

	public void testUpdateAppointmentRequestStatus4() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		bean.setStatus(AppointmentRequestBean.Rejected);
		appointmentRequestDAO.updateAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(AppointmentRequestBean.Rejected, newBean.getStatus());
	}

	public void testUpdateAppointmentRequestStatus5() throws Exception {
		AppointmentRequestBean bean = appointmentRequestDAO.getAppointmentRequest(1);
		bean.setStatus(AppointmentRequestBean.Rescheduled);
		appointmentRequestDAO.updateAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(1);
		assertEquals(AppointmentRequestBean.Rescheduled, newBean.getStatus());
	}

}
