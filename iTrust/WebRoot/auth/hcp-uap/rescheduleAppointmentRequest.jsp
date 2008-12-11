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
pageTitle = "iTrust - Reschedule Appointment Request";
%>

<%@include file="/header.jsp" %>

<%
ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());
AppointmentRequestBean bean = null;
long requestID = 0L;
String reasonString = "";
String lhcpString = "";
String date1String = "";
String date2String = "";
String weeksString = "";
String minutesString = "";
String date1 = "";
String date2 = "";
String time1 = "";
String time2 = "";
String minutes = "";
String reason = "";

String reschedule = request.getParameter("reschedule");
if (reschedule != null && !reschedule.equals("")) {
	try {
		
		requestID = Long.parseLong(reschedule);
		bean = action.getAppointmentRequest(requestID);
		lhcpString = String.valueOf(bean.getRequesterMID());
		reasonString = bean.getReason();
		date1String = bean.getDate1String();
		date2String = bean.getDate2String();
		if (bean.getWeeksUntilVisit() > 0)
			weeksString = String.valueOf(bean.getWeeksUntilVisit());
		minutesString = String.valueOf(bean.getMinutes());
	} catch (Exception e) {
		e.printStackTrace();
	}
}
		


boolean formIsFilled = request.getParameter("formIsFilled") != null
	&& request.getParameter("formIsFilled").equals("true");

AppointmentRequestBean apptRequest;
if (formIsFilled) {
	apptRequest = new BeanBuilder<AppointmentRequestBean>()
			.build(request.getParameterMap(),
					new AppointmentRequestBean());
	apptRequest.setRequesterMID(loggedInMID.longValue());
	apptRequest.setHospitalID("0");
	apptRequest.setStatus(AppointmentRequestBean.NeedPatientConfirm);
	apptRequest.setRequestedMID(bean.getRequesterMID());
	apptRequest.setDate1String(request.getParameter("dateone") + " " + request.getParameter("timeone"));
	apptRequest.setDate2String(request.getParameter("datetwo") + " " + request.getParameter("timetwo"));
	date1 = request.getParameter("dateone");
	date2 = request.getParameter("datetwo");
	time1 = request.getParameter("timeone");
	time2 = request.getParameter("timetwo");
	minutes = request.getParameter("minutesString");
	reason = request.getParameter("reason");
	try {
		//add appointment
		AppointmentRequestAction apptRequestAction = new AppointmentRequestAction(prodDAO, loggedInMID.longValue());
		AppointmentRequestValidator validator = new AppointmentRequestValidator();
		minutesString = request.getParameter("minutesString");
		if (minutesString != null && !minutesString.equals(""))
			apptRequest.setMinutes(Integer.parseInt(minutesString));
		validator.validate(apptRequest);
		date1String = request.getParameter("dateone") + " " + request.getParameter("timeone");
		if (!date1String.equals(" "))
			validator.validateDate(AppointmentRequestBean.dateFormat, date1String);
		else
			apptRequest.setDate1String(null);
		date2String = request.getParameter("datetwo") + " " + request.getParameter("timetwo");
		if (!date2String.equals(" "))
			validator.validateDate(AppointmentRequestBean.dateFormat, date2String);
		else
			apptRequest.setDate2String(null);
		apptRequestAction.rescheduleAppointmentRequest(apptRequest, requestID);
		response.sendRedirect("viewMyAppointments.jsp?message=Request%20Successfully%20Sent");
		date1 = "";
		date2 = "";
		time1 = "";
		time2 = "";
		minutes = "";
		reason = "";
	} catch (FormValidationException e) {
%>
	<div align=center>
		<span class="iTrustError"><%=e.getMessage() %></span>
	</div>
<%
	}
}

%>


<div align=center>
<form action="rescheduleAppointmentRequest.jsp?reschedule=<%=requestID%>" method="post" name="mainForm">

<input type="hidden" name="formIsFilled" value="true"> 
<br />
<br />
The following doctor has requested an appointment with you: <%=lhcpString%><br />
<br />
<table class="fTable" align=center>
	<tr>
		<th colspan=2>Original request details</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">Date 1: </td>
		<td><%=bean.getDate1String()%></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Date 2: </td>
		<td><%=bean.getDate2String()%></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Minutes: </td>
		<td><%=bean.getMinutes()%></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Weeks: </td>
		<td><%=bean.getWeeksUntilVisit()%></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Reason: </td>
		<td><%=bean.getReasonString()%></td>
	</tr>
</table>
<br />
Please reschedule this appointment for a time that is convenient with you.
<br />
<br />
<table>
		<tr>
		<td>Date 1:</td>
		<td><input type="text" id="dateone" name="dateone" value="<%=date1%>" /></td>
		<td>
			<input type=button value="Select Date" onclick="displayDatePicker('dateone');">
		</td>
	</tr>
	<tr>
		<td>Time 1:</td>
		<td><input type="text" id="timeone" name="timeone" value="<%=time1%>" /></td>
		<td><span style="font-size: 12px">(HH:mm)</span></td>
	</tr>
	<tr>
		<td>Date 2:</td>
		<td><input type="text" id="datetwo" name="datetwo" value="<%=date2%>" /> </td>
		<td>
			<input type=button value="Select Date" onclick="displayDatePicker('datetwo');">
		</td>
	</tr>
		<tr>
		<td>Time 2:</td>
		<td><input type="text" id="timetwo" name="timetwo" value="<%=time2%>" /></td>
		<td><span style="font-size: 12px">(HH:mm)</span></td>
	</tr>
	<tr>
		<td>Length of Appointment:</td>
		<td><input type="text" name="minutesString" maxlength = 4 size=4 value="<%=minutes%>" /> </td>
		<td><span style="font-size: 12px">(1-9999 minutes)</span></td>
	</tr>
	<tr>
		<td>Reason:</td>
		<td colspan=2><input type="text" name="reason" value="<%=reason%>" size=40></td>
	</tr>

</table>
<br />
<input type="submit" style="font-size: 16pt; font-weight: bold;" value="Add Request">
<br />
<br />
</form>
</div>

<br />

<%@include file="/footer.jsp" %>
