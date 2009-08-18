<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewPrescriptionRecordsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.PatientBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.PrescriptionBean" %>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PatientDAO" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Get My Prescription Report";
%>

<%@include file="/header.jsp"%>

<%
PatientBean patient = new PatientDAO(prodDAO).getPatient(loggedInMID.longValue()); 
ViewPrescriptionRecordsAction action = new ViewPrescriptionRecordsAction(prodDAO,loggedInMID.longValue());
List<PatientBean> representees = action.getRepresentees();
boolean showMine = false;
boolean showOther = false;
	
	if (request.getParameter("mine") != null && request.getParameter("mine").equals("View Current")) showMine = true;
	if (request.getParameter("other") != null && request.getParameter("other").equals("View")) showOther = true;
	if (request.getParameter("representee") != null && request.getParameter("representee").equals("-1")) showOther = false;
%>
<div align="center">
	<form action="viewPrescriptionRecords.jsp" method="post">
		<table>
			<tr>
				<td>
					<span style="font-size: 24px; font-weight: bold;">View My Own Prescriptions</span>
				</td>
				<td>
					<input type="submit" name="mine" value="View Current"></input>
				</td>
			</tr>
			<tr>
				<td>
					<span style="font-size: 24px; font-weight: bold;">View Other's Prescriptions</span>
				</td>
			
<%	
		if (representees.size() > 0) { 
%>
				<td>
					<select name="representee">
						<option value="-1"></option>
<%
			int index = 0;
			for (PatientBean representee : representees) { 
%>
						<option value="<%=index %>"><%=representee.getFullName()%></option>
<%
				index ++;
			} 
%>
					</select>
					<input type="submit" name="other" value="View"></input>
				</td>
<%	
		} else { 
%>
				<td>
					<i>You are not anyone's representative</i>
				</td>
<%	
		} 
%>
			</tr>
		</table>
	</form>
	<br />
	<table class="fTable">
	
	
	
	
	
<%
	if (showMine) { 
		List<PrescriptionBean> prescriptions = action.getPrescriptionsForPatient(loggedInMID.longValue());
		if (prescriptions.size() == 0) { 
%>
		<tr>
			<td colspan=4>
				<i>No prescriptions found</i>
			</td>
		</tr>
<%
		} else { 
%>
		<tr>
			<th colspan=4><%= patient.getFullName() %></th>
		</tr>
		<tr class="subHeader">
			<td>ND Code</td>
			<td>Description</td>
			<td>Duration</td>
			<td>Prescribing HCP</td>
		</tr>
<%			
			for (PrescriptionBean prescription : prescriptions) { 
%>
		<tr>
			<td ><a href="viewPrescriptionInformation.jsp?visitID=<%=prescription.getVisitID()%>&presID=<%=prescription.getId()%>"><%=prescription.getMedication().getNDCodeFormatted() %></a></td>
			<td ><%=prescription.getMedication().getDescription() %></td>
			<td ><%=prescription.getStartDateStr() %> to <%=prescription.getEndDateStr() %></td>
			<td ><%= action.getPrescribingDoctor(prescription).getFullName() %></td>
		</tr>
<%			
			}
		} 
	} else if (showOther) {
		PatientBean representee = representees.get(Integer.parseInt(request.getParameter("representee"))); 
%>
		<tr>
			<th colspan=4><%= representee.getFullName() %></th>
		</tr>
<%	
		List<PrescriptionBean> prescriptions = action.getPrescriptionsForPatient(representee.getMID());
		if (prescriptions.size() == 0) { 
%>
		<tr>
			<td colspan=4>
				<i>No prescriptions found</i>
			</td>
		</tr>
<%
		} else { 
%>
		<tr class="subHeader">
			<td>ND Code</td>
			<td>Description</td>
			<td>Duration</td>
			<td>Prescribing HCP</td>
		</tr>
<%			
			for (PrescriptionBean prescription : prescriptions) { 
%>
		<tr>
			<td ><%=prescription.getMedication().getNDCodeFormatted() %></td>
			<td ><%=prescription.getMedication().getDescription() %></td>
			<td ><%=prescription.getStartDateStr() %> to <%=prescription.getEndDateStr() %></td>
			<td ><%= action.getPrescribingDoctor(prescription).getFullName() %></td>
		</tr>
<%
			} 
		}
	} 
%>
	</table>	
	<br />
</div>

<%@include file="/footer.jsp"%>
