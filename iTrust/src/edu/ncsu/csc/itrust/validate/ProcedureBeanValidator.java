package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.UpdateNDCodeListAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates an ND code, from {@link UpdateNDCodeListAction}
 * 
 * @author Andy
 * 
 */
public class ProcedureBeanValidator extends BeanValidator<ProcedureBean> {
	public ProcedureBeanValidator() {
	}

	@Override
	public void validate(ProcedureBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("CPT Code", p.getCPTCode(), ValidationFormat.CPT, false));
		errorList.addIfNotNull(checkFormat("Description", p.getDescription(),
				ValidationFormat.CPT_CODE_DESCRIPTION, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
