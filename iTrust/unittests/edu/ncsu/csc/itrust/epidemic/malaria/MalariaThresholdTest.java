package edu.ncsu.csc.itrust.epidemic.malaria;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class MalariaThresholdTest extends TestCase {
	private MockMalariaDetector detector;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemic();
		detector = new MockMalariaDetector(TestDAOFactory.getTestInstance(), "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("06/25/2007"), 4);
	}

	public void testThreshold() throws Exception {
		// we're justing testing that 6/27 is getting passed and not 6/25, all other fancy tests are
		// for EpidemicDAO directly
		assertEquals(2.0, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("06/27/2007")));
	}
}
