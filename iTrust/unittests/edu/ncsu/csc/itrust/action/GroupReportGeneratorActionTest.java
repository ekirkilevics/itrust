package edu.ncsu.csc.itrust.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.report.DemographicReportFilter;
import edu.ncsu.csc.itrust.report.PersonnelReportFilter;
import edu.ncsu.csc.itrust.report.ReportFilter;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GroupReportGeneratorActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private GroupReportGeneratorAction gpga;

	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGenerateReport(){
		List<ReportFilter> filters = new ArrayList<ReportFilter>();
		filters.add(new DemographicReportFilter(DemographicReportFilter.filterTypeFromString("DEACTIVATED"),"exclude",factory));
		filters.add(new PersonnelReportFilter(PersonnelReportFilter.filterTypeFromString("DLHCP"),"Gandalf Stormcrow",factory));
		gpga = new GroupReportGeneratorAction(factory, filters);
		gpga.generateReport();
		assertEquals(2, gpga.getReportFilterTypes().size());
		assertEquals(2, gpga.getReportFilterValues().size());
		assertEquals("DEACTIVATED", gpga.getReportFilterTypes().get(0).toString());
		assertEquals("exclude", gpga.getReportFilterValues().get(0));
		assertEquals("DECLARED HCP", gpga.getReportFilterTypes().get(1).toString());
		assertEquals("Gandalf Stormcrow", gpga.getReportFilterValues().get(1));
		int deactivatedIndex = gpga.getReportHeaders().indexOf("DEACTIVATED");
		int DHCPIndex = gpga.getReportHeaders().indexOf("DECLARED HCP");
		for(int i = 0; i < gpga.getReportData().size(); i++){
			assertEquals("", gpga.getReportData().get(i).get(deactivatedIndex));
			assertEquals("Gandalf Stormcrow\n", gpga.getReportData().get(i).get(DHCPIndex));
		}
	}
}
