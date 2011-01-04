package edu.ncsu.csc.itrust.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class ProfilePhotoAction {

	private long loggedInMID;
	private DAOFactory factory;
	private ServletFileUpload upload;

	public ProfilePhotoAction(DAOFactory factory, long loggedInMID) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
		upload = new ServletFileUpload(new DiskFileItemFactory());
	}

	// For unit testing purposes
	public ProfilePhotoAction(DAOFactory factory, long loggedInMID, ServletFileUpload upload) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
		this.upload = upload;
	}

	/**
	 * Processes a multipart reqeuest (i.e. uploads) and then parses it as an image, then storing it in the
	 * database.
	 * 
	 * @param request
	 * @return
	 * @throws DBException
	 */
	public String storePicture(HttpServletRequest request) throws DBException {
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<?> fileItems = upload.parseRequest(request);
				Iterator<?> iterator = fileItems.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					// only process the first field anyway
					if (!item.isFormField()) {
						BufferedImage bi = ImageIO.read(item.getInputStream());
						// TODO do the validation of uploading and image size here
						factory.getProfilePhotoDAO().store(loggedInMID, bi);
					}
				}
				return "Picture stored successfully";
			} catch (FileUploadException e) {
				e.printStackTrace();
				return "Error uploading file - please try again";
			} catch (IOException e) {
				e.printStackTrace();
				return "Error uploading file - please try again";
			}
		} else {
			System.err.println("Not a multi-part request");
			return "Error uploading file - please try again";
		}
	}

}
