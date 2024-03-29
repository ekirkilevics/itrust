package edu.ncsu.csc.itrust.dao.labprocedure;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetAllLabProceduresLOINCTest extends TestCase {
	private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();

	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.labProcedures();
	}

	public void testGetAllLabProcedures() throws Exception {
		List<LabProcedureBean> procedures = lpDAO.getAllLabProceduresLOINC(1L);
		assertEquals(3, procedures.size());
		assertEquals("10640-1", procedures.get(0).getLoinc());
		assertEquals("10666-6", procedures.get(1).getLoinc());
		assertEquals("10763-1", procedures.get(2).getLoinc());
	}

	public void testFailGetLabProcedures() throws Exception {
		try {
			lpDAO.getAllLabProceduresLOINC(0L);
			fail();
		} catch (DBException e) {
			assertEquals("PatientMID cannot be null", e.getExtendedMessage());
		}
	}

}
