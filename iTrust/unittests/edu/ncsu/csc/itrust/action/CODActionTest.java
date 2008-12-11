package edu.ncsu.csc.itrust.action;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CODBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataForCauseOfDeath;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;

import edu.ncsu.csc.itrust.beans.forms.CODForm;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.Gender;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class CODActionTest extends TestCase {
	private TestDataForCauseOfDeath codGen = new TestDataForCauseOfDeath();
	private TestDataGenerator gen = new TestDataGenerator();
	private CODAction action;
	private CODForm fullForm;
	private CODForm emptyForm;
//	private TransactionDAO transDAO = new TransactionDAO(TestDAOFactory.getTestInstance());

	@SuppressWarnings("static-access")
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		codGen.insertAllData();		
		DAOFactory factory = TestDAOFactory.getTestInstance();
		long mid = 9000000011l;
		action = new CODAction(factory, mid);
		
		fullForm = new CODForm();
		fullForm.setAfterYear("1000");
		fullForm.setBeforeYear("3000");
		fullForm.setGen(Gender.NotSpecified); 
		fullForm.getCurrentYear();
		
		emptyForm = new CODForm();
		emptyForm.setAfterYear("1000");
		emptyForm.setBeforeYear("1000");
		emptyForm.setGen(Gender.Male);
	}
	
	public void testSetForm() throws FormValidationException, iTrustException {
		try {
			action.setForm(fullForm);
		} catch (FormValidationException fve) {
			fail("Valid form incorrectly considered invalid");
		} catch (iTrustException ie) {
			fail("Unforeseen exception encountered");
		}
		
	}
	
	public void testGetAllCODs() throws FormValidationException, iTrustException {
		action.setForm(fullForm);
		List<CODBean> cods = action.getAllCODs();
		assertNotNull(cods);
		
		action.setForm(emptyForm);
		cods = action.getAllCODs();
		assertNotNull(cods);
		assert(cods.size() == 0);
	}
	
	public void testGetMyCODs() throws Exception {
		action.setForm(fullForm);
		List<CODBean> list = action.getMyCODs();
		assertNotNull(list);
	}
	
	// Andy disabled these tests on 05/21/2008 because he decided the entire feature needs rewriting

	public void testBaseCase() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000012l);
		// CODForm form = new CODForm();
		// form.setGen(Gender.NotSpecified);
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getAllCODs();
		// assertEquals(1, theList.size());
		// CODBean first = theList.get(0);
		// assertEquals(1, first.getRank());
		// assertEquals("Malaria", first.getDiagnosisName());
		// CODBean sec = theList.get(1);
		// assertEquals(2, sec.getRank());
		// List<TransactionBean> tbs = transDAO.getAllTransactions();
		// boolean found = false;
		// while (!tbs.isEmpty() && !found) {
		// TransactionBean thisone = tbs.remove(0);
		// if (thisone.getLoggedInMID() == 9000000012l
		// && thisone.getTranactionType() == TransactionType.CAUSE_OF_DEATH)
		// found = true;
		// }
	}

	public void testGetMales() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000012l);
		// CODForm form = new CODForm();
		// form.setGen(Gender.Male);
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getAllCODs();
		// assertEquals(2, theList.size());
		// CODBean first = theList.get(0);
		// assertEquals(1, first.getRank());
		// assertEquals(4, first.getTotal());
		// CODBean sec = theList.get(1);
		// assertEquals(2, sec.getRank());
		// assertEquals(3, sec.getTotal());
	}

	public void testGetFemales() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000012l);
		// CODForm form = new CODForm();
		// form.setGen(Gender.Female);
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getAllCODs();
		// assertEquals(2, theList.size());
		// CODBean first = theList.get(0);
		// assertEquals(1, first.getRank());
		// assertEquals(4, first.getTotal());
		// CODBean sec = theList.get(1);
		// assertEquals(2, sec.getRank());
		// assertEquals(3, sec.getTotal());
	}

	public void testMyFemales() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000012l);
		// CODForm form = new CODForm();
		// form.setGen(Gender.Female);
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getMyCODs();
		// assertTrue(theList.size() == 2);
		// CODBean first = theList.get(0);
		// assertEquals(1, first.getRank());
		// CODBean sec = theList.get(1);
		// assertEquals(1, sec.getRank());
	}

	public void testMyMales() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000012l);
		// CODForm form = new CODForm();
		// form.setGen(Gender.Male);
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getMyCODs();
		// assertTrue(theList.size() == 2);
		// CODBean first = theList.get(0);
		// assertEquals(1, first.getRank());
		// CODBean sec = theList.get(1);
		// assertEquals(1, sec.getRank());

	}

	public void testMyCODsAllPatients() throws iTrustException, FormValidationException {
		// action = new CODAction(TestDAOFactory.getTestInstance(), 9000000011l);
		// CODForm form = new CODForm();
		// form.setAfterYear("2001");
		// form.setBeforeYear("2007");
		// action.setForm(form);
		// List<CODBean> theList = action.getMyCODs();
		// assertEquals(2, theList.size());
		// CODBean first = theList.get(0);
		// assertEquals("84.5", first.getIcdCode());
		// CODBean sec = theList.get(1);
		// assertEquals("250.1", sec.getIcdCode());

	}

}
