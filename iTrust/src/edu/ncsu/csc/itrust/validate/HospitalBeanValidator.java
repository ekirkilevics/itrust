package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.UpdateHospitalListAction;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates the input for hospital beans, {@link UpdateHospitalListAction}
 * 
 * @author Andy
 * 
 */
public class HospitalBeanValidator extends BeanValidator<HospitalBean> {
	public HospitalBeanValidator() {
	}

	@Override
	public void validate(HospitalBean h) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Hospital ID", h.getHospitalID(), ValidationFormat.HOSPITAL_ID,
				false));
		errorList.addIfNotNull(checkFormat("Hospital Name", h.getHospitalName(),
				ValidationFormat.HOSPITAL_NAME, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

}
