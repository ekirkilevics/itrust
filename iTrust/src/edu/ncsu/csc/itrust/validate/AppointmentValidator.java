package edu.ncsu.csc.itrust.validate;

import java.util.Calendar;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class AppointmentValidator extends BeanValidator<AppointmentBean> {
	
	public static final String AppointmentIDValidationError = "Appointment ID cannot be null or 0";
	public static final String AppointmentRequestIDValidationError = "Appointment Request ID cannot be null or 0";
	public static final String AppointmentPatientMIDValidationError = "Patient MID cannot be null or 0";
	public static final String AppointmentLHCPMIDValidationError = "LHCP MID cannot be null or 0";
	public static final String AppointmentDateNullValidationError = "Appointment date cannot be null";
	public static final String AppointmentDatePastValidationError = "Appointment date must be in the future";
	public static final String AppointmentMinutesFormatError = "Minutes must be 1-9999";

	@Override
	public void validate(AppointmentBean b) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		
		if (b.getID() == 0L) 
			errorList.addIfNotNull(AppointmentIDValidationError);
		if (b.getRequestID() == 0L) 
			errorList.addIfNotNull(AppointmentRequestIDValidationError);
		if (b.getPatientMID() == 0L) 
			errorList.addIfNotNull(AppointmentPatientMIDValidationError);
		if (b.getLHCPMID() == 0L) 
			errorList.addIfNotNull(AppointmentLHCPMIDValidationError);
		if (b.getAppointmentDate() == null) 
			errorList.addIfNotNull(AppointmentDateNullValidationError);
		else if (b.getAppointmentDate().before(Calendar.getInstance().getTime()))
			errorList.addIfNotNull(AppointmentDatePastValidationError);
		if ((b.getMinutes() < 1) || (b.getMinutes() > 9999))
			errorList.addIfNotNull(AppointmentMinutesFormatError);
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
