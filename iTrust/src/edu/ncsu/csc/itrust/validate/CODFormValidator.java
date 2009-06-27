package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.forms.CODForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates the form for making a request in the Cause-Of-Death feature
 * 
 * @author Andy
 * 
 */
public class CODFormValidator extends BeanValidator<CODForm> {
	@Override
	public void validate(CODForm bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		if (null == bean)
			throw new FormValidationException("Null Form!");
		errorList.addIfNotNull(checkFormat("After Date", bean.getAfterYear(), ValidationFormat.YEAR, false));
		errorList
				.addIfNotNull(checkFormat("Before Date", bean.getBeforeYear(), ValidationFormat.YEAR, false));
		errorList.addIfNotNull(checkGender("Gender", bean.getGen(), ValidationFormat.GENDERCOD, true));

		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
