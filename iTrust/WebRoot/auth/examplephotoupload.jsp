<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.exception.iTrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ProfilePhotoAction"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Example Profile Photo Upload";
%>

<%@include file="/header.jsp"%>


<%
	PatientBean patient = DAOFactory.getProductionInstance().getPatientDAO().getPatient(loggedInMID);
%>
<br>
This is an example page for future assignments. Eventually, we would
like to have full-featured, secure file uploading capabilities for
things like medical imaging and document management (e.g. scanned PDFs).
For now, this example is your basic profile picture. Here are some
things to note about this example
<ul>
	<li>All photos are stored in the MySQL database - <em>not</em> on
	the file system. This is desirable for things like security, platform
	independence, and simpler deployment. We use the BLOB database type to
	store the image's bytes.</li>
	<li>Currently, we only support JPEG, although we could easily
	support other formats by adding a column to the database for the file
	type.</li>
	<li>Note the file upload limit is enforced at the database level.
	This is not as desirable as in the upload stage, because one could
	upload a 100 GB file and crash the server before it ever gets to the
	database. We will need some kind of validation for the file size.</li>
</ul>
<br>
To browse through this example, look through the following files:
<ul>
	<li>WebRoot/auth/examplephotoupload.jsp</li>
	<li>WebRoot/auth/exampleprofilephoto.jsp</li>
	<li>sql/createTables.sql - the ProfilePhotos table</li>
	<li>ProfilePhotoAction - this is the upload part where we use an
	external library to do our uploads</li>
	<li>ProfilePhotoDAO - this is the storage part</li>
</ul>
<br>


<p>Current logged in user: <%=patient.getFullName()%>
</div>


<div id=Content>
<%
	ProfilePhotoAction action = new ProfilePhotoAction(DAOFactory.getProductionInstance(),
			loggedInMID);
	if (ServletFileUpload.isMultipartContent(request)) {
		action.storePicture(request);
	}
%>
<h2>To be uploaded</h2>

<form action="" method="post" enctype="multipart/form-data"><input
	type="file" name="photo"> <input type="hidden"
	name="formIsFilled" value="true"> <input type="submit"
	value="Upload"></form>

<h2>Here's what's stored in the database for User <%=loggedInMID %></h2>
<img width="300" src="exampleprofilephoto.jsp"
	alt="Photo should be showing up here - if there's one in the database."></div>

<br>
<br>

<%@include file="/footer.jsp"%>