package edu.ncsu.csc.itrust.action;

import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;

public class ProfilePhotoActionTest extends TestCase {

	private ProfilePhotoAction action;
	private IMocksControl ctrl;
	private DAOFactory mockDAOFactory;
	private ServletFileUpload mockUpload;
	private ProfilePhotoDAO mockDAO;
	private FileItem mockItem;

	// This class uses an advanced concept not taught in CSC326 at NCSU called Mock Objects
	// Feel free to take a look at how this works, but know that you will not need to know mock objects
	// to do nearly everything in iTrust
	//
	// This class uses the EasyMock library to manage the mock objects. http://easymock.org/

	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		mockDAOFactory = ctrl.createMock(DAOFactory.class);
		mockUpload = ctrl.createMock(ServletFileUpload.class);
		mockDAO = ctrl.createMock(ProfilePhotoDAO.class);
		mockItem = ctrl.createMock(FileItem.class);
		action = new ProfilePhotoAction(mockDAOFactory, 1, mockUpload);
	}

	public void testHappyPath() throws Exception {
		HttpServletRequest request = ctrl.createMock(HttpServletRequest.class);
		InputStream photoStream = ProfilePhotoDAO.class.getResourceAsStream("defaultProfilePhoto.jpg");

		expect(request.getMethod()).andReturn("post");
		expect(request.getContentType()).andReturn("multipart/").anyTimes();
		expect(request.getCharacterEncoding()).andReturn("").anyTimes();
		expect(mockItem.isFormField()).andReturn(false).anyTimes();
		expect(mockUpload.parseRequest(request)).andReturn(Arrays.asList(mockItem));
		expect(mockItem.getInputStream()).andReturn(photoStream);
		expect(mockDAOFactory.getProfilePhotoDAO()).andReturn(mockDAO);
		expect(mockDAO.store(anyLong(), (BufferedImage) anyObject())).andReturn(1);
		ctrl.replay();

		assertEquals("Picture stored successfully", action.storePicture(request));

		ctrl.verify();
	}

	public void testUploadException() throws Exception {
		HttpServletRequest request = ctrl.createMock(HttpServletRequest.class);

		expect(request.getMethod()).andReturn("post");
		expect(request.getContentType()).andReturn("multipart/").anyTimes();
		expect(request.getCharacterEncoding()).andReturn("").anyTimes();
		expect(mockItem.isFormField()).andReturn(false).anyTimes();
		expect(mockUpload.parseRequest(request)).andThrow(new FileUploadException("Testing"));
		ctrl.replay();

		assertEquals("Error uploading file - please try again", action.storePicture(request));

		ctrl.verify();
	}

	public void testIOException() throws Exception {
		HttpServletRequest request = ctrl.createMock(HttpServletRequest.class);

		expect(request.getMethod()).andReturn("post");
		expect(request.getContentType()).andReturn("multipart/").anyTimes();
		expect(request.getCharacterEncoding()).andReturn("").anyTimes();
		expect(mockItem.isFormField()).andReturn(false).anyTimes();
		expect(mockUpload.parseRequest(request)).andReturn(Arrays.asList(mockItem));
		expect(mockItem.getInputStream()).andThrow(new IOException());
		ctrl.replay();

		assertEquals("Error uploading file - please try again", action.storePicture(request));

		ctrl.verify();
	}

	public void testNotMultipart() throws Exception {
		HttpServletRequest request = ctrl.createMock(HttpServletRequest.class);

		expect(request.getMethod()).andReturn("post");
		expect(request.getContentType()).andReturn("notmultipart/").anyTimes();
		ctrl.replay();

		assertEquals("Error uploading file - please try again", action.storePicture(request));

		ctrl.verify();
	}

	public void testNormalConstructorNoException() throws Exception {
		new ProfilePhotoAction(mockDAOFactory, 2L);
	}
}
