package edu.ncsu.csc.itrust.dao.personnel;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.enums.Role;

public class AddPersonnelTest extends TestCase {
	PersonnelDAO personnelDAO = TestDAOFactory.getTestInstance().getPersonnelDAO();
	
	@Override
	protected void setUp() throws Exception {
		
	}
	
	public void testAddEmptyPersonnel() throws Exception {
		long mid = personnelDAO.addEmptyPersonnel(Role.HCP);
		assertEquals(" ", personnelDAO.getName(mid));
		assertEquals("hcp", personnelDAO.getPersonnel(mid).getRole().getUserRolesString());
	}
	
	public void testAddEmptyUAP() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		long mid = personnelDAO.addEmptyPersonnel(9000000000l);
		assertEquals(" ", personnelDAO.getName(mid));
		assertEquals("uap", personnelDAO.getPersonnel(mid).getRole().getUserRolesString());
	}

	public void testAddEmptyER() throws Exception {
		long mid = personnelDAO.addEmptyPersonnel(Role.ER);
		assertEquals(" ", personnelDAO.getName(mid));
		assertEquals("er", personnelDAO.getPersonnel(mid).getRole().getUserRolesString());
	}
	
	public void testDoesNotExist() throws Exception {
		try{
			personnelDAO.getName(0L);
			fail("exception should have been thrown");
		} catch(iTrustException e){
			assertEquals("User does not exist",e.getMessage());
		}
	}
}
