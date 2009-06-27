package edu.ncsu.csc.itrust.epidemic;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.State;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EpidemicChooserTest extends TestCase {

	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.epidemicMalaria();
	}

	public void testChooseMalaria() throws Exception {
		String detector = "malaria";
		EpidemicChooser ec = new EpidemicChooser();
		EpidemicDetector ed = ec.chooseEpidemic(TestDAOFactory.getTestInstance(), detector, "27607",
				State.NC, new SimpleDateFormat("MM/dd/yyyy").parse("06/17/2007"), 2);
		assertEquals(MalariaDetector.ICD_LOWER, ed.getIcdLower());
		assertEquals(MalariaDetector.ICD_UPPER, ed.getIcdUpper());
		assertEquals(MalariaDetector.ICD_LOWER, ec.getICDLower("malaria"));
		assertEquals(MalariaDetector.ICD_UPPER, ec.getICDUpper("malaria"));
	}

	public void testChooseInfluenza() throws Exception {
		String detector = "influenza";
		EpidemicChooser ec = new EpidemicChooser();
		EpidemicDetector ed = ec.chooseEpidemic(TestDAOFactory.getTestInstance(), detector, "27607",
				State.NC, new SimpleDateFormat("MM/dd/yyyy").parse("01/21/1993"), 2);
		assertEquals(InfluenzaDetector.ICD_LOWER, ed.getIcdLower());
		assertEquals(InfluenzaDetector.ICD_UPPER, ed.getIcdUpper());
		assertEquals(InfluenzaDetector.ICD_LOWER, ec.getICDLower("influenza"));
		assertEquals(InfluenzaDetector.ICD_UPPER, ec.getICDUpper("influenza"));
	}

	public void testNoneSupported() throws Exception {
		String detector = "wrong";
		EpidemicChooser ec = new EpidemicChooser();
		try {
			ec.chooseEpidemic(TestDAOFactory.getTestInstance(), detector, "wrong", null, null, -20);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Please select a diagnosis to detect", e.getErrorList().get(0));
		}
		try{
			ec.getICDLower(detector);
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Please select a diagnosis to detect", e.getErrorList().get(0));
		}
		try{
			ec.getICDUpper(detector);
		} catch (FormValidationException e) {
			assertEquals(1, e.getErrorList().size());
			assertEquals("Please select a diagnosis to detect", e.getErrorList().get(0));
		}
	}

}
