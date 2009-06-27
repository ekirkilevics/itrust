package edu.ncsu.csc.itrust.exception;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspWriter;

public class FormValidationException extends Exception {
	private static final long serialVersionUID = 1L;
	private ErrorList errorList;

	public FormValidationException(String... errorMessages) {
		errorList = new ErrorList();
		for (String msg : errorMessages) {
			errorList.addIfNotNull(msg);
		}
	}

	public FormValidationException(ErrorList errorList) {
		this.errorList = errorList;
	}

	public List<String> getErrorList() {
		return errorList.getMessageList();
	}

	@Override
	public String getMessage() {
		return "This form has not been validated correctly. The following field are not properly filled in: "
				+ errorList.toString();
	}

	public void printHTML(JspWriter out) throws IOException {
		out.print("<h2>Information not valid</h2><div class=\"errorList\">");
		for (String errorMessage : errorList) {
			out.print(errorMessage + "<br />");
		}
		out.print("</div>");
	}
}
