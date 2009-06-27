package edu.ncsu.csc.itrust.dao.epidemic;

import java.text.SimpleDateFormat;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisCount;
import edu.ncsu.csc.itrust.dao.mysql.EpidemicDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetDiagnosisTrendsTest extends TestCase {
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
	public void testGetDiagnosesNC() throws Exception {
		List<DiagnosisCount> diagCount = epDAO.getDiagnosisCounts(84.0, 85.0, "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("06/09/2007"), 1);
		assertEquals(1, diagCount.size());
		assertEquals(3, diagCount.get(0).getNumInRegion());
		assertEquals(4, diagCount.get(0).getNumInState());
		assertEquals(5, diagCount.get(0).getNumInTotal());
	}

	public void testGetDiagnosesPATwoWeeksBack() throws Exception {
		// Yes, this is wrong data because 27607 is not in PA, but we're testing here...
		List<DiagnosisCount> diagCount = epDAO.getDiagnosisCounts(487.4, 487.5, "27607", State.PA,
				new SimpleDateFormat("MM/dd/yyyy").parse("06/17/2007"), 2);
		assertEquals(2, diagCount.size());
		assertEquals(0, diagCount.get(0).getNumInRegion());
		assertEquals(1, diagCount.get(0).getNumInState());
		assertEquals(1, diagCount.get(0).getNumInTotal());
		// Most recent week had nothing
		assertEquals(0, diagCount.get(1).getNumInRegion());
		assertEquals(0, diagCount.get(1).getNumInState());
		assertEquals(0, diagCount.get(1).getNumInTotal());
	}

	public void testNothingFourWeeksBack() throws Exception {
		List<DiagnosisCount> diagCount = epDAO.getDiagnosisCounts(0.5, 0.6, "Nowhere", State.MI, new SimpleDateFormat(
				"MM/dd/yyyy").parse("06/17/2000"), 4);
		assertEquals(4, diagCount.size());
		for (int i = 0; i < 4; i++) {
			assertEquals(0, diagCount.get(i).getNumInRegion());
			assertEquals(0, diagCount.get(i).getNumInState());
			assertEquals(0, diagCount.get(i).getNumInTotal());
		}
	}
}
