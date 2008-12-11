<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyAppointmentsAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AppointmentRequestAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.validate.AppointmentRequestValidator"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Create Appointment Request";
%>

<%@include file="/header.jsp" %>

<%
ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());
AppointmentRequestBean bean = null;
String reasonString = "";

String reschedule = request.getParameter("reschedule");
if (reschedule != null && !reschedule.equals("")) {
	try {
		
		long requestID = Long.parseLong(reschedule);
		bean = action.getAppointmentRequest(requestID);
		reasonString = bean.getReason();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
		


boolean formIsFilled = request.getParameter("formIsFilled") != null
	&& request.getParameter("formIsFilled").equals("true");

AppointmentRequestBean apptRequest = new AppointmentRequestBean();
if (formIsFilled) {
	apptRequest = new BeanBuilder<AppointmentRequestBean>()
			.build(request.getParameterMap(),
					new AppointmentRequestBean());
	apptRequest.setRequesterMID(loggedInMID.longValue());
	apptRequest.setStatus(AppointmentRequestBean.NeedPatientConfirm);
	apptRequest.setHospitalID("0");
	String patient = request.getParameter("patientMID");
	if (patient != null && !patient.equals(""))
		apptRequest.setRequestedMID(Long.parseLong(patient));
	String weeks = request.getParameter("weeks");
	if (weeks != null && !weeks.equals(""))
		apptRequest.setWeeksUntilVisit(Integer.parseInt(weeks));
	String minutes = request.getParameter("minutesString");
	if (minutes != null && !minutes.equals(""))
		apptRequest.setMinutes(Integer.parseInt(minutes));
	try {
		//add appointment
		AppointmentRequestAction apptRequestAction = new AppointmentRequestAction(prodDAO, loggedInMID.longValue());
		AppointmentRequestValidator validator = new AppointmentRequestValidator();
		validator.validate(apptRequest);
		apptRequestAction.addAppointmentRequest(apptRequest);
%>
	<br />
	<div align=center>
		<span class="iTrustMessage">Request Successfully Sent</span>
	</div>
	<br />
<%
		apptRequest.setWeeksUntilVisit(0);
		apptRequest.setRequestedMID(0);
		apptRequest.setMinutes(0);
	} catch (FormValidationException e) {
%>
	<br />
	<div align=center>
		<span class="iTrustError"><%=e.getMessage() %></span>
	</div>
	<br />
<%
	}
}

%>

<div align=center>
<form action="addAppointmentRequest.jsp" method="post" name="mainForm">
<input type="hidden" name="formIsFilled" value="true"> 
<br />
<br />
<div style="width: 50%">Please enter in weeks until visit, reason, and
requested patient for the appointment.</div>
<br />
<br />
<table class="fTable">
	<tr>
		<th colspan=2>
			Appointment Details
		</th>
	</tr>
	<tr><td class="subHeaderVertical">Weeks Until Visit:</td>
	<td><input type="text" name="weeks" value="<%=apptRequest.getWeeksUntilVisit()%>" /></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Length of Appointment:</td>
		<td>
			<input type="text" name="minutesString" maxlength = 4 size=4 value="<%=apptRequest.getMinutes()%>"/>
			<span style="font-size: 12px;">1-9999 minutes</span> 
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Reason:</td>
		<td colspan=2><input type="text" name="reason" size=40 value="<%=reasonString%>"></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Patient MID:</td>
		<td colspan=2><input type="text" name="patientMID" size=10 value="<%=apptRequest.getRequestedMID()%>"></td>
	</tr>
</table>
<br />
<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Add Request"></td>

</form>
</div>

<br />

<%@include file="/footer.jsp" %>
