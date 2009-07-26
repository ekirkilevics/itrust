
package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import org.apache.commons.validator.*;


public class MailValidator extends EmailValidator{
	
	public MailValidator(){
		
	}
	public boolean validateEmail(String email) throws FormValidationException {
		MailValidator val = new MailValidator();
	
		return val.isValid(email);
		
	}

}
 
