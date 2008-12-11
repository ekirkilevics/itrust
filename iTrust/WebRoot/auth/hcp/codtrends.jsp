<%@page import="java.util.List"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="edu.ncsu.csc.itrust.action.CODAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.CODForm"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.CODBean"%>
<%@page import="edu.ncsu.csc.itrust.enums.Gender"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Cause of Death Trends";
%>

<%@include file="/header.jsp" %>

<div align=center>
	<table>
		<tr>
			<td>
<%
	CODAction action = new CODAction(prodDAO, loggedInMID.longValue());
	CODForm form;
	
	if("true".equals(request.getParameter("formIsFilled"))){
		
		form = new BeanBuilder<CODForm>().build(request.getParameterMap(), new CODForm());
		form.setGen(Gender.parse(request.getParameter("gen")));
		try{
			action.setForm(form);
		} catch(FormValidationException e){
			e.printHTML(pageContext.getOut());
		}
		
			List<CODBean> allPs = action.getAllCODs();
			List<CODBean> myPs = action.getMyCODs();
		%>
		
				<table class="fTable">
					<tr>
						<th colspan=4>All Patients</th>
					</tr>
		<%
		if (allPs.size() == 0) {
%>
					<tr>
						<td colspan=4>No patients found.</td>
					</tr>
<%
		}
		else {
%>
					<tr class="subHeader">
						<th>Rank</th>
						<th>Description</th>
						<th>ICD Code</th>
						<th>Quantity</th>
					</tr>
<%	
			while (! allPs.isEmpty())
			{
				CODBean thisOne = allPs.remove(0);
				out.println("<tr><td>" + thisOne.getRank() + ".</td><td>" 
					+ thisOne.getDiagnosisName() + "</td><td>" 
					+ thisOne.getIcdCode() + "</td><td>" 
					+ thisOne.getTotal() 
					+ "</td></tr>");
			}
		}
		%>
				</table>
			</td>
			<td width="15px">&nbsp;</td>
			<td>
				<table class="fTable">
					<tr>
						<th colspan=4>My Patients</th>
					</tr>
<% 
		if (myPs.size() == 0) {
%>
					<tr>
						<td colspan=4>No patients found.</td>
					</tr>
<%
		}
		else {
%>
					<tr class="subHeader">
						<th>Rank</th>
						<th>Description</th>
						<th>ICD Code</th>
						<th>Quantity</th>
					</tr>
<%		
			while (! myPs.isEmpty()) {
				CODBean thisOne = myPs.remove(0);
				out.println("<tr><td>" + thisOne.getRank() + ".</td><td>" 
					+ thisOne.getDiagnosisName() + "</td><td>" 
					+ thisOne.getIcdCode() + "</td><td>" 
					+ thisOne.getTotal() 
					+ "</td></tr>");
			}
		}
%>
				</table>
<%		
	}
	else {
		form = new CODForm();
	}
	%>
			</td>
		</tr>
	</table>
	<br />
<form action="codtrends.jsp" method="post">
<input type=hidden name="formIsFilled" value="true">
From: <select name="afterYear">
		<%
		
		for (int year = CODForm.getCurrentYear(); year > 1900; year--)
		{
			if (form.getAfterYear().equals("" + year))
				out.println("<option selected value='" + year + "'>" + year + "</option>");
			else
				out.println("<option value='" + year + "'>" + year + "</option>");
		}
		%>
</select>
To: 
<select name="beforeYear">
		<%
		for (int year = CODForm.getCurrentYear(); year > 1900; year--)
		{
			if (form.getBeforeYear().equals("" + year))
				out.println("<option selected value='" + year + "'>" + year + "</option>");
			else
				out.println("<option value='" + year + "'>" + year + "</option>");
		}
		%>
</select>
Gender: <select name="gen">
<%

for (int i = 0; i < Gender.values().length; i++)
		{
		
			if (form.getGen().compareTo(Gender.values()[i]) == 0)
			{
				out.println("<option selected value='" + Gender.values()[i] + "'>" + Gender.values()[i] + "</option>");
			}
			else
			{
				out.println("<option value='" + Gender.values()[i] + "'>" + Gender.values()[i] + "</option>");
			}
		}
%>

</select>
<input type="submit" value="View">
</form>
</div>	
<br />

<%@include file="/footer.jsp" %>
