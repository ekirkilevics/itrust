package edu.ncsu.csc.itrust.dao.officevisit;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * 
 *
 */

public class GetAllOfficeVisitsTest extends TestCase {
	private OfficeVisitDAO ovDAO = TestDAOFactory.getTestInstance().getOfficeVisitDAO();

	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient1();
		gen.patient2();
	}

	public void testVisitsFor2() throws Exception {
		List<OfficeVisitBean> visits = ovDAO.getAllOfficeVisits(2L);
		assertEquals(9, visits.size());
		assertEquals(955, visits.get(0).getID());
		assertEquals(9000000000L, visits.get(0).getHcpID());
		assertEquals(2L, visits.get(0).getPatientID());
		assertEquals("1", visits.get(0).getHospitalID());
		assertEquals("Yet another office visit.", visits.get(0).getNotes());
		//Note that that "all office visits" doesn't return the procedures, etc. since it's not needed (yet)
		assertEquals(0, visits.get(0).getDiagnoses().size());
		assertEquals(0, visits.get(0).getProcedures().size());
		assertEquals(0, visits.get(0).getProcedures().size());
		//Now check the order
		assertEquals(952L, visits.get(1).getID());
		assertEquals(959L, visits.get(2).getID());
	}
	
	public void testVisitsFor1() throws Exception {
		List<OfficeVisitBean> visits = ovDAO.getAllOfficeVisits(1L);
		assertEquals(1, visits.size());
		
	}
	public void testVisitsForHCP0() throws Exception {
		List<OfficeVisitBean> visits = ovDAO.getAllOfficeVisitsForLHCP(9000000000L);
		assertEquals(10, visits.size());
		
	}
	
	public void testGetAllVisitsWithNoSurvey() throws Exception {
		List<OfficeVisitBean> visits = ovDAO.getOfficeVisitsWithNoSurvey(2L);
		assertEquals(7, visits.size());		
	}

}
