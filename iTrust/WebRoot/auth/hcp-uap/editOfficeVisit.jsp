<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.LabProcedureBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.LOINCbean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.beans.DiagnosisBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.MedicationBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ProcedureBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditOfficeVisitAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.EditOfficeVisitForm"%>
<%@page import="edu.ncsu.csc.itrust.exception.iTrustException"%>
<%@page import="edu.ncsu.csc.itrust.beans.HospitalBean"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.beans.PrescriptionBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Document Office Visit";
%>

<%@include file="/header.jsp" %>

<%
	String ovIDString = request.getParameter("ovID");

	String pidString = (String)session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/editOfficeVisit.jsp?ovID=" + ovIDString);
	   	return;
	}
	//else {
	//	session.removeAttribute("pid");
	//}
	
    EditOfficeVisitAction action = new EditOfficeVisitAction(prodDAO, loggedInMID, pidString, ovIDString);
    
	long ovID = action.getOvID();
	OfficeVisitBean visit = action.getOfficeVisit();
	String confirm = "";
	if ("true".equals(request.getParameter("formIsFilled"))) {		
		EditOfficeVisitForm form = new BeanBuilder<EditOfficeVisitForm>().build(request.getParameterMap(), new EditOfficeVisitForm());
		form.setHcpID("" + visit.getHcpID());
		form.setPatientID("" + visit.getPatientID());
		try {
			confirm = action.updateInformation(form);
		}
		catch (FormValidationException e) {
			e.printHTML(pageContext.getOut());
			confirm = "Input not valid";
		}
	}
	OfficeVisitBean ov = action.getOfficeVisit();
	List<HospitalBean> hcpHospitals = action.getHospitals(ov.getHcpID());
	List <LabProcedureBean> lpBeans = action.getLabProcedures(Long.parseLong(pidString), ovID);
%>

<div align=center>
<%
if (!"".equals(confirm)) {
	if ("success".equals(confirm)) { %>
		<span class="iTrustMessage">Information Updated Successfully</span>
<%	}
	else { %>
		<span class="iTrustError"><%=confirm%></span>		
<%	}
}	
%>
</div>


<script type="text/javascript">
	function removeID(type, value) {
		document.getElementById(type).value = value;
		document.forms[0].submit();
	}
</script>

<form action="editOfficeVisit.jsp" method="post" id="mainForm">
	<input type="hidden" name="formIsFilled" value="true" />
	<input type="hidden" name="ovID" value="<%=ovID%>" />
	<input type="hidden" id="removeDiagID" name="removeDiagID" value="" />
	<input type="hidden" id="removeMedID" name="removeMedID" value="" />
	<input type="hidden" id="removeProcID" name="removeProcID" value="" />
	<input type="hidden" id="removeImmunizationID" name="removeImmunizationID" value="" />
	<input type="hidden" id="removeLabProcID" name="removeLabProcID" value="" />

<div align=center>
<table class="fTable" align="center">
	<tr>
		<th colspan="2">Office Visit</th>
	</tr>
	<tr>
		<td class="subHeaderVertical">Patient ID:</td>
		<td><%=prodDAO.getAuthDAO().getUserName(ov.getPatientID())%> </td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Date of Visit:</td>
		<td><input name="visitDate" value="<%=ov.getVisitDateStr()%>" /><input type="button" value="Select Date" onclick="displayDatePicker('visitDate');" /></td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Hospital:</td>
		<td><select name="hospitalID">
				<option value="">N/A</option>
				<%for(HospitalBean hos : hcpHospitals) {%>
					<option value="<%=hos.getHospitalID()%>" 
						<%=hos.getHospitalID().equals(ov.getHospitalID()) ? "selected=selected" : ""%> > 
						<%=hos.getHospitalName()%>
					</option>
				<%} %>				
			</select>
		</td>
	</tr>
	<tr>
		<td class="subHeaderVertical">Notes:</td>
		<td><textarea rows="4" style="width: 100%;" name="notes"><%=ov.getNotes()%></textarea></td>
	</tr>
</table>
<br />
<input type="submit" name="update" value="Update" >
</div>
<br /><br />
<div align=center>
<table class="fTable" align="center">
	<tr>
		<th colspan="6">Prescriptions</th>
	</tr>
	<tr class="subHeader">
		<td>Medication</th>
		<td>Dosage</th>
		<td>Dates</th>
		<td colspan=2>Instructions</th>
		<td style="width: 60px;">Action</th>
	</tr>
	
	<%if(ov.getPrescriptions().size()==0){ %>
		<tr>
			<td colspan="6" style="text-align: center;">No Prescriptions on record</td>
		</tr>
	<%}else{ %>
		<%for(PrescriptionBean pres : ov.getPrescriptions()){ %>
		<tr>
			<td align=center><%=pres.getMedication().getDescription()%> (<%=pres.getMedication().getNDCode()%>)</td>
			<td align=center><%=pres.getDosage()%>mg</td>
			<td align=center><%=pres.getStartDateStr()%> to <%=pres.getEndDateStr()%></td>						
			<td align=center colspan=2><%=pres.getInstructions()%></td>						
			<td align=center><a href="javascript:removeID('removeMedID','<%=pres.getId()%>');">Remove</a></td>
		</tr>
		<%}
	}%>
	<tr>
		<th colspan="6" style="text-align: center;">Add New</th>
	</tr>
	 <tr>
	 	<td align=center>
	 		<select name="addMedID" style="font-size:10px;">
	 			<option value=""> -- Please Select a Medication -- </option>
	 			<%for(MedicationBean med : prodDAO.getNDCodesDAO().getAllNDCodes()){%>
		 			<option value="<%=med.getNDCode()%>"><%=med.getNDCode()%> - <%=med.getDescription()%></option>			 			
	 			<%}%>
	 		</select>
	 	</td>
	 	<td align=center>
	 		<input type="text" name="dosage" maxlength="6" style="width: 50px;"> mg
	 	</td>
	 	<td align=center colspan=2>
	 		<input type="text" name="startDate" style="width: 80px;" 
	 			onclick="displayDatePicker('startDate');" 
	 			onselect="displayDatePicker('startDate');"
	 			value="<%=new SimpleDateFormat("MM/dd/yyyy").format(new Date())%>"> 
	 		to 
			<input type="text" name="endDate" style="width: 80px;"
				onclick="displayDatePicker('endDate');" 
	 			onselect="displayDatePicker('endDate');"
	 			value="<%=new SimpleDateFormat("MM/dd/yyyy").format(new Date())%>">
	 	</td>
	 	<td align=center>
	 		<input type="text" name="instructions" value="-- Instructions --" maxlength=500>
	 	</td>
	 	<td>
		 	<input type="submit" value="Add Prescription">
	 	</td>
	 </tr>
</table>
</div>
<br /><br />
<div align=center>
	<table class="fTable" align="center">
			<tr>
				<th colspan="5">Laboratory Procedures</th>
			</tr>
			<tr class="subHeader">
				<td>LOINC Code</td>
				<td>Status</td>
				<td>Commentary</td>
				<td>Results</td>
				<td style="width: 60px;">Updated Date</td>
			</tr>
			<%if(lpBeans.size()==0){ %>
			<tr>
				<td colspan="5" style="text-align: center;">No Laboratory Procedures on	record</td>
			</tr>
			<%} else { %>
			<%for(LabProcedureBean labproc : lpBeans){ %>
			<tr>
				<td align=center><%=labproc.getLoinc()%></td>
				<td align=center><%=labproc.getStatus()%></td>
				<td align=center><%=labproc.getCommentary()%></td>
				<td align=center><%=labproc.getResults()%></td>
				<td align=center><%=labproc.getTimestamp()%></td>
				<td ><a
					href="javascript:removeID('removeLabProcID','<%=labproc.getProcedureID()%>');">Remove</a></td>
			</tr>
			<%} %>
			<%} %>
			<tr>
				<th colspan="5" style="text-align: center;">New</th>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<select name="addLabProcID"	style="font-size: 10px;">
						<option value="">-- Please Select a Procedure --</option>
							<% for(LOINCbean loinc : prodDAO.getLOINCDAO().getAllLOINC()) { %>
						<option value="<%=loinc.getLabProcedureCode()%>"> <%=loinc.getLabProcedureCode()%>
					 		- <%=loinc.getComponent()%> - <%=loinc.getKindOfProperty()%> - <%=loinc.getTimeAspect()%>
					 		- <%=loinc.getSystem()%> - <%=loinc.getScaleType()%> 
					 		- <%=loinc.getMethodType()%></option>
							<% } %>
					</select>
					<input	type="submit" name="addLP" value="Add Lab Procedure" >
				</td>
			</tr>
		</table>
	</div>
<br /><br />
<div align=center>
	<div style="display: inline-table; margin-right:10px;">
		<table class="fTable" align="center">
			<tr>
				<th colspan="3">Diagnoses</th>
			</tr>
			<tr class="subHeader">
				<td>ICD Code</td>
				<td>Description</td>
				<td style="width: 60px;">Action</td>
			</tr>

			<%if(ov.getDiagnoses().size()==0){ %>
			<tr>
				<td  colspan="3" style="text-align: center;">No Diagnoses on record</td>
			</tr>
			<%} else { 
					for(DiagnosisBean d : ov.getDiagnoses()) { %>
			<tr>
				<td align=center><%=d.getICDCode()%></td>
				<td ><%=d.getDescription()%></td>
				<td ><a
					href="javascript:removeID('removeDiagID','<%=d.getOvDiagnosisID()%>');">Remove</a></td>
			</tr>
			<%		}
				}%>
			<tr>
				<th colspan="3" style="text-align: center;">New</th>
			</tr>
			<tr>
				<td colspan="3" align=center><select name="addDiagID" style="font-size:10">
					<option value="">-- None Selected --</option>
					<%for(DiagnosisBean diag : prodDAO.getICDCodesDAO().getAllICDCodes()) { %>
					<option value="<%=diag.getICDCode()%>"><%=diag.getICDCode()%>
					- <%=diag.getDescription()%></option>
					<%}%>
					</select>
					<input type="submit" value="Add Diagnosis">
				</td>
			</tr>
		</table>
	</div>
	<div style="display: inline-table; margin-right:10px;">
		<table class="fTable" align="center">
			<tr>
				<th colspan="3">Procedures</th>
			</tr>
			<tr class="subHeader">
				<td>CPT Code</td>
				<td>Description</td>
				<td style="width: 60px;">Action</td>
			</tr>
			<% if (0 == ov.getProcedures().size()) { %>
			<tr>
				<td colspan="3" style="text-align: center;">No Procedures on record</td>
			</tr>
			<% } 
			   else { %>
			<% for (ProcedureBean proc : ov.getProcedures()) { 
				if (null == proc.getAttribute() || !proc.getAttribute().equals("immunization")) {%>
			<tr>
				<td align="center"><%=proc.getCPTCode()%></td>
				<td ><%=proc.getDescription()%></td>
				<td ><a href="javascript:removeID('removeProcID','<%=proc.getOvProcedureID()%>');">Remove</a></td>
			</tr>
			<% } } } %>
			<tr>
				<th colspan="3" style="text-align: center;">New</th>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<select name="addProcID" style="font-size: 10px;">
						<option value="">-- Please Select a Procedure --</option>
						<% for (ProcedureBean proc : prodDAO.getCPTCodesDAO().getAllCPTCodes()) {
							if (null == proc.getAttribute() || !proc.getAttribute().equals("immunization")) { %>
						<option value="<%=proc.getCPTCode() %>"><%=proc.getCPTCode() %> - <%=proc.getDescription() %></option>
						<% } } %>
					</select>
					<input type="submit" name="addP" value="Add Procedure" >
				</td>
			</tr>
		</table>
	</div>
	<div style="display: inline-table;">
		<table class="fTable" align="center">
			<tr>
				<th colspan="3">Immunizations</th>
			</tr>
			<tr class="subHeader">
				<td>CPT Code</td>
				<td>Description</td>
				<td style="width: 60px;">Action</td>
			</tr>
			<% if (0 == ov.getProcedures().size()) { %>
			<tr>
				<td colspan="3" style="text-align: center;">No immunizations on record</td>
			</tr>
			<% } 
			   else { %>
			<%	for (ProcedureBean proc : ov.getProcedures()) { 
					if (null != proc.getAttribute() && proc.getAttribute().equals("immunization")) { %>
			<tr>
				<td align="center"><%=proc.getCPTCode()%></td>
				<td ><%=proc.getDescription()%></td>
				<td ><a href="javascript:removeID('removeImmunizationID','<%=proc.getOvProcedureID()%>');">Remove</a></td>
			</tr>
			<% } } } %>
			<tr >
				<th colspan="3" style="text-align: center;">New</th>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<select name="addImmunizationID" style="font-size: 10px;">
						<option value="">-- Please Select a Procedure --</option>
						<% for (ProcedureBean proc : prodDAO.getCPTCodesDAO().getImmunizationCPTCodes()) {%>
							<option value="<%=proc.getCPTCode()%>"><%=proc.getCPTCode()%> - <%=proc.getDescription()%></option>
						<% } %>
					</select>
					<input type="submit" name="addImmu" value="Add Immunization" >
				</td>
			</tr>
		</table>
	</div>
</div>

<br /><br /><br />
<itrust:patientNav />
<br />

</form>

<%@include file="/footer.jsp" %>
