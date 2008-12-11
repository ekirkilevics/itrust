package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates a patient bean, from {@link EditPatientAction}
 * 
 * @author Andy
 * 
 */
public class PatientValidator extends BeanValidator<PatientBean> {
	public PatientValidator() {
	}

	@Override
	public void validate(PatientBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("First name", p.getFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Last name", p.getLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Date of Birth", p.getDateOfBirthStr(), ValidationFormat.DATE,
				false));
		errorList.addIfNotNull(checkFormat("Date of Death", p.getDateOfDeathStr(), ValidationFormat.DATE,
				true));
		try {
			if (p.getDateOfDeath().before(p.getDateOfBirth()))
				errorList.addIfNotNull("Death date cannot be before birth date!");
		} catch (NullPointerException e) {
			// ignore this
		}
		boolean deathCauseNull = (null == p.getDateOfDeathStr() || p.getDateOfDeathStr().equals(""));
		errorList.addIfNotNull(checkFormat("Cause of Death", p.getCauseOfDeath(), ValidationFormat.ICD9CM,
				deathCauseNull));
		errorList.addIfNotNull(checkFormat("Email", p.getEmail(), ValidationFormat.EMAIL, false));
		errorList.addIfNotNull(checkFormat("Street Address 1", p.getStreetAddress1(),
				ValidationFormat.ADDRESS, false));
		errorList.addIfNotNull(checkFormat("Street Address 2", p.getStreetAddress2(),
				ValidationFormat.ADDRESS, true));
		errorList.addIfNotNull(checkFormat("City", p.getCity(), ValidationFormat.CITY, false));
		errorList.addIfNotNull(checkFormat("State", p.getState(), ValidationFormat.STATE, false));
		errorList.addIfNotNull(checkFormat("Zip Code", p.getZip(), ValidationFormat.ZIPCODE, false));
		errorList
				.addIfNotNull(checkFormat("Phone Number", p.getPhone(), ValidationFormat.PHONE_NUMBER, false));
		errorList.addIfNotNull(checkFormat("Emergency Contact Name", p.getEmergencyName(),
				ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Emergency Contact Phone", p.getEmergencyPhone(),
				ValidationFormat.PHONE_NUMBER, false));
		errorList.addIfNotNull(checkFormat("Insurance Company Name", p.getIcName(), ValidationFormat.NAME,
				false));
		errorList.addIfNotNull(checkFormat("Insurance Company Address 1", p.getIcAddress1(),
				ValidationFormat.ADDRESS, false));
		errorList.addIfNotNull(checkFormat("Insurance Company Address 2", p.getIcAddress2(),
				ValidationFormat.ADDRESS, true));
		errorList.addIfNotNull(checkFormat("Insurance Company City", p.getIcCity(), ValidationFormat.CITY,
				false));
		errorList.addIfNotNull(checkFormat("Insurance Company State", p.getIcState(), ValidationFormat.STATE,
				false));
		errorList.addIfNotNull(checkFormat("Insurance Company Zip", p.getIcZip(), ValidationFormat.ZIPCODE,
				false));
		errorList.addIfNotNull(checkFormat("Insurance Company Phone", p.getIcPhone(),
				ValidationFormat.PHONE_NUMBER, false));
		errorList.addIfNotNull(checkFormat("Insurance Company ID", p.getIcID(),
				ValidationFormat.INSURANCE_ID, false));
		errorList.addIfNotNull(checkFormat("Mother MID", p.getMotherMID(), ValidationFormat.MID, true));
		errorList.addIfNotNull(checkFormat("Father MID", p.getFatherMID(), ValidationFormat.MID, true));
		errorList
				.addIfNotNull(checkFormat("Topical Notes", p.getTopicalNotes(), ValidationFormat.NOTES, true));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
