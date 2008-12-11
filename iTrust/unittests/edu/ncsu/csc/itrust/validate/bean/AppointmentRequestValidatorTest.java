package edu.ncsu.csc.itrust.validate.bean;

import java.util.Calendar;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AppointmentRequestValidator;

public class AppointmentRequestValidatorTest  extends TestCase {
	
	public void testAppointmentRequestCorrect() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		bean.setRequestedMID(9000000000L);
		bean.setRequesterMID(2L);
		bean.setMinutes(123);
		try {
			new AppointmentRequestValidator().validate(bean);
		} catch (FormValidationException e) {
			fail("no exception should have been thrown");
		}
	}
	
	public void testAppointmentRequestFail() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		try {
			new AppointmentRequestValidator().validate(bean);
		fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(AppointmentRequestValidator.AppointmentRequesterError, e.getErrorList().get(0));
			assertEquals(AppointmentRequestValidator.AppointmentRequestedError, e.getErrorList().get(1));
			assertEquals(AppointmentRequestValidator.AppointmentRequestMinutesFormatError, e.getErrorList().get(2));
		}
	}
	
	public void testAppointmentRequestBadDates() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		bean.setRequestedMID(9000000000L);
		bean.setRequesterMID(2L);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		bean.setDate1(cal.getTime());
		bean.setDate2(cal.getTime());
		try {
			new AppointmentRequestValidator().validate(bean);
		fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(AppointmentRequestValidator.AppointmentRequestDate1Error, e.getErrorList().get(0));
			assertEquals(AppointmentRequestValidator.AppointmentRequestDate2Error, e.getErrorList().get(1));
			assertEquals(AppointmentRequestValidator.AppointmentRequestMinutesFormatError, e.getErrorList().get(2));
		}
	}
	
	public void testBadDateParse() throws Exception {
		try {
			new AppointmentRequestValidator().validateDate(AppointmentRequestBean.dateFormat, "xxx");
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(AppointmentRequestValidator.AppointmentRequestDateFormatError + AppointmentRequestBean.dateFormat, e.getErrorList().get(0));
		}
	}
	
	public void testGoodDateParse() throws Exception {
		try {
			new AppointmentRequestValidator().validateDate(AppointmentRequestBean.dateFormat, "01/01/2008 12:00");
		} catch (FormValidationException e) {
			fail("exception should not have been thrown");
		}
	}
}
