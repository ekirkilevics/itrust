<%@page import="edu.ncsu.csc.itrust.action.ViewMyAccessLogAction"%>
<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.TransactionBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="java.util.ArrayList"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View My Access Log";
%>

<%@include file="/header.jsp"%>

<%
session.removeAttribute("personnelList");

	ViewMyAccessLogAction action = new ViewMyAccessLogAction(DAOFactory.getProductionInstance(), loggedInMID);
	List<TransactionBean> accesses;
	try{
		accesses = action.getAccesses(request.getParameter("startDate"), request.getParameter("endDate"));
	} catch(FormValidationException e){
		e.printHTML(pageContext.getOut());
		accesses = action.getAccesses(null,null);
	}
	
	
%>

<br />
<table class="fTable" align='center'>
	<tr>
		<th>Date</th>
		<th>Personnel</th>
		<th>Description</th>
	</tr>
<%
	boolean hasData = false;
	List<PersonnelBean> personnelList = new ArrayList<PersonnelBean>();
	int index = 0;
	for(TransactionBean t : accesses){ 
		PersonnelBean hcp = new PersonnelDAO(DAOFactory.getProductionInstance()).getPersonnel(t.getLoggedInMID());
		if (hcp != null) {
			hasData = true;
%>
	<tr>
		<td ><%=t.getTimeLogged()%></td>
		<td ><a href="/iTrust/auth/viewPersonnel.jsp?personnel=<%=index%>"><%=hcp.getFullName()%></a></td>
		<td ><%=t.getAddedInfo()%></td>		
	</tr>
<%
			PersonnelBean personnel = new PersonnelDAO(prodDAO).getPersonnel(t.getLoggedInMID());
			personnelList.add(personnel);
			index++;
		}
	}
	session.setAttribute("personnelList", personnelList);
	if(!hasData) {
%>
	<tr>
		<td colspan=3 align="center">No Data</td>
	</tr>
<%
	}
%>
</table>
<br />
<br />

<form action="viewAccessLog.jsp" method="post">

<div align=center>
<table class="fTable" align="center">
	<tr class="subHeader">
		<td>Start Date:</td>
		<td>
			<input name="startDate" value="<%=action.getDefaultStart(accesses)%>" size="10">
			<input type=button value="Select Date" onclick="displayDatePicker('startDate');">
		</td>
		<td>End Date:</td>
		<td>
			<input name="endDate" value="<%=action.getDefaultEnd(accesses)%>">
			<input type=button value="Select Date" onclick="displayDatePicker('endDate');">
		</td>
	</tr>
</table>
<br />
<input type="submit" name="submit" value="Filter Records">
</div
</form>

<%@include file="/footer.jsp"%>
