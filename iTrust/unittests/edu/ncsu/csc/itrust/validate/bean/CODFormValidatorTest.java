package edu.ncsu.csc.itrust.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.CODForm;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.CODFormValidator;

public class CODFormValidatorTest extends TestCase {
	private CODFormValidator validator = new CODFormValidator();

	public void testAllCorrect() throws Exception {
		CODForm f = new CODForm();
		f.setAfterYear("2001");
		f.setBeforeYear("2007");
		f.setGen(Gender.Male);
		validator.validate(f);
	}

	public void testNullForm()
	{
		try
		{
			validator.validate(null);
			fail();
		}
		catch (FormValidationException fe)
		{
			
		}
	}
	
	public void testOneYearMissing()
	{
		try
		{
			CODForm f = new CODForm();
			f.setAfterYear("2001");
			f.setGen(Gender.Male);
			validator.validate(f);
			fail();
		}
		catch (FormValidationException fe)
		{
			
		}
	}
}
