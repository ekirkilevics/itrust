package edu.ncsu.csc.itrust.dao.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ImageDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class InsertImageTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ImageDAO myDAO = null;
	private File f = null;
	private FileInputStream fis = null;

	@Override
	protected void setUp() throws Exception {
		f = new File("WebRoot/WEB-INF/web.xml");
		fis = new FileInputStream(f);
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		myDAO = factory.getImageDAO();
	}
	
	/**
	 * Tests that web.xml, which is not an image, can
	 * be inserted and retrieved from the images table
	 * successfully.  Rather than remain reliant on a
	 * consistent set of images, we choose to use
	 * web.xml, which will always be in iTrust because
	 * iTrust is a web application.
	 * @throws Exception
	 */
	public void testInsertAndRetrieveImage() throws Exception
	{
		assertEquals(1, myDAO.insertImage(fis));
		long lastId = myDAO.getLastImageId();
		assertTrue(lastId != 0l);
		InputStream is = myDAO.getImageWithId(lastId);
		assertNotNull(is);
		is.read();
	}
	
}
