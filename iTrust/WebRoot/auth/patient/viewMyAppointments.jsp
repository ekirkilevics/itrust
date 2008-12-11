<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyAppointmentsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="java.util.List"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View My Appointments";
%>

<%@include file="/header.jsp"%>

<%
ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());
PatientBean patient;

String reject = request.getParameter("reject");
if (reject != null && !reject.equals("")) {
	try {
		long requestID = Long.parseLong(reject);
		action.rejectAppointmentRequestByPatient(requestID);
%>
	<div align=center>
		<span class="iTrustMessage">Appointment Request Rejected</span>
	</div>
<%
	} catch (Exception e) {
%>
		<div align=center>
			<span class="iTrustError"><%=e.getMessage()%></span>
		</div>
<%
	}
}

String schedule = request.getParameter("schedule");
if (schedule != null && !schedule.equals("")) {
	String choice = request.getParameter("choice");
	if (choice != null && !choice.equals("")) {
		try {
			long requestID = Long.parseLong(schedule);
			int dateChoice = Integer.parseInt(choice);
			action.scheduleAppointmentRequestByPatient(requestID, dateChoice);
%>
		<div align=center>
			<span class="iTrustMessage">Appointment Request Scheduled</span>
		</div>
<%
		} catch (Exception e) {
%>
		<div align=center>
			<span class="iTrustError"><%=e.getMessage()%></span>
		</div>
<%
		}
	}
}

patient = action.getPatient();
%>

<%
if (request.getParameter("message") != null) {
	%><span class="iTrustMessage" style="font-size: 16px;"><%=request.getParameter("message") %></span><%
}
%>

<br />
<table class="fTable" align="center">
	<tr>
		<th colspan="3">Appointments</th>
	</tr>
	<tr class="subHeader">
    		<td>HCP</td>
   			<td>Date</td>
   			<td>Minutes</td>
  	</tr>
<%
	List<AppointmentBean> abList = action.getAppointments();
	if(abList.size() > 0 ) {
		for(AppointmentBean bean : abList){ 
			PersonnelBean hcp = new PersonnelDAO(prodDAO).getPersonnel(bean.getLHCPMID());
%>
	<tr>
		<td ><%=hcp.getFullName()%></td>
		<!-- td ><%=bean.getLHCPMID()%></td>-->
		<td ><%=bean.getAppointmentDateString()%></td>
		<td ><%=bean.getMinutes()%></td>
	</tr>
<%
		}
	}
	else {
%>
	<tr>
		<td colspan=3 align=center>
			No Data
		</td>
	</tr>
<%
	}
%>
</table>
<br /><br />

<table class="fTable" align="center">
	<tr>
		<th colspan="9">Requests Needing Response</th>
	</tr>
	<tr class="subHeader">
		<td>HCP</td>
		<td>Date 1</td>
		<td>Date 2</td>
		<td>Minutes</td>
		<td>Reason</td>
		<td>Weeks Until Appt</td>
		<td colspan=3>Patient Actions</td>
  	</tr>
<%

	List<AppointmentRequestBean> rnrList = action.getRequestsNeedingReponse(AppointmentRequestBean.NeedPatientConfirm);
	if(rnrList.size() > 0) {
		for(AppointmentRequestBean bean : rnrList){ 
			PersonnelBean hcp = new PersonnelDAO(prodDAO).getPersonnel(bean.getRequesterMID());
%>
	<tr>
		<td ><%=hcp.getFullName()%></td>
		<td ><%=bean.getDate1String()%></td>
		<td ><%=bean.getDate2String()%></td>
		<td ><%=bean.getMinutes()%></td>
		<td ><%=bean.getReason()%></td>
		<td ><%=bean.getWeeksUntilVisit()%></td>
<%
			if ((bean.getDate1() == null) && (bean.getDate2() == null)) {
				%><td >&nbsp;</td><%
			} else { 
				%><td ><a href="scheduleAppointment.jsp?id=<%=bean.getID()%>">Schedule</a></td><%
			} 
%>
		<td ><a href="rescheduleAppointmentRequest.jsp?reschedule=<%=bean.getID()%>">Reschedule</a></td>
		<td ><a href="viewMyAppointments.jsp?reject=<%=bean.getID()%>">Reject</a></td>
	</tr>
<%
		}
	} 
	else {
%>
	<tr>
		<td colspan=9 align=center>
			No Data
		</td>
	</tr>
<%		
	}
%>
</table>
<br /><br />

<table class="fTable" align="center">
	<tr>
		<th colspan="9">Request History</th>
	</tr>
	<tr class="subHeader">
		<td>Requester</td>
		<td>Requested</td>
		<td>Hospital</td>
		<td>Date 1</td>
		<td>Date 2</td>
		<td>Minutes</td>
		<td>Reason</td>
		<td>Weeks Until Appt</td>
		<td>Status</td>
  	</tr>
<%

	List<AppointmentRequestBean> apbList = action.getRequestHistory(AppointmentRequestBean.NeedPatientConfirm);
	if(apbList.size() > 0) {
		for(AppointmentRequestBean bean : apbList){ 
			String requester = "";
			String requested = "";
			
			if (bean.getRequestedMID() == patient.getMID()) {
				requested = patient.getFullName();
			} else {
				PersonnelBean hcp = new PersonnelDAO(prodDAO).getPersonnel(bean.getRequestedMID());
				requested = hcp.getFullName();
			}
			
			if(bean.getRequesterMID() == patient.getMID()) {
				requester = patient.getFullName();
			}
			else {
				PersonnelBean hcp2 = new PersonnelDAO(prodDAO).getPersonnel(bean.getRequesterMID());
				requester = hcp2.getFullName();
			}
					
%>
	<tr>
		<td ><%=requester%></td>
		<td ><%=requested%></td>
		<td ><%=bean.getHospitalIDString() %></td>
		<td ><%=bean.getDate1String()%></td>
		<td ><%=bean.getDate2String()%></td>
		<td ><%=bean.getMinutes()%></td>
		<td ><%=bean.getReasonString()%></td>
		<td ><%=bean.getWeeksUntilVisit()%></td>
		<td ><%=bean.getStatus()%></td>
	</tr>
<%
		}
	}
	else {
%>
	<tr>
		<td colspan=9 align=center>
			No Data
		</td>
	</tr>
<%		
	}
%>
</table>
<br />

<%@include file="/footer.jsp"%>
