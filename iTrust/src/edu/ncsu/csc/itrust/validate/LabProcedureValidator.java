package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class LabProcedureValidator {
	
	public void validate(LabProcedureBean b) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("LOINC", b.getLoinc(), ValidationFormat.LOINC, false));
		errorList.addIfNotNull(checkFormat("Commentary", b.getCommentary(), ValidationFormat.COMMENTS, true));
		errorList.addIfNotNull(checkFormat("Results", b.getCommentary(), ValidationFormat.COMMENTS, true));
		errorList.addIfNotNull(checkFormat("Status", b.getStatus(), ValidationFormat.LAB_STATUS, false));
		errorList.addIfNotNull(checkFormat("Rights", b.getRights(), ValidationFormat.LAB_RIGHTS, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}

	protected String checkFormat(String name, String value, ValidationFormat format, boolean isNullable) {
		String errorMessage = name + ": " + format.getDescription();
		if (value == null || "".equals(value))
			return isNullable ? "" : errorMessage;
		if (format.getRegex().matcher(value).matches())
			return "";
		else
			return errorMessage;
	}
}
