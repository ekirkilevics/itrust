package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.SurveyResultBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validator for zip code that is entered when a user searches for HCP survey results.
 * @author Kathryn
 *
 */
public class SurveySearchValidator extends BeanValidator<SurveyResultBean>{

	@Override
	public void validate(SurveyResultBean bean) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Zip Code", bean.getHCPzip(), ValidationFormat.ZIPCODE, false));
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
