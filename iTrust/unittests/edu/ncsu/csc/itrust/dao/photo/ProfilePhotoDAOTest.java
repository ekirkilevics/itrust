package edu.ncsu.csc.itrust.dao.photo;

import java.awt.image.BufferedImage;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ProfilePhotoDAOTest extends TestCase {
	private TestDataGenerator gen;
	private ProfilePhotoDAO mydao;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.patient1();
		gen.clearProfilePhotos();
		mydao = TestDAOFactory.getTestInstance().getProfilePhotoDAO();
	}

	public void testStoreAndGetPicture() throws Exception {
		BufferedImage bi = new BufferedImage(900, 500, BufferedImage.TYPE_3BYTE_BGR);
		assertEquals(1, mydao.store(1l, bi));

		BufferedImage returnedimage = mydao.get(1l);
		assertEquals(bi.getWidth(), returnedimage.getWidth());
		assertEquals(bi.getHeight(), returnedimage.getHeight());
	}

	public void testGetNullPicture() throws Exception {
		try {
			mydao.get(7l);
			fail("Exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("No photo available", e.getMessage());
		}
	}
}
