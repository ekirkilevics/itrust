package edu.ncsu.csc.itrust.validate.bean;

import java.util.Calendar;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AppointmentValidator;

public class AppointmentValidatorTest extends TestCase {
	public void testAppointmentCorrect() throws Exception {
		AppointmentBean bean = new AppointmentBean();
		bean.setID(1L);
		bean.setRequestID(1L);
		bean.setPatientMID(1L);
		bean.setLHCPMID(1L);
		bean.setMinutes(230);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		bean.setAppointmentDate(tomorrow.getTime());
		new AppointmentValidator().validate(bean);
	}

	public void testAppointmentIncorrect() throws Exception {
		AppointmentBean bean = new AppointmentBean();
		try {
			new AppointmentValidator().validate(bean);
		fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(AppointmentValidator.AppointmentIDValidationError, e.getErrorList().get(0));
			assertEquals(AppointmentValidator.AppointmentRequestIDValidationError, e.getErrorList().get(1));
			assertEquals(AppointmentValidator.AppointmentPatientMIDValidationError, e.getErrorList().get(2));
			assertEquals(AppointmentValidator.AppointmentLHCPMIDValidationError, e.getErrorList().get(3));
			assertEquals(AppointmentValidator.AppointmentDateNullValidationError, e.getErrorList().get(4));
			assertEquals(AppointmentValidator.AppointmentMinutesFormatError, e.getErrorList().get(5));
		}
	}
	
	public void testAppointmentDateIncorrect() throws Exception {
		AppointmentBean bean = new AppointmentBean();
		bean.setID(1L);
		bean.setRequestID(1L);
		bean.setPatientMID(1L);
		bean.setLHCPMID(1L);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, -1);
		bean.setAppointmentDate(tomorrow.getTime());
		try {
			new AppointmentValidator().validate(bean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(AppointmentValidator.AppointmentDatePastValidationError, e.getErrorList().get(0));
		}
	}
}
