package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.UpdateNDCodeListAction;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates ND code beans, from {@link UpdateNDCodeListAction}
 * 
 * @author Andy
 * 
 */
public class MedicationBeanValidator extends BeanValidator<MedicationBean> {
	public MedicationBeanValidator() {
	}

	@Override
	public void validate(MedicationBean m) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("ND Code", m.getNDCode(), ValidationFormat.ND, false));
		errorList.addIfNotNull(checkFormat("Description", m.getDescription(),
				ValidationFormat.ND_CODE_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
