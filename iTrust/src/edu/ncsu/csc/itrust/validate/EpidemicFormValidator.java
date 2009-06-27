package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EpidemicDetectionAction;
import edu.ncsu.csc.itrust.beans.forms.EpidemicForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Validates the form for a request in the Epidemic Detection feature, from {@link EpidemicDetectionAction}
 * 
 * @author Andy
 * 
 */
public class EpidemicFormValidator extends BeanValidator<EpidemicForm> {
	public EpidemicFormValidator() {
	}

	@Override
	public void validate(EpidemicForm f) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Date", f.getDate(), ValidationFormat.DATE, false));
		errorList.addIfNotNull(checkDetector(f.getDetector()));
		errorList.addIfNotNull(checkFormat("ICDLower", f.getIcdLower(), ValidationFormat.ICD9CM, true));
		errorList.addIfNotNull(checkFormat("ICDUpper", f.getIcdUpper(), ValidationFormat.ICD9CM, true));
		errorList.addIfNotNull(checkFormat("State", f.getState(), ValidationFormat.STATE, false));
		errorList.addIfNotNull(checkInt("Weeks Back", f.getWeeksBack(), 0, 9, false));
		errorList.addIfNotNull(checkFormat("Zip", f.getZip(), ValidationFormat.ZIPCODE, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

	private String checkDetector(String detector) {
		if ("".equals(detector) || "malaria".equals(detector) || "influenza".equals(detector))
			return "";
		else
			return "Detector must be either \"malaria\" or \"influenza\"";
	}
}
