package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CODBean;

public class CODBeanTest extends TestCase {
	
	private CODBean bean;

	protected void setUp() throws Exception {
		super.setUp();
		bean = new CODBean();
	}

	public void testSetDiagnosisName() {
		String expected = "Some Diagnosis";
		bean.setDiagnosisName(expected);
		String actual = bean.getDiagnosisName();
		assertEquals("CODBean.setDiagnosisName() failed to set the diagnosis name properly.", expected, actual);
	}

	public void testSetIcdCode() {
		String expected = "Some Code";
		bean.setIcdCode(expected);
		String actual = bean.getIcdCode();
		assertEquals("CODBean.setIcdCode() failed to set the icd code properly.", expected, actual);
	}

	public void testSetTotal() {
		int expected = 100;
		bean.setTotal(expected);
		int actual = bean.getTotal();
		assertEquals("CODBean.setTotal() failed to set the total properly.", expected, actual);
	}

}
