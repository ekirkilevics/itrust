<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@include file="/authenticate.jsp" %>

<%
	if(validSession) {
		errorMessage = (String) session.getAttribute("errorMessage");
		session.removeAttribute("errorMessage");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><%=pageTitle %></title>
		<link href="/iTrust/css/main.css" type="text/css" rel="stylesheet" />
		<link href="/iTrust/css/datepicker.css" type="text/css" rel="stylesheet" />
		<script src="/iTrust/js/DatePicker.js" type="text/javascript"></script>

		<script src="/iTrust/js/jquery-1.2.6.js" type="text/javascript"></script>
		<link href="/iTrust/css/facebox/facebox.css" media="screen" rel="stylesheet" type="text/css"/>
		<script src="/iTrust/js/facebox/facebox.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$('a[rel*=facebox]').facebox()
			});
			$.facebox.settings.loading_image = '/iTrust/image/facebox/loading.gif';
			$.facebox.settings.close_image   = '/iTrust/image/facebox/closelabel.gif';
		</script>
	</head>
	<body>
		<div class="iTrustNav">
			<div style="float: left; width: 48%; margin-left: 10px;">
					<a class="iTrustNavlink" href="/iTrust">Home</a>
					&nbsp;&nbsp;&nbsp;
<%
			if( validSession ) {
				if(    (loggedInMID != null)
					&& (loggedInMID.longValue() != 0L) ) 
				{
					if(userRole != "tester") {
%>					
						<a class="iTrustNavlink"
						   href="/iTrust/auth/<%=userRole %>/information.jsp"
						   rel="facebox">Information</a>
<%
					}
%>
					<!-- Extra global nav links go here -->
			</div>
			<div style="float: right; width: 48%; text-align: right; margin-right: 10px;">
				<%=userName %> 
				| <a class="iTrustNavlink" href="/iTrust/logout.jsp">Logout</a>
<%
				}
			}	
%>
			</div>
			<div style="clear: both;">
			</div>
		</div>
		<div style="margin-left: 10px;">
			<span style="color: #FFCC00; font-style: italic; font-size: 2em;">i</span><span style="color: #666; font-size: 3em;">Trust</span>
		</div>	

		<div>
			<div class="iTrustMenu">
<%
				if (  validSession ) {
					if (    (loggedInMID != null)
					     && (loggedInMID.longValue() != 0L)) 
					{
						if (userRole == "patient") {
							%><%@include file="/auth/patient/menu.jsp"%><%
						}
						else if (userRole == "uap") {
							%><%@include file="/auth/uap/menu.jsp"%><%
						}
						else if (userRole == "hcp") {
							%><%@include file="/auth/hcp/menu.jsp"%><%
						}
						else if (userRole == "er") {
							%><%@include file="/auth/er/menu.jsp"%><%
						}
						else if (userRole == "admin") {
							%><%@include file="/auth/admin/menu.jsp"%><%
						}
						else if (userRole == "tester") {
							%><%@include file="/auth/tester/menu.jsp"%><%
						}
					}	
					else {
						String uri = request.getRequestURI();
						if( uri.indexOf("privacyPolicy.jsp") >= 0) {
%>
							<%@include file="/logoutMenu.jsp"%>
<%
						} else {
%>
							<%@include file="/loginMenu.jsp"%>
<%
						}
					}
				}
				else {
%>
					<%@include file="/logoutMenu.jsp"%>
<%
				}
%>
			</div>
			<div class="iTrustPage">
				<div class="iTrustContent">
<%
	if(errorMessage != null) {
%>	
					<div style="text-align: center; width: 100%; background-color: black;">
						<span style="color: red; font-size: 28px; font-weight: bold;"><%=errorMessage %></span>
					</div>
<% 
	}
%>