<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewMyAppointmentsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Schedule an Appointment";
%>

<%@include file="/header.jsp"%>

<%
String confirm = request.getParameter("confirm");
AppointmentRequestBean bean = new AppointmentRequestBean();
boolean check1 = false;
boolean check2 = false;
String id = request.getParameter("id");
if (id != null && !id.equals("")) {
	ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());
	long requestID = Long.parseLong(id);
	bean = action.getAppointmentRequest(requestID);
	check1 = action.checkAppointmentConflictForPatient(bean.getRequestedMID(), bean.getDate1(), bean.getMinutes());
	check2 = action.checkAppointmentConflictForPatient(bean.getRequestedMID(), bean.getDate2(), bean.getMinutes());
}

if (confirm != null && !confirm.equals("")) {
%>
This appointment conflicts with another appointment.<br />
To confirm, select the appointment again.<br />
<% if (confirm.equals("1")) { %>
<a href="viewMyAppointments.jsp?schedule=<%=bean.getID()%>&choice=1">Schedule for <%=bean.getDate1String()%></a>
<% } else { %>
<a href="viewMyAppointments.jsp?schedule=<%=bean.getID()%>&choice=2">Schedule for <%=bean.getDate2String()%></a>
<% } %>
<a href="viewMyAppointments.jsp">Don't schedule appointment</a><br />
<%
} else {
%>

Schedule this appointment:<br />
<%=bean.getReason()%><br />
<%
PersonnelBean hcp = new PersonnelDAO(prodDAO).getPersonnel(bean.getRequesterMID());
%>
With doctor <%=hcp.getFullName()%><br />
<%if (bean.getDate1() != null) { 
if (check1 == false) {%>
<a href="viewMyAppointments.jsp?schedule=<%=bean.getID()%>&choice=1">Schedule for <%=bean.getDate1String()%></a><br />
<%} else {%>
<a href="scheduleAppointment.jsp?id=<%=bean.getID()%>&confirm=1">Schedule for <%=bean.getDate1String()%></a><br />
<%} }%>
<%if (bean.getDate2() != null) { 
if (check2 == false) {%>
<a href="viewMyAppointments.jsp?schedule=<%=bean.getID()%>&choice=2">Schedule for <%=bean.getDate2String()%></a><br />
<%} else {%>
<a href="scheduleAppointment.jsp?id=<%=bean.getID()%>&confirm=2">Schedule for <%=bean.getDate2String()%></a><br />
<%} }%>
<a href="viewMyAppointments.jsp">Don't schedule appointment</a><br />
	
<%} %>

<%@include file="/footer.jsp"%>
