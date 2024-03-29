package edu.ncsu.csc.itrust.dao.officevisit;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddOfficeVisitTest extends TestCase {
	private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient1();
		gen.officeVisit1();
	}

	public void testAddNewOfficeVisit() {
		try {
			OfficeVisitBean ovPut = new OfficeVisitBean();
			long newOVID = ovDAO.add(ovPut);
			OfficeVisitBean ovGet = ovDAO.getOfficeVisit(newOVID);
			assertEquals(newOVID, ovGet.getVisitID());
		} 
		catch (Exception e) {
			
		}
	}

	public void testGetOfficeVisit() {
		try {
			OfficeVisitBean ov = ovDAO.getOfficeVisit(1);
			assertEquals(1, ov.getVisitID());
			assertEquals(9000000000L, ov.getHcpID());
			assertEquals("Generated for Death for Patient: 1", ov.getNotes());
			assertEquals(1, ov.getPatientID());
		}
		catch(Exception e) {
			
		}
	}

	public void testGetEmptyOfficeVisit() throws Exception {
		assertNull(ovDAO.getOfficeVisit(0L));
	}
}
