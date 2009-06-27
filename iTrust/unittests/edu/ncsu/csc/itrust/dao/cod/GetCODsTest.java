package edu.ncsu.csc.itrust.dao.cod;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataForCauseOfDeath;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;

public class GetCODsTest extends TestCase {
//	private DAOFactory factory = TestDAOFactory.getTestInstance();
//	private CODDAO dao = factory.getCODDAO();
//	private CODDAO evilDAO = EvilDAOFactory.getEvilInstance().getCODDAO();
	TestDataForCauseOfDeath gen = new TestDataForCauseOfDeath();

	@Override
	protected void setUp() throws Exception {
		new TestDataGenerator().clearAllTables();
		gen.insertAllData();
	}
	
	// Andy disabled these tests on 05/21/2008 because he decided the entire feature needs rewriting

	public void testGetCODsAllPatients() throws DBException {
//		List<CODBean> list = dao.getCODsAllPatients("2001-01-01", "2007-09-18");
//		assertTrue(list.size() == 3);
//		CODBean first = list.get(0);
//		assertTrue(first.getIcdCode().equals("84.5"));
//		assertTrue(first.getTotal() == 8);
	}

	public void testGetCODsMale() throws DBException {
//		dao.setGender(Gender.Male);
//		assertEquals(Gender.Male, dao.getGender());
//		List<CODBean> list = dao.getCODsAllPatients("2001-01-01", "2007-09-18");
//		assertTrue(list.size() == 3);
//		CODBean first = list.get(0);
//		assertTrue(first.getIcdCode().equals("84.5"));
//		assertTrue(first.getTotal() == 4);
	}

	public void testGetCODsAllPatients12() throws DBException {
//		List<CODBean> list = dao.getCODsMyPatients(9000000011l, "2001-01-01", "2007-12-31");
//		assertEquals(3, list.size());
//		CODBean first = list.get(0);
//		assertEquals("84.5", first.getIcdCode());
//		assertTrue(first.getTotal() == 6);

	}

	public void testGetCODsFemale() throws DBException {
//		dao.setGender(Gender.Female);
//		List<CODBean> list = dao.getCODsMyPatients(9000000011l, "2001-01-01", "2007-09-18");
//		assertTrue(list.size() == 3);
//		CODBean first = list.get(0);
//		assertTrue(first.getIcdCode().equals("84.5"));
//		assertTrue(first.getTotal() == 3);
	}

	public void testGetCODsAllPatientsEvil() throws Exception {
//		try {
//			evilDAO.getCODsAllPatients("2001-01-01", "2007-09-18");
//			fail("DBException should have been thrown");
//		} catch (DBException e) {
//			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
//		}
	}

	public void testGetCODsMyPatientsEvil() throws Exception {
//		try {
//			evilDAO.getCODsMyPatients(9000000011l, "2001-01-01", "2007-09-18");
//			fail("DBException should have been thrown");
//		} catch (DBException e) {
//			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
//		}
	}
}
