package edu.ncsu.csc.itrust.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class ProfilePhotoAction {

	private long loggedInMID;
	private DAOFactory factory;

	public ProfilePhotoAction(DAOFactory factory, long loggedInMID) {
		this.factory = factory;
		this.loggedInMID = loggedInMID;
	}

	@SuppressWarnings("unchecked")
	public String storePicture(HttpServletRequest request) throws DBException {
		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory itemFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(itemFactory);
			try {
				List fileItems = upload.parseRequest(request);
				Iterator iterator = fileItems.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					// only process the first field anyway
					if (!item.isFormField()) {
						BufferedImage bi = ImageIO.read(item.getInputStream());
						// TODO do the validation of uploading and image size here
						// TODO Modify DAOFActory to have the getter - don't use this construction
						new ProfilePhotoDAO(factory).store(loggedInMID, bi);
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
