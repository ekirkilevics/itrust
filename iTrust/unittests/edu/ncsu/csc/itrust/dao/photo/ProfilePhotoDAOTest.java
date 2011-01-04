package edu.ncsu.csc.itrust.dao.photo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ProfilePhotoDAOTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();
	private ProfilePhotoDAO  mydao = TestDAOFactory.getTestInstance().getProfilePhotoDAO();
	
	@Override
	protected void setUp() throws Exception {
		gen.patient1();
		gen.clearProfilePhotos();
	}	
	public void testStoreAndGetPicture()
	{
	
		try
		{
			BufferedImage bi = new BufferedImage(900, 500, BufferedImage.TYPE_3BYTE_BGR);
			assertEquals(1, mydao.store(1l, bi));
			
			BufferedImage returnedimage = mydao.get(1l);
			assertEquals(bi.getWidth(), returnedimage.getWidth());
			assertEquals(bi.getHeight(), returnedimage.getHeight());
		}
		catch (iTrustException e)
		{
			e.printStackTrace();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
	}
	
	public void testGetNullPicture()
	{
		try
		{
			mydao.get(7l);
		}
		catch (iTrustException e)
		{
			assertEquals("No photo available", e.getMessage());
		}
		catch (IOException e2)
		{
			fail("Wrong type of exception! Shoudl throw an iTrustException!");
		}
	}
	
	public void testEvilConnectionGet()
	{
		try
		{
			mydao = EvilDAOFactory.getEvilInstance().getProfilePhotoDAO();
			mydao.get(1l);
			
		}
		catch (iTrustException e)
		{
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
			fail("Wrong type of exception!");
		}
	}
	
	public void testEvilConnectionStore()
	{
		try
		{
			mydao = EvilDAOFactory.getEvilInstance().getProfilePhotoDAO();
			BufferedImage bi = new BufferedImage(900, 500, BufferedImage.TYPE_3BYTE_BGR);
			mydao.store(1l, bi);
		}
		catch (iTrustException e)
		{
			assertEquals("A database exception has occurred. Please see the log in the console for stacktrace", e.getMessage());
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
			fail("Wrong type of exception!");
		}
	}

}
