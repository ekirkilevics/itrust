package edu.ncsu.csc.itrust.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.EditOfficeVisitValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class EditOfficeVisitValidatorTest extends TestCase {
	public void testEditOfficeVisitAllCorrect() throws Exception {
		EditOfficeVisitForm form = new EditOfficeVisitForm();
		form.setHcpID("99");
		form.setHospitalID("9840");
		form.setNotes("");
		form.setPatientID("309");
		form.setVisitDate("09/09/1982");
		form.setStartDate("09/09/1982");
		form.setEndDate("09/09/1982");
		form.setInstructions("New instructions");
		form.setDosage("5");
		form.setAddDiagID(null);// Can always be null - optional, validated in the Action class
		form.setAddMedID(null);
		form.setAddProcID(null);
		form.setRemoveDiagID(null);
		form.setRemoveMedID(null);
		form.setRemoveProcID(null);
		new EditOfficeVisitValidator(true).validate(form);
	}

	public void testPatientAllErrors() throws Exception {
		EditOfficeVisitForm form = new EditOfficeVisitForm();
		form.setHcpID("99L");
		form.setHospitalID("-9840");
		form.setNotes("Some fun notes**");
		form.setPatientID("a309");
		form.setVisitDate("09.09.1982");
		form.setStartDate("09/09/1982a");
		form.setEndDate("09/09/1982 ");
		form.setInstructions("New instructions_)#_$@_");
		form.setDosage("5a");
		try {
			new EditOfficeVisitValidator(true).validate(form);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("HCP ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(0));
			assertEquals("Hospital ID: " + ValidationFormat.HOSPITAL_ID.getDescription(), e.getErrorList().get(1));
			assertEquals("Notes: " + ValidationFormat.NOTES.getDescription(), e.getErrorList().get(2));
			assertEquals("Patient ID: " + ValidationFormat.MID.getDescription(), e.getErrorList().get(3));
			assertEquals("Visit Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(4));
			assertEquals("Start Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(5));
			assertEquals("End Date: " + ValidationFormat.DATE.getDescription(), e.getErrorList().get(6));
			assertEquals("Instructions: " + ValidationFormat.NOTES.getDescription(), e.getErrorList().get(7));
			assertEquals("Dosage must be an integer in [0,9999]", e.getErrorList().get(8));
			assertEquals("number of errors", 9, e.getErrorList().size());
		}
	}
}
