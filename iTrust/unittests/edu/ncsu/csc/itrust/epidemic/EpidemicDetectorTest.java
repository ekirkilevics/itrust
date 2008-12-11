package edu.ncsu.csc.itrust.epidemic;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * The goal of this test is to test the EpidemicDetector abstract class (hence the simple subclass)
 * @author Andy
 * 
 */
public class EpidemicDetectorTest extends TestCase {
	private MockEpidemicDetector detector;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemic();
	}

	public void testThresholds() throws Exception {
		detector = new MockEpidemicDetector(TestDAOFactory.getTestInstance(), "27607", State.NC,
				new SimpleDateFormat("MM/dd/yyyy").parse("06/25/2007"), 4);
		detector.setThresh(10); // nothing goes over the threshold
		assertEquals("", detector.checkForEpidemic());
		detector.setThresh(1); // two weeks in a row go over the threshold
		assertEquals("Epidemic detected for weeks of: 06/04/2007 to 06/18/2007", detector.checkForEpidemic());
	}

	public void testOnlyOneWeek() throws Exception {
		detector = new MockEpidemicDetector(TestDAOFactory.getTestInstance(),  "27607", State.NC,
				new SimpleDateFormat("MM/dd/yyyy").parse("06/16/2007"), 8);
		assertEquals("", detector.checkForEpidemic()); // one week over, but not two
	}

	public void testThreeWeeksOverlap() throws Exception {
		detector = new MockEpidemicDetector(TestDAOFactory.getTestInstance(),  "27607", State.NC,
				new SimpleDateFormat("MM/dd/yyyy").parse("06/27/2007"), 4);
		detector.setThresh(10); // nothing goes over the threshold
		assertEquals("", detector.checkForEpidemic());
		detector.setThresh(1); // two weeks in a row go over the threshold
		assertEquals("Epidemic detected for weeks of: 06/06/2007 to 06/20/2007, 06/13/2007 to 06/27/2007", detector
				.checkForEpidemic());
	}
}
