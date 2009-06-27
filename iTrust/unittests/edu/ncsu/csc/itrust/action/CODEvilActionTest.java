package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class CODEvilActionTest extends TestCase {

	private CODAction action;

	public void testConstructEvil() {
		try {
			action = new CODAction(EvilDAOFactory.getEvilInstance(), 9000000000L);
			fail("DBException should have been thrown");
		} catch (iTrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
	}

	public void testGetAllCODsEvil() throws Exception {
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		action = new CODAction(new EvilDAOFactory(1), 9000000000l);
		try {
			action.getAllCODs();
			fail("DBException should have been thrown");
		} catch (iTrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
	}
	
	public void testGetMyCODsEvil() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		action = new CODAction(new EvilDAOFactory(1), 9000000000L);
		try {
			action.getMyCODs();
			fail("DBException should have been thrown");
		} catch (iTrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
	}
}
