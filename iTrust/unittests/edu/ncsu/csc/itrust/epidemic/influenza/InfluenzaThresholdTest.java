package edu.ncsu.csc.itrust.epidemic.influenza;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class InfluenzaThresholdTest extends TestCase {
	private MockInfluenzaDetector detector;

	public void testThreshold() throws Exception {
		new TestDataGenerator().clearAllTables();
		detector = new MockInfluenzaDetector(TestDAOFactory.getTestInstance(), "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("06/27/2007"), 2);
		// try week 0
		assertEquals(73.75, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("01/04/1998")), 0.01);
		// try week 1
		assertEquals(74.37, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("01/11/1998")), 0.01);
		// try negative week
		assertEquals(59.66, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("01/04/1997")), 0.01);
		// very small, will be used for acceptance testing this
		assertEquals(3.91, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("01/12/1993")), 0.01);
		// very small, will be used for acceptance testing this
		assertEquals(4.41, detector.getThreshold(new SimpleDateFormat("MM/dd/yyyy").parse("01/20/1993")), 0.01);
	}
}
