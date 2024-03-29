/**
 * Tests for AddUAPAction
 */

package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddUAPActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddUAPAction action;
	
	
	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.hcp0();
		action = new AddUAPAction(factory, 9000000000L);
	}

	/**
	 * Tests adding a new UAP
	 * @throws Exception
	 */
	public void testAddPatient() throws Exception {
		PersonnelBean p = new PersonnelBean();
		p.setFirstName("Cosmo");
		p.setLastName("Kramer");
		p.setEmail("cosmo@kramer.com");
		long newMID = action.add(p);
		assertEquals(p.getMID(), newMID);
	}
}
