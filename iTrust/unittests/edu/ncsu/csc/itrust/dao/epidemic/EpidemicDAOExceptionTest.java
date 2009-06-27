package edu.ncsu.csc.itrust.dao.epidemic;

import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.EpidemicDAO;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class EpidemicDAOExceptionTest extends TestCase {
	private EpidemicDAO evilDAO = EvilDAOFactory.getEvilInstance().getEpidemicDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testGetDiagnosisCountsException() throws Exception {
		try {
			evilDAO.getDiagnosisCounts(0.0, 0.0, "", State.AK, new Date(), 0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetWeeklyAverageException() throws Exception {
		try {
			evilDAO.getWeeklyAverage(0.0, 0.0, "", new Date());
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
