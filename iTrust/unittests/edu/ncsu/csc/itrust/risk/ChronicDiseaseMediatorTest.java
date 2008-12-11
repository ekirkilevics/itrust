package edu.ncsu.csc.itrust.risk;

import java.io.PrintStream;
import java.util.List;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.MockSystemOut;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ChronicDiseaseMediatorTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	public void testPatient2() throws Exception {
		gen.patient2();
		List<RiskChecker> list = new ChronicDiseaseMediator(factory, 2L).getDiseaseAtRisk();
		assertEquals(2, list.size());
		assertEquals("Heart Disease", list.get(0).getName());
		assertEquals("Type 2 Diabetes", list.get(1).getName());
	}

	public void testNoRecords() throws Exception {
		// TODO I don't like the error handling here - can we throw an exception and handle in the Action?
		// For now, this will catch the System.err output
		MockSystemOut mockOut = new MockSystemOut();
		PrintStream realErr = System.err;
		System.setErr(mockOut);
		new ChronicDiseaseMediator(factory, 2L);
		System.setErr(realErr);
		assertEquals("No records exist, creating new ones.\n", mockOut.getConsole());
	}
}
