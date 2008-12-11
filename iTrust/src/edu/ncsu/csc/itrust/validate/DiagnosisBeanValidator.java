package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates an ICD9CM code
 * 
 * @author Andy
 * 
 */
public class DiagnosisBeanValidator extends BeanValidator<DiagnosisBean> {
	public DiagnosisBeanValidator() {
	}

	@Override
	public void validate(DiagnosisBean d) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("ICD9CM Code", d.getICDCode(), ValidationFormat.ICD9CM, false));
		errorList.addIfNotNull(checkFormat("Description", d.getDescription(),
				ValidationFormat.ICD_CODE_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
