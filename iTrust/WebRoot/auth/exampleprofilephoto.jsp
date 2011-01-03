<%@page import="javax.imageio.ImageIO"%>
<%@page language="java" contentType="image/jpg"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO"%>
<%@include file="/global.jsp"%>
<%
//Using this unconventional constructor to avoid modifying DAOFactory
BufferedImage bi = new ProfilePhotoDAO(DAOFactory.getProductionInstance()).get(loggedInMID);

OutputStream os = response.getOutputStream();
ImageIO.write(bi, "jpg", os);
os.close();
%>