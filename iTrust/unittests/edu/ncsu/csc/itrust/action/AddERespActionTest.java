/**
 * Tests for AddPatientAction
 * @author Matt Mercer
 */

package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.Role;

public class AddERespActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private AddERespAction action;
	
	
	/**
	 * Sets up defaults
	 * * @author Matt Mercer
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.admin1();
		action = new AddERespAction(factory, 9000000000L);
	}
	
	/**
	 * Tests adding a new ER
	 * * @author Matt Mercer
	 * @throws Exception
	 */
	public void testAddER() throws Exception {
		PersonnelBean person = new PersonnelBean();
		person.setRole(Role.ER);
		person.setFirstName("Para");
		person.setLastName("Medic");
		person.setEmail("Paramedic@itrust.com");
		long newMID = action.add(person);
		assertEquals(person.getMID(), newMID);		
	}
	
}
