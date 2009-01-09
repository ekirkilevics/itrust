<%@include file="/global.jsp" %>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ImageDAO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%
boolean isMultipart = ServletFileUpload.isMultipartContent(request);
String header = request.getHeader("referer");

if (isMultipart)
{
	// Create a factory for disk-based file items
	FileItemFactory factory = new DiskFileItemFactory();

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	// Parse the request
	List /* FileItem */ items = upload.parseRequest(request);
	
	//The ImageDAO will do the work of inserting this uploaded file into the images table.
	ImageDAO theDAO = DAOFactory.getProductionInstance().getImageDAO();
	
	//We actually got a list of request parts (files), but we will ignore all but the first.
	FileItem theFile = (FileItem) items.get(0);
	
	//Returned from the number of affected rows. So, if > 0 we were successful.
	int success = theDAO.insertImage(theFile.getInputStream());
	
	if (success == 0)
	{
		//If no rows affected, something went wrong, pass internal server error
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	else
	{
		//Rows were affected.
		session.setAttribute("last_image", "" + theDAO.getLastImageId());
		response.sendRedirect(header);
	}
}
else
{
	//This is not a multipart request form, so the user is doing something tricky.
	response.sendError(HttpServletResponse.SC_BAD_REQUEST);
}
%>



