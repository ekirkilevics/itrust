<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyAppointmentsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentRequestBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View My Appointments";
%>

<%@include file="/header.jsp" %>

<%
	PatientDAO patientDAO = new PatientDAO(prodDAO);
	PersonnelDAO personnelDAO = new PersonnelDAO(prodDAO);

ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());

String reject = request.getParameter("reject");
if (reject != null && !reject.equals("")) {
	try {
		long requestID = Long.parseLong(reject);
		action.rejectAppointmentRequestByLHCP(requestID);
%>
	<br />
	<div align=center>
		<span class="iTrustMessage">Appointment Request Rejected</span>
	</div>
	<br />
<%
	} catch (Exception e) {
%>
	<br />
	<div align=center>
		<span class="iTrustError"><%=e.getMessage() %></span>
	</div>
	<br />
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
			action.scheduleAppointmentRequestByLHCP(loggedInMID.longValue(), requestID, dateChoice);
%>
	<br />
	<div align=center>
		<span class="iTrustMessage">Appointment Request Scheduled</span>
	</div>
	<br />
<%
		} catch (Exception e) {
%>
	<br />
	<div align=center>
		<span class="iTrustError"><%=e.getMessage() %></span>
	</div>
	<br />
<%
		}
	}
}

List<AppointmentBean> appointments = action.getAppointments();
List<AppointmentRequestBean> requests1 = action.getRequestsNeedingReponse(AppointmentRequestBean.NeedLHCPConfirm);
List<AppointmentRequestBean> requests2 = action.getRequestHistory(AppointmentRequestBean.NeedLHCPConfirm);
%>

<%
if (request.getParameter("message") != null) {
%>
	<br />
	<div align=center>
		<span class="iTrustMessage" style="font-size: 16px;"><%=request.getParameter("message") %></span>
	</div>
	<br />
<%
}
%>

<table class="fTable" align="center">
	<tr>
		<th colspan="3">Appointments</th>
	</tr>

	<tr class="subHeader">
   		<td>Patient</td>
   		<td>Date</td>
   		<td>Minutes</td>
  	</tr>
 <%		
 		PatientBean patient;
 		for(AppointmentBean bean : appointments){ 
			patient = patientDAO.getPatient(bean.getPatientMID());
%>
			<tr>
				<td ><%=patient.getFullName()%></td>
				<td ><%=bean.getAppointmentDateString()%></td>
				<td ><%=bean.getMinutes()%></td>
			</tr>
		<%} %>
</table>
<br /><br />

<table class="fTable" align="center">
	<tr>
		<th colspan="9">Requests Needing Response</th>
	</tr>
	<tr class="subHeader">
  		<td>Patient</td>
  		<td>Date 1</td>
  		<td>Date 2</td>
  		<td>Minutes</td>
  		<td>Reason</td>
  		<td>Weeks Until Appt</td>
  		<td colspan="3">HCP Actions</td>

  	</tr>
<%
		long requesterMID;
		String requester;
		PersonnelBean personnel;
		for(AppointmentRequestBean bean : requests1) {
			requesterMID = bean.getRequesterMID();
			if (requesterMID < 999999999L) {
				patient = patientDAO.getPatient(requesterMID);
				requester = patient.getFullName();
			} else {
				personnel = personnelDAO.getPersonnel(requesterMID);
				requester = personnel.getFullName();
			}
%>
			<tr>
				<td ><%=requester%></td>
				<td ><%=bean.getDate1String()%></td>
				<td ><%=bean.getDate2String()%></td>
				<td ><%=bean.getMinutes()%></td>
				<td ><%=bean.getReasonString()%></td>
				<td ><%=bean.getWeeksUntilVisit()%></td>
				<%if ((bean.getDate1() == null) && (bean.getDate2() == null)) {%>
				<td >&nbsp;</td>
				<%} else { %>
				<td ><a href="scheduleAppointment.jsp?id=<%=bean.getID()%>">Schedule</a></td>
				<%} %>
				<td ><a href="rescheduleAppointmentRequest.jsp?reschedule=<%=bean.getID()%>">Reschedule</a></td>
				<td ><a href="viewMyAppointments.jsp?reject=<%=bean.getID()%>">Reject</a></td>
			</tr>
<%		} %>
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
		long requestedMID;
		String requested;
		for(AppointmentRequestBean bean : requests2) {
			requesterMID = bean.getRequesterMID();
			requestedMID = bean.getRequestedMID();
			
			if (requesterMID < 999999999L) {
				patient = patientDAO.getPatient(requesterMID);
				requester = (patient != null) ? patient.getFullName() : "";
			} else {
				personnel = personnelDAO.getPersonnel(requesterMID);
				requester = (personnel != null) ? personnel.getFullName() : "";
			}
			
			if (requestedMID < 999999999L) {
				patient = patientDAO.getPatient(requestedMID);
				requested = (patient != null) ? patient.getFullName() : "";
			} else {
				personnel = personnelDAO.getPersonnel(requestedMID);
				requested = (personnel != null) ? personnel.getFullName() : "";
			}
%>
			<tr>
				<td ><%=requester%></td>
				<td ><%=requested%></td>
				<td ><%=bean.getHospitalIDString()%></td>
				<td ><%=bean.getDate1String()%></td>
				<td ><%=bean.getDate2String()%></td>
				<td ><%=bean.getMinutes()%></td>
				<td ><%=bean.getReasonString()%></td>
				<td ><%=bean.getWeeksUntilVisit()%></td>
				<td ><%=bean.getStatus()%></td>
			</tr>
<%		} %>
</table>
<br />
<a href="addAppointmentRequest.jsp">Add a new Appointment Request</a>

<%@include file="/footer.jsp" %>
