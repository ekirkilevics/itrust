package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.beans.UserPrefsBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * The validator used by {@link UserPrefsAction}. Only checks first name, last name, and email
 * 
 * @author Andy
 * 
 */
public class UserPrefsValidator extends BeanValidator<UserPrefsBean> {
	
	/**
	 * The default constructor.
	 */
	public UserPrefsValidator() {
	}

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(UserPrefsBean p) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("ThemeColor", p.getThemeColor(), ValidationFormat.THEME_COLOR, false));
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
