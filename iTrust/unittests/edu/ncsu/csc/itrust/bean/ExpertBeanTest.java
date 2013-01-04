/**
 * 
 */
package edu.ncsu.csc.itrust.bean;

import java.util.ArrayList;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ExpertBean;
import edu.ncsu.csc.itrust.dao.mysql.ExpertsDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * @author Tiago Sommer Damasceno (tsdamasc)
 *
 */
public class ExpertBeanTest extends TestCase {

	private TestDataGenerator gen;
	private ExpertsDAO expertDAO;
	private ExpertsDAO expertDAONULL;
	private ExpertBean expert;
	private ArrayList<ExpertBean> list, list2;
	
	public void setup() throws Exception {
		super.setUp();
		gen=new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.expertHospitals();
		expertDAO   = TestDAOFactory.getTestInstance().getExpertsDAO();
	}

	public void testExpertDAO() {
		expertDAONULL   = TestDAOFactory.getTestInstance().getExpertsDAO();
		assertNotNull(expertDAONULL);
	}
		
	public void testExpertBeanLoader() throws Exception {
		expertDAO   = TestDAOFactory.getTestInstance().getExpertsDAO();
		list = (ArrayList<ExpertBean>) expertDAO.getTopFiveExperts("10666-6");
		list2 = (ArrayList<ExpertBean>) expertDAO.getTopFiveExperts("10763-1");
		assertEquals(3, list.size());
		assertEquals(5, list2.size());
	}
	
	public void testSmallExpertBean() {
		expert = new ExpertBean("CrazyHospital", "100");
		assertNotNull(expert);
		assertEquals("CrazyHospital", expert.getName());
		assertEquals("100", String.valueOf(expert.getFrequency()));
	}
	
	public void testBigExpertBean() {
		expert = new ExpertBean("CrazyHospital", "Address", "City", "ST", "12345-6789", 0, 0, "100", "10666-6");
		assertNotNull(expert);
		assertEquals("CrazyHospital", expert.getName());
		assertEquals("Address", expert.getAddress());
		assertEquals("City", expert.getCity());
		assertEquals("ST", expert.getState());
		assertEquals("12345-6789", expert.getZip());
		assertEquals(0.0, expert.getLat());
		assertEquals(0.0, expert.getLng());
		assertEquals("100", String.valueOf(expert.getFrequency()));
	}
}
