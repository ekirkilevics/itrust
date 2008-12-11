package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;

public class PersonnelBeanTest extends TestCase {
	
	public void testPersonnelBeanSecurity() {
		PersonnelBean p = new PersonnelBean();
		p.setPassword("password");
		p.setConfirmPassword("confirm");
		p.setSecurityQuestion("Question");
		p.setSecurityAnswer("Answer");
		
		assertEquals("confirm", p.getConfirmPassword());
		assertEquals("password", p.getPassword());
		assertEquals("Question", p.getSecurityQuestion());
		assertEquals("Answer", p.getSecurityAnswer());
	}

}
