<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyAppointmentsAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AppointmentRequestAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.AppointmentRequestBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.validate.AppointmentRequestValidator"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Create Appointment Request";
%>

<%@include file="/header.jsp"%>

<%
ViewMyAppointmentsAction action = new ViewMyAppointmentsAction(prodDAO, loggedInMID.longValue());

AppointmentRequestBean bean = null;
String reasonString = "";
String Time1 = "";
String Time2 = "";
String Date1 = "";
String Date2 = "";
String Minutes = "";
String LHCPMID = "";
String HospitalID = "";
String HCPName = "";

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

AppointmentRequestBean apptRequest;
PersonnelBean pBean = (PersonnelBean)session.getAttribute("personnel");
if(pBean != null) {
	LHCPMID = "" + pBean.getMID();
	HCPName = pBean.getFullName();
}

if (formIsFilled) {
	apptRequest = new BeanBuilder<AppointmentRequestBean>()
			.build(request.getParameterMap(),
					new AppointmentRequestBean());
	
	
	apptRequest.setRequesterMID(loggedInMID.longValue());
	apptRequest.setStatus(AppointmentRequestBean.NeedLHCPConfirm);

	apptRequest.setDate1String(request.getParameter("dateone") + " " + request.getParameter("timeone"));
	apptRequest.setDate2String(request.getParameter("datetwo") + " " + request.getParameter("timetwo"));
	

	String personnel = request.getParameter("UID_PERSONNELID");
	if (personnel != null && !personnel.equals(""))
		apptRequest.setRequestedMID(Long.parseLong(personnel));
	String minutes = request.getParameter("minutesString");
	if (minutes != null && !minutes.equals(""))
		apptRequest.setMinutes(Integer.parseInt(minutes));
	
	Time1 = request.getParameter("timeone");
	Time2 = request.getParameter("timetwo");
	Date1 = request.getParameter("dateone");
	Date2 = request.getParameter("datetwo");
	Minutes = request.getParameter("minutesString");
	LHCPMID = request.getParameter("UID_PERSONNELID");
	HospitalID = request.getParameter("hospitalID");
	reasonString = request.getParameter("reason");
	
	try {
		//add appointment
		AppointmentRequestAction apptRequestAction = new AppointmentRequestAction(prodDAO, loggedInMID.longValue());
		AppointmentRequestValidator validator = new AppointmentRequestValidator();
		validator.validate(apptRequest);
		String date1String = request.getParameter("dateone") + " " + request.getParameter("timeone");
		if (!date1String.equals(" "))
			validator.validateDate(AppointmentRequestBean.dateFormat, date1String);
		else
			apptRequest.setDate1String(null);
		String date2String = request.getParameter("datetwo") + " " + request.getParameter("timetwo");
		if (!date2String.equals(" "))
			validator.validateDate(AppointmentRequestBean.dateFormat, date2String);
		else
			apptRequest.setDate2String(null);
		apptRequestAction.addAppointmentRequest(apptRequest);
		%><span class="iTrustMessage">Request Successfully Sent</span><br /><br /><%
		Time1 = "";
		Time2 = "";
		Date1 = "";
		Date2 = "";
		Minutes = "";
		LHCPMID = "";
		HospitalID = "";
		reasonString = "";
	} catch (FormValidationException e) {
		%><span class="iTrustError"><%
		e.printHTML(pageContext.getOut());
		%></span><%
	}
}



%>

<%@include file="/util/getUserFrame.jsp" %>

<form action="addappointmentrequest.jsp" method="post" name="mainForm">
<script type="text/javascript" src="/iTrust/js/DatePicker.js"></script>
<input type="hidden" name="formIsFilled" value="true"> 
<br />
<br />

<div align="center">
	<div style="width: 50%">
		Please enter in the dates, reason, and requested doctor for the appointment. You can select up to two dates, or no dates if your timing is flexible. If you know which doctor you want to see, please select that person. If you 
		are willing to see any doctor, then please select the hospital, and the request will be sent to all doctors at that hospital.
	</div>
</div>
<br />
<br />

<table align="center">
	<tr>
		<td>Date 1:</td>
		<td><input type="text" id="dateone" name="dateone" value="<%=Date1%>" /></td>
		<td><input type=button value="Select Date" onclick="displayDatePicker('dateone');"></td>
	</tr>
	<tr>
		<td>Time 1:</td>
		<td><input type="text" id="timeone" name="timeone" value="<%=Time1%>" /></td>
		<td>HH:mm</td>
	</tr>
	<tr>
		<td>Date 2:</td>
		<td><input type="text" id="datetwo" name="datetwo" value="<%=Date2%>" /> </td>
		<td><input type=button value="Select Date" onclick="displayDatePicker('datetwo');"></td>
	</tr>
	<tr>
		<td>Time 2:</td>
		<td><input type="text" id="timetwo" name="timetwo" value="<%=Time2%>"/></td>
		<td>HH:mm</td>
	</tr>

	<tr>
		<td>Length of Appointment</td>
		<td><input type="text" name="minutesString" maxlength = 4 size=4 value="<%=Minutes%>"/> </td>
		<td>1-9999 minutes</td>
	</tr>
	<tr>
		<td>Reason:</td>
		<td colspan=2><input type="text" name="reason" size=40 value="<%=reasonString%>"></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td colspan=3><i>Note: Please input a doctor (HCP) number OR a hospital number.</i></td>
	</tr>
	<tr>
		<td>Doctor:</td>
		<td>
			<input id="UID_PERSONNELID" name="UID_PERSONNELID" value="<%=LHCPMID%>" type="hidden">
			<span id="NAME_PERSONNELID" name="NAME_PERSONNELID"><%=HCPName%></span>
			<input id="find_user" type="button" onclick="getUser('PERSONNELID');" value="Find User">
		</td>
	</tr>
	<tr>
		<td>Hospital</td>
		<td><input type="text" name="hospitalID" size=20 value="<%=HospitalID%>"></td>		
	</tr>
</table>
<br />
<table align="center">
	<tr>
		<td colspan=2 align=center><input type="submit"
			style="font-size: 16pt; font-weight: bold;" value="Add Request"></td>
	</tr>
</table>
</form>

<%@include file="/footer.jsp"%>
