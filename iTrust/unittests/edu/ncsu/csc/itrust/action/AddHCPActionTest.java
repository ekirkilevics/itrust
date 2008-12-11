/**
 * Tests for AddHCPAction
 */

package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.Role;

public class AddHCPActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddHCPAction action;

	
	/**
	 * Sets up defaults
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
		gen.hcp0();
		action = new AddHCPAction(factory, 9000000000L);
	}

	/**
	 * Tests adding a new HCP
	 * @throws Exception
	 */
	public void testAddHCP() throws Exception {
		PersonnelBean p = new PersonnelBean();
		p.setFirstName("Cosmo");
		p.setLastName("Kramer");
		p.setEmail("cosmo@kramer.com");
		p.setRole(Role.HCP);
		long newMID = action.add(p);
		assertEquals(p.getMID(), newMID);
		}
	
}
