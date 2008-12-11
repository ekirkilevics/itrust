package edu.ncsu.csc.itrust.epidemic.influenza;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.epidemic.InfluenzaDetector;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * This is an acceptance test for malaria epidemic detection - not totally necessary, but a good
 * sanity check
 * 
 * @author Andy
 */
public class InfluenzaEpidemicTest extends TestCase {
	private InfluenzaDetector detector;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemicInfluenza();
	}

	public void testInfluenzaEpidemic() throws Exception {
		detector = new InfluenzaDetector(TestDAOFactory.getTestInstance(), "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("01/21/1993"), 2);
		// Our trig-based model is based on data from 1998. Since it's expecting ~60 cases for that
		// time, we might as well rewind a bit and calculate it for 1993 so that we don't have to
		// slow down our unit tests.
		// For 1/12/1993, we're expecting 3.91 cases of Influenza
		// For 1/20/1993, we're expecting 4.41 cases of Influenza
		assertEquals("Epidemic detected for weeks of: 01/07/1993 to 01/21/1993", detector.checkForEpidemic());
		// Now check one week later - should be nothing
		detector = new InfluenzaDetector(TestDAOFactory.getTestInstance(), "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("01/28/1993"), 2);
		assertEquals("", detector.checkForEpidemic());
	}
}
