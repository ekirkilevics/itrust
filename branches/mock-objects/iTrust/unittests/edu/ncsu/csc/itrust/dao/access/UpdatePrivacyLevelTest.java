package edu.ncsu.csc.itrust.dao.access;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class UpdatePrivacyLevelTest extends TestCase {
	private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testUpdatePrivacyLevelWithAccess() throws Exception {
		List<DiagnosisBean> diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
		DiagnosisBean d = new DiagnosisBean();
		d.setOvDiagnosisID(diagnoses.get(1).getOvDiagnosisID());
		diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
	}

	public void testUpdatePrivacyLevelWithoutAccess() throws Exception {
		List<DiagnosisBean> diagnoses = getDiagnoses();
		DiagnosisBean d = new DiagnosisBean();
		d.setOvDiagnosisID(diagnoses.get(0).getOvDiagnosisID());
		d.setDescription("My Description");
		d.setICDCode("79.3");
		diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
	}

	private List<DiagnosisBean> getDiagnoses() throws DBException {
		List<DiagnosisBean> diagnoses = ovDAO.getDiagnoses(960);
		Collections.sort(diagnoses, new Comparator<DiagnosisBean>() {
			public int compare(DiagnosisBean o1, DiagnosisBean o2) {
				return Long.valueOf(o1.getOvDiagnosisID()).compareTo(Long.valueOf(o2.getOvDiagnosisID()));
			}
		});
		return diagnoses;
	}
}
