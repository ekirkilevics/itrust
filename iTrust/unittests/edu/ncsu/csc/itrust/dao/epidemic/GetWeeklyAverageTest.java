package edu.ncsu.csc.itrust.dao.epidemic;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.EpidemicDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetWeeklyAverageTest extends TestCase {
	private EpidemicDAO epDAO = TestDAOFactory.getTestInstance().getEpidemicDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemic();
	}

	// The data in epidemic.sql have been chosen so that each part of the query is enforced
	public void testAverage() throws Exception {
		assertEquals(2.5, epDAO.getWeeklyAverage(84.49, 84.51, "27607", new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/27/2008")));
		// only have one year to look at
		assertEquals(2.0, epDAO.getWeeklyAverage(84.49, 84.51, "27600", new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/27/2007")));
		// 3 here all from one year
		assertEquals(3.0, epDAO.getWeeklyAverage(84.0, 85.0, "27600", new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/09/2008")));
		// Same as above, only counting 84 to 84.49
		assertEquals(2.0, epDAO.getWeeklyAverage(84.0, 84.49, "27600", new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/09/2008")));
		// 3 here all from one year, but it's this year
		assertEquals(0.0, epDAO.getWeeklyAverage(84.0, 85.0, "27600", new SimpleDateFormat("MM/dd/yyyy")
				.parse("06/09/2007")));
	}
}
