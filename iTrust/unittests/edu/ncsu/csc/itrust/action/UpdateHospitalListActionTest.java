package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class UpdateHospitalListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private UpdateHospitalListAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	private long performingAdmin = 9000000001l;

	@Override
	protected void setUp() throws Exception {
		action = new UpdateHospitalListAction(factory, performingAdmin);
		gen.clearAllTables();
		gen.admin1();
		gen.hospitals();
	}

	private String getAddHospitalSuccessString(HospitalBean proc) {
		return "Success: " + proc.getHospitalID() + " - " + proc.getHospitalName() + " added";
	}

	/**
	 * KILLS-- M FAIL: edu.ncsu.csc.itrust.action.UpdateHospitalListAction:35: changed return value (areturn)
	 */
	public void testEvilFactory() {
		action = new UpdateHospitalListAction(EvilDAOFactory.getEvilInstance(), 0l);
		HospitalBean db = new HospitalBean("2223", "ananana");

		try {
			String x = action.addHospital(db);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {

		}
	}

	/**
	 * KILLS-- M FAIL: edu.ncsu.csc.itrust.action.UpdateHospitalListAction:49: changed return value (areturn)
	 */
	public void testEvilFactory2() {
		action = new UpdateHospitalListAction(EvilDAOFactory.getEvilInstance(), 0l);
		HospitalBean db = new HospitalBean("2223", "ananana");

		try {
			String x = action.updateInformation(db);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {

		}
	}

	private void addEmpty(String id) throws Exception {
		HospitalBean hosp = new HospitalBean(id, " ");
		assertEquals(getAddHospitalSuccessString(hosp), action.addHospital(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals(" ", hosp.getHospitalName());
	}

	public void testAddHospital() throws Exception {
		String id = "9999999999";
		String name = "testAddHospital Hospital";
		HospitalBean hosp = new HospitalBean(id, name);
		assertEquals(getAddHospitalSuccessString(hosp), action.addHospital(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals(name, hosp.getHospitalName());
	}

	/**
	 * Kills: M FAIL: edu.ncsu.csc.itrust.action.UpdateHospitalListAction:27: CP[64] "added hospital " ->
	 * "___jumble___" M FAIL: edu.ncsu.csc.itrust.action.UpdateHospitalListAction:27: 0L -> 1L
	 */
	public void testAddHospital2() throws Exception {
		String id = "88888888";
		String name = "Test Hospital";
		HospitalBean hosp = new HospitalBean(id, name);
		assertEquals(getAddHospitalSuccessString(hosp), action.addHospital(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals(name, hosp.getHospitalName());
	}

	public void testAddDuplicate() throws Exception {
		String id = "0000000000";
		String name0 = "hospital 0";
		HospitalBean hosp = new HospitalBean(id, name0);
		assertEquals(getAddHospitalSuccessString(hosp), action.addHospital(hosp));
		hosp.setHospitalName("hospital 1");
		assertEquals("Error: Hospital already exists.", action.addHospital(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals(name0, hosp.getHospitalName());
	}

	public void testUpdateICDInformation0() throws Exception {
		String id = "8888888888";
		String name = "new hospital 8...";
		HospitalBean hosp = new HospitalBean(id);
		addEmpty(id);
		hosp.setHospitalName(name);
		assertEquals("Success: 1 row(s) updated", action.updateInformation(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals(name, hosp.getHospitalName());
	}

	public void testUpdateNonExistent() throws Exception {
		String id = "9999999999";
		HospitalBean hosp = new HospitalBean(id, "shouldn't be here");
		assertEquals("Error: Hospital not found.", action.updateInformation(hosp));
		assertEquals(null, factory.getHospitalsDAO().getHospital(id));
		assertEquals(9, factory.getHospitalsDAO().getAllHospitals().size());
	}
	
	public void testAddAddress() throws Exception {
		String id = "9999999999";
		HospitalBean hosp = new HospitalBean(id, "shouldn't be here", "Address", "City", "ST", "00000-0000",0,0);
		action.addHospital(hosp);
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals("Address",hosp.getHospitalAddress());
		assertEquals("City",hosp.getHospitalCity());
		assertEquals("ST",hosp.getHospitalState());
		assertEquals("00000-0000",hosp.getHospitalZip());
	}
	
	public void testUpdateAddress() throws Exception {
		String id = "8888888888";
		String name = "new hospital 8...";
		HospitalBean hosp = new HospitalBean(id, name, "Address", "City", "ST", "00000-0000",0,0);
		addEmpty(id);
		hosp.setHospitalName(name);
		assertEquals("Success: 1 row(s) updated", action.updateInformation(hosp));
		hosp = factory.getHospitalsDAO().getHospital(id);
		assertEquals("Address",hosp.getHospitalAddress());
		assertEquals("City",hosp.getHospitalCity());
		assertEquals("ST",hosp.getHospitalState());
		assertEquals("00000-0000",hosp.getHospitalZip());
	}
}
