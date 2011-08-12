package edu.ncsu.csc.itrust.dao.officevisit;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Test client diagnoses
 * @author David White
 * @ author Nazaire Gnassounou
 *
 */
public class OVDiagnosesTest extends TestCase {
	//private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();
	private DiagnosesDAO diagDAO = TestDAOFactory.getTestInstance().getDiagnosesDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.officeVisit1();
	}

	public void testAddRemoveOneOVDiagnosis() throws Exception {
		assertEquals("no current diagnoses on office vist 1", 0, diagDAO.getList(1).size());
		DiagnosisBean bean = new DiagnosisBean();
		bean.setICDCode("250.1");
		bean.setVisitID(1);
		long ovDID = diagDAO.add(bean);
		List<DiagnosisBean> diagnoses = diagDAO.getList(1);
		assertEquals("now there's 1", 1, diagnoses.size());
		assertEquals("test the description", "Diabetes with ketoacidosis", diagnoses.get(0).getDescription());
		diagDAO.remove(ovDID);
		assertEquals("now there's none", 0, diagDAO.getList(1).size());
	}

	public void testAddBadDiagnosis() throws Exception {
		DiagnosisBean bean = new DiagnosisBean();
		bean.setVisitID(-1);
		bean.setICDCode(null);
		try {
			diagDAO.add(bean);
			fail("expected an exception");
		} catch (DBException e) {
			
		}
	}
	public void testEditBadDiagnosis() throws Exception {
		DiagnosisBean bean = new DiagnosisBean();
		bean.setVisitID(-1);
		bean.setICDCode(null);
		try {
			diagDAO.edit(bean);
			fail("expected an exception");
		} catch (DBException e) {
			
		}
	}
}
