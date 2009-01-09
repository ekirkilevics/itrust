<%@page errorPage="/auth/exceptionHandler.jsp"%>

<!-- IMPORTS GO HERE -->

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Demo Image";
%>

<%@include file="/header.jsp" %>
<h1>Images</h1>
This page is to illustrate the mechanisms for uploading a file.  Developers
should view the JSP source to discover how to handle image uploading in JSP.

<!-- You can modify this form, but DO NOT modify the <FORM> tag or remove the file input field. -->
 <FORM ACTION="/iTrust/auth/fileUpload.jsp"  
           ENCTYPE="multipart/form-data"
           METHOD=POST>
     <INPUT TYPE=FILE NAME=pics>
     <INPUT TYPE=Submit />
</FORM>

<% 
/*
The segment below shows you how to load images from the database.
fileUpload.jsp, used by the form above, sets a session attribute
called last_image.  last_image tells you the database identifier of the
last-inserted image.
*/

//First, check to see if the img_id attribut is set.
if (null != session.getAttribute("last_image"))
{
	//We have an img_id set, so let's pull it out.
	String lastImage = (String) session.getAttribute("last_image");
	
	/*
	The line below can be used anywhere inside the authorized directories
	to include an image from the database. It takes a parameter: img_id, which
	is the database identifier of the image.  Here we have inserted the
	variable lastImage, which we pulled from above, but you could
	insert any value you want...
	*/
%>
	<img src="/iTrust/auth/dbImg.jsp?img_id=<%=lastImage %>" />
<%
	
	/*
	last_image is a session variable, so unless we clear it, it will stay
	set until the user logs out.  It is highly recommended that you clear it
	as soon as the user has first seen the image they uploaded, but you
	could make a link which clears it instead.
	*/
	session.removeAttribute("last_image");
	
}

%>
<%@include file="/footer.jsp" %>
