<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory, edu.ncsu.csc.itrust.dao.mysql.ImageDAO, java.io.*"%><%

try
{
	ImageDAO id = DAOFactory.getProductionInstance().getImageDAO();
	
	int imgId = Integer.parseInt(request.getParameter("img_id"));
	
	InputStream sImage = id.getImageWithId(imgId);
	
	byte[] buff	  = new byte[32*1024];
	int len=0;
	
	OutputStream os = response.getOutputStream();
	
	while ((len=sImage.read( buff ))!=-1 ) {
		os.write(buff,0,len);
	}
}
catch (Exception e)
{
	response.reset();
	response.sendError(HttpServletResponse.SC_NOT_FOUND);
}


%>