package edu.ncsu.csc.itrust.action;

import static edu.ncsu.csc.itrust.testutils.JUnitiTrustUtils.assertTransactionOnly;
import java.awt.image.BufferedImage;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.forms.EpidemicForm;
import edu.ncsu.csc.itrust.beans.forms.EpidemicReturnForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EpidemicDetectionActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private EpidemicDetectionAction action;
	private EpidemicDetectionImageAction imageAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.epidemic();
	}

	public void testCheckStandardEpidemic() throws Exception {
		action = new EpidemicDetectionAction(factory, 9000000000L);
		EpidemicForm form = new EpidemicForm();
		form.setDate("06/27/2008");
		form.setDetector("malaria");
		form.setUseEpidemic("true");
		form.setState("NC");
		EpidemicReturnForm rForm = action.execute(form);
		assertEquals("No epidemic detected", rForm.getEpidemicMessage());
		assertTransactionOnly(TransactionType.REQUEST_BIOSURVEILLANCE, 9000000000L, 0L, "");
		//then pass this to the image action
		imageAction = new EpidemicDetectionImageAction(factory);
		BufferedImage graph = imageAction.createGraph(form);
		//Assert that it's a non-trivial image
		assertEquals(500, graph.getHeight());
	}
	
	public void testGetImageQuery() throws Exception {
		action = new EpidemicDetectionAction(factory, 9000000000L);
		EpidemicForm form = new EpidemicForm();
		form.setUseEpidemic("false");
		form.setDate("06/27/2008");
		form.setState("NC");
		form.setIcdLower("");
		form.setIcdUpper("");
		try {
			action.execute(form);
			fail("Expected FormValidationException");
		} catch (FormValidationException fve) {
			//Good
		} catch (Exception e) {
			fail("Incorrect exception: " + e.toString());
		}
	}
		public void testGetImageQuery1() throws Exception {
			action = new EpidemicDetectionAction(factory, 9000000000L);
			EpidemicForm form = new EpidemicForm();
			form.setUseEpidemic("false");
			form.setDate("06/27/2008");
			form.setState("NC");
			form.setIcdLower("1");
			form.setIcdUpper("2");
			try {
				action.execute(form);
			} catch (Exception e) {
				fail("No exception expected: " + e.toString());
			}
	}
}
