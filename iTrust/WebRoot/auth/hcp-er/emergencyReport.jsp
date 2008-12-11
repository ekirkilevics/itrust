<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EmergencyReportAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PrescriptionBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.AllergyBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ProcedureBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - ER Report";

String pidString = null;
boolean print = (null != request.getParameter("print") && request.getParameter("print").equals("true"));

if (!print) {
	/* Require a Patient ID first */
	pidString = (String)session.getAttribute("pid");
	if (null == pidString || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-er/emergencyReport.jsp");
		return;
	}
	else {
		session.setAttribute("printPid", pidString);
		session.removeAttribute("pid");
	}
}
else {
	pidString = (String)session.getAttribute("printPid");
	if (null == pidString || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-er/emergencyReport.jsp");
		return;
	}
	session.removeAttribute("printPid");
}

/* If the patient id doesn't check out, then kick 'em out to the exception handler */
EmergencyReportAction action = new EmergencyReportAction(prodDAO, loggedInMID.longValue(), pidString);
	
/* Now take care of updating information */
%>

<% if (print) { %>
<%@include file="/print_header.jsp" %>
<%}
else {%>
<%@include file="/header.jsp" %>
<%}%>


<% if (!print) {%>
<form action="emergencyReport.jsp" method="post">
	<input type="hidden" name="print" id="print" value="true" />
	<input type="submit" value="Print" />
</form>
<%} %>

<ul>
<li>Name: <%=action.getPatientName()%></li>
<li>Age: <%=action.getPatientAge()%></li>
<li>Gender: <%=action.getPatientGender()%> </li>
<li>Emergency Contact: <%=action.getPatientEmergencyContact() %></li>
<li>Allergies:
<%
if (0 == action.getAllergies().size()) {
%><strong>No allergies on record</strong><%	
}
else {
	%><ul><%
	for ( AllergyBean bean: action.getAllergies()) {
		out.print("<li>" + bean.getDescription() + " " + bean.getFirstFoundStr() + "</li>");
	} 
	%></ul><%
}
%>
</li>
<li>Blood Type: <%=action.getBloodType()%> </li>

<li>Diagnoses: 
<%
if (0 == action.getWarningDiagnoses().size()) {
%><strong>No critical diagnoses on record</strong><%	
}
else {
	%><ul><%
	for(DiagnosisBean bean : action.getWarningDiagnoses()) {
		out.print("<li>" + bean.getICDCode() + " " + bean.getDescription() + "</li>");
	} 
	%></ul><%
}
%>
</li>

<li>Prescriptions: 
<%
if (0 == action.getCurrentPrescriptions().size()) {
%><strong>No current prescriptions on record</strong><%	
}
else {
	%><ul><%
	for(PrescriptionBean bean : action.getCurrentPrescriptions()) {
		out.print("<li>" + bean.getMedication().getNDCode() + " " + bean.getMedication().getDescription() + "</li>");
	} 
	%></ul><%
}
%>
</li>

<li>Immunizations:
<% if (0 == action.getImmunizations().size()) { %>
<strong>no immunizations on record</strong>
<%	
}
else {
%>
<ul>
<%
	for (ProcedureBean bean : action.getImmunizations()) {
		if (null != bean.getAttribute() && bean.getAttribute().equals("immunization"))
			out.print("<li>" + bean.getDescription() + " (" + bean.getCPTCode() +")" + "</li>");
	} 
%>
</ul>
<%
}
%>
</li>

<%@include file="/footer.jsp" %>
