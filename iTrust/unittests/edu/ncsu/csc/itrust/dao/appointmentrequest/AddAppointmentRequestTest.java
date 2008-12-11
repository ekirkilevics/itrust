package edu.ncsu.csc.itrust.dao.appointmentrequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AppointmentRequestAction;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddAppointmentRequestTest extends TestCase {
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

	public void testAddAppointmentRequest() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		AppointmentRequestAction action = new AppointmentRequestAction(TestDAOFactory.getTestInstance(), 2L);
		String d1 = "03/01/2008 13:30";
		String d2 = "04/01/2008 14:30";
		bean.setDate1(new SimpleDateFormat(AppointmentRequestBean.dateFormat).parse(d1));
		bean.setDate1String(d1);
		bean.setDate2(new SimpleDateFormat(AppointmentRequestBean.dateFormat).parse(d2));
		bean.setDate2String(d2);
		bean.setRequesterMID(2);
		bean.setRequestedMID(9000000000L);
		bean.setHospitalID("8181818181");
		bean.setReason("Testing");
		bean.setStatus(AppointmentRequestBean.NeedLHCPConfirm);
		bean.setWeeksUntilVisit(4);
		bean.setMinutes(60);
		long appointmentID = action.addAppointmentRequest(bean);
		AppointmentRequestBean newBean = appointmentRequestDAO.getAppointmentRequest(appointmentID);
		assertEquals(appointmentID, newBean.getID());
		assertEquals(d1, new SimpleDateFormat(AppointmentRequestBean.dateFormat).format(newBean.getDate1()));
		assertEquals(d2, new SimpleDateFormat(AppointmentRequestBean.dateFormat).format(newBean.getDate2()));
		assertEquals(2, newBean.getRequesterMID());
		assertEquals(9000000000L, newBean.getRequestedMID());
		assertEquals("8181818181", newBean.getHospitalID());
		assertEquals("Testing", newBean.getReason());
		assertEquals(AppointmentRequestBean.NeedLHCPConfirm, newBean.getStatus());
		assertEquals(4, bean.getWeeksUntilVisit());
		assertEquals("8181818181", newBean.getHospitalIDString());
		assertEquals("03/01/2008 13:30", newBean.getDate1String());
		assertEquals("04/01/2008 14:30", newBean.getDate2String());
		assertEquals("Testing", newBean.getReasonString());
		assertEquals(60, newBean.getMinutes());
		try{
		bean.setDate1String("bad parse");
		bean.setDate2String("bad parse");
		}
		catch(ParseException e){
			assertEquals("Unparseable date: \"bad parse\"", e.getMessage());
			
		}
	}
}
