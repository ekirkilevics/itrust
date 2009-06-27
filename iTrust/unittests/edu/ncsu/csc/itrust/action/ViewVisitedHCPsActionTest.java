package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.ViewVisitedHCPsAction;
import edu.ncsu.csc.itrust.beans.HCPVisitBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.exception.iTrustException;
import java.util.List;
import junit.framework.TestCase;

public class ViewVisitedHCPsActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private ViewVisitedHCPsAction action;
	private ViewVisitedHCPsAction action2;

	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
		action = new ViewVisitedHCPsAction(factory, 2L);
		action2 = new ViewVisitedHCPsAction(factory, 1L);
	}

	public void testGetVisitedHCPsRed() {
		List<HCPVisitBean> beans = action.getVisitedHCPs();
		

		assertEquals("Jason Frankenstein", beans.get(0).getHCPName());
		assertEquals("Kelly Doctor", beans.get(1).getHCPName());
		assertEquals("Gandalf Stormcrow", beans.get(2).getHCPName());
	}

	public void testGetVisitedHCPsRed2(){
	List<HCPVisitBean> beans = action2.getVisitedHCPs();

		assertEquals("Kelly Doctor", beans.get(0).getHCPName());
		assertEquals("Gandalf Stormcrow", beans.get(1).getHCPName());
	}

	public void testDeclareHCP() {
		
		try {
			action.declareHCP("Kelly Doctor");
		}
		catch (iTrustException ie) {
			fail("should have been successfull");
		}
		
		/*try {
			action.declareHCP("Kelly Doctor");
			fail("should have thrown exeception");
		}
		catch (iTrustException ie) {
			System.out.println(ie.getMessage());
		}
		*/
		try {
			action.undeclareHCP("Kelly Doctor");
		}
		catch (iTrustException ie) {
			fail("should have been successfull");
		}
	}
	
	public void testCheckDeclared() {
		assertEquals(false, action.checkDeclared(0));
		assertEquals(true, action.checkDeclared(9000000003L));
	}
	
	public void testFilterHCPList() {
		List<PersonnelBean> beans = action.filterHCPList("Frank", "pediatrician", "");
		
		for (PersonnelBean bean: beans) {
			assertEquals("Lauren Frankenstein", bean.getFullName());
		}
	}
}
