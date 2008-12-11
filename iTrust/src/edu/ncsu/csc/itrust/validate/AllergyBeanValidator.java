package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.AllergyBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator used to validate adding a new allergy in {@link EditOfficeVisitAction}
 * 
 * @author Andy
 * 
 */
public class AllergyBeanValidator extends BeanValidator<AllergyBean> {
	public AllergyBeanValidator() {
	}

	@Override
	public void validate(AllergyBean m) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Allergy Description", m.getDescription(),
				ValidationFormat.ALLERGY_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
