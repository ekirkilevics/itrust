package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditPersonnelAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates a personnel bean, from {@link EditPersonnelAction}
 * 
 * @author Andy
 * 
 */
public class PersonnelValidator extends BeanValidator<PersonnelBean> {
	public PersonnelValidator() {
	}

	@Override
	public void validate(PersonnelBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("First name", p.getFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Last name", p.getLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Street Address 1", p.getStreetAddress1(),
				ValidationFormat.ADDRESS, false));
		errorList.addIfNotNull(checkFormat("Street Address 2", p.getStreetAddress2(),
				ValidationFormat.ADDRESS, true));
		errorList.addIfNotNull(checkFormat("City", p.getCity(), ValidationFormat.CITY, false));
		errorList.addIfNotNull(checkFormat("State", p.getState(), ValidationFormat.STATE, false));
		errorList.addIfNotNull(checkFormat("Zip Code", p.getZip(), ValidationFormat.ZIPCODE, false));
		errorList
				.addIfNotNull(checkFormat("Phone Number", p.getPhone(), ValidationFormat.PHONE_NUMBER, false));
		errorList
		.addIfNotNull(checkFormat("Email", p.getEmail(), ValidationFormat.EMAIL, true));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
