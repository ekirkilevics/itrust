package edu.ncsu.csc.itrust.dao.appointment;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AppointmentAction;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddAppointmentTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentDAO appointmentDAO = factory.getAppointmentDAO();
	String format = "MM/dd/yyyy HH:mm:ss";

	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.hcp3();
	}
	
	public void testAddAppointment1() throws Exception {
		AppointmentBean apptBean = new AppointmentBean();
		AppointmentAction apptAction = new AppointmentAction(TestDAOFactory.getTestInstance(), 9000000003L);
		String apptDate = "04/03/2009 12:00:00";
		apptBean.setAppointmentDate(new SimpleDateFormat(format).parse(apptDate));
		apptBean.setPatientMID(2);
		apptBean.setLHCPMID(9000000000L);
		apptBean.setRequestID(2147483647);
		apptBean.setMinutes(20);
		
		long appointmentID = apptAction.addAppointment(apptBean);
		AppointmentBean newApptBean = appointmentDAO.getAppointment(appointmentID);
		//check to see if added appt to database correctly
		assertEquals(appointmentID, newApptBean.getID());
		//check to see if correct date added
		assertEquals(apptDate, new SimpleDateFormat(format).format(newApptBean.getAppointmentDate()));
		//check to see if the correct requestID is added
		assertEquals(2147483647, newApptBean.getRequestID());
		//check for correct patient ID
		assertEquals(2, newApptBean.getPatientMID());
		//check for correct LHCP ID
		assertEquals(9000000000L, newApptBean.getLHCPMID());
		//check for correct minutes
		assertEquals(20, newApptBean.getMinutes());
	}
	
	public void testAddAppointment2() throws Exception {
		AppointmentBean apptBean = new AppointmentBean();
		AppointmentAction apptAction = new AppointmentAction(TestDAOFactory.getTestInstance(), 9000000003L);
		String apptDate = "12/31/2009 15:00:00";
		apptBean.setAppointmentDate(new SimpleDateFormat(format).parse(apptDate));
		apptBean.setPatientMID(1);
		apptBean.setLHCPMID(9000000003L);
		apptBean.setRequestID(2147483647);
		apptBean.setMinutes(9999);
		
		long appointmentID = apptAction.addAppointment(apptBean);
		AppointmentBean newApptBean = appointmentDAO.getAppointment(appointmentID);
		//check to see if added appt to database correctly
		assertEquals(appointmentID, newApptBean.getID());
		//check to see if correct date added
		assertEquals(apptDate, new SimpleDateFormat(format).format(newApptBean.getAppointmentDate()));
		//check to see if the correct requestID is added
		assertEquals(2147483647, newApptBean.getRequestID());
		//check for correct patient ID
		assertEquals(1, newApptBean.getPatientMID());
		//check for correct LHCP ID
		assertEquals(9000000003L, newApptBean.getLHCPMID());
		assertEquals(9999, newApptBean.getMinutes());
	}
	
	
}
