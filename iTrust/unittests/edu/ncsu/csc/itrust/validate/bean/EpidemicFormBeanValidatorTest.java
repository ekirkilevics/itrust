package edu.ncsu.csc.itrust.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.EpidemicForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.EpidemicFormValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class EpidemicFormBeanValidatorTest extends TestCase {
	private BeanValidator<EpidemicForm> validator = new EpidemicFormValidator();

	public void testAllCorrect() throws Exception {
		EpidemicForm form = new EpidemicForm();
		form.setDate("05/19/1984");
		form.setDetector("malaria");
		form.setIcdLower("250.00");
		form.setIcdUpper("250.00");
		form.setState("NC");
		form.setWeeksBack("6");
		form.setZip("27607");
		validator.validate(form);
		form.setDetector("influenza");
		validator.validate(form);
		form.setDetector("");
		validator.validate(form);
	}

	public void testPatientAllErrors() throws Exception {
		EpidemicForm form = new EpidemicForm();
		form.setDate("05/19/19840");
		form.setDetector("malaria2");
		form.setIcdLower("250.000");
		form.setIcdUpper("250.00a");
		form.setState("XYZ");
		form.setWeeksBack("6.2");
		form.setZip("276");
		try {
			validator.validate(form);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(0));
			assertEquals("Detector must be either \"malaria\" or \"influenza\"", e.getErrorList().get(1));
			assertEquals("ICDLower: " + ValidationFormat.ICD9CM.getDescription(), e.getErrorList().get(2));
			assertEquals("ICDUpper: " + ValidationFormat.ICD9CM.getDescription(), e.getErrorList().get(3));
			assertEquals("State: " + ValidationFormat.STATE.getDescription(), e.getErrorList().get(4));
			assertEquals("Weeks Back must be an integer in [0,9]", e.getErrorList().get(5));
			assertEquals("Zip: " + ValidationFormat.ZIPCODE.getDescription(), e.getErrorList().get(6));
			assertEquals("number of errors", 7, e.getErrorList().size());
		}
	}
}
