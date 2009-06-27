package edu.ncsu.csc.itrust.epidemic.malaria;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.epidemic.MalariaDetector;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * This is an acceptance test for malaria epidemic detection - not totally necessary, but a good
 * sanity check
 * 
 * @author Andy
 */
public class MalariaEpidemicTest extends TestCase {
	private MalariaDetector detector;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemicMalaria();
	}

	public void testMalariaEpidemic() throws Exception {
		detector = new MalariaDetector(TestDAOFactory.getTestInstance(), "27607", State.NC, new SimpleDateFormat(
				"MM/dd/yyyy").parse("06/17/2007"), 2);
		// description of the data:
		// 2005 had an average of 2 cases for both weeks
		// 2006 had an average of 3 cases for both weeks
		// 2007 has an average of 4 cases for both weeks
		// Therefore, we have an average of 2.5 for both weeks for past years
		detector.setPercentage(1.59); // just barely over the threshold
		assertEquals("Epidemic detected for weeks of: 06/03/2007 to 06/17/2007", detector.checkForEpidemic());
		detector.setPercentage(1.60); // at the threshold, no epidemic
		assertEquals("", detector.checkForEpidemic());
		detector.setPercentage(1.61); // below the threshold, no epidemic
		assertEquals("", detector.checkForEpidemic());
	}
}
