package edu.ncsu.csc.itrust.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class AppointmentRequestValidator extends BeanValidator<AppointmentRequestBean> {

	public static final String AppointmentRequesterError = "Appointment Requester cannot be null";
	public static final String AppointmentRequestedError = "Either a LHCP or Hospital ID must be entered as a 10-digit number";
	public static final String AppointmentRequestDate1Error = "Date 1 must be in the future";
	public static final String AppointmentRequestDate2Error = "Date 2 must be in the future";
	public static final String AppointmentRequestDateFormatError = "Format of date must be: ";
	public static final String AppointmentRequestMinutesFormatError = "Minutes must be 1-9999";

	@Override
	public void validate(AppointmentRequestBean b) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		if (b.getRequesterMID() == 0L) 
			errorList.addIfNotNull(AppointmentRequesterError);
		
		if (b.getRequestedMID() < 999999999L || b.getRequestedMID() > 9999999999L)
			if (b.getHospitalID() == null || b.getHospitalID().equals(""))
				errorList.addIfNotNull(AppointmentRequestedError);
		try{
		if (b.getDate1() != null && b.getDate1().before(Calendar.getInstance().getTime()))
			errorList.addIfNotNull(AppointmentRequestDate1Error);
		
		if (b.getDate2() != null && b.getDate2().before(Calendar.getInstance().getTime()))
			errorList.addIfNotNull(AppointmentRequestDate2Error);
		}
		catch(ParseException e){
			System.err.println(e.getMessage());
		}
		
		if ((b.getMinutes() < 1) || (b.getMinutes() > 9999))
			errorList.addIfNotNull(AppointmentRequestMinutesFormatError);
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
	
	public void validateDate(String format, String aDate) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		try {
			new SimpleDateFormat(format).parse(aDate);
		} catch (ParseException ex) {
			errorList.addIfNotNull(AppointmentRequestDateFormatError + format);
		}
		finally{
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
		}
	}
}
