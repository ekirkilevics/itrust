<%@page import="edu.ncsu.csc.itrust.beans.OfficeVisitBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.LabProcedureBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyRecordsAction"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DateFormat"%>

<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View Lab Procedures";
%>

<%@include file="/header.jsp"%>
<%
loggingAction.logEvent(TransactionType.LAB_RESULTS_VIEW, loggedInMID.longValue(), 0, "");

ViewMyRecordsAction action = new ViewMyRecordsAction(prodDAO, loggedInMID.longValue());
List<LabProcedureBean> procs = action.getLabs();
action.setViewed(procs);

%>

<br />
<table class="fTable" align="center" id="labProceduresTable">
	<tr>
		<th colspan="9">Lab Procedures</th>
	</tr>
	<tr class="subHeader">
	    <td>HCP</td>
        <td>Office Visit Date</td>
        <td>Lab Code</td>
        <td>Status</td>
        <td>Commentary</td>
        <td>Numerical<br/>Result</td>
        <td colspan="2">Confidence<br/>Interval</td>
        <td>Updated Date</td>
	</tr>
<%
	if(procs.size() > 0 ) {
		for (LabProcedureBean bean : procs) {
			OfficeVisitBean ovbean = action.getCompleteOfficeVisit(bean.getOvID());
			String ovid = Long.toString(ovbean.getID());
			String hcpname = action.getPersonnel(ovbean.getHcpID()).getFullName();
			String ovdate = ovbean.getVisitDateStr();
			String status = bean.getStatus();
			String commentary = "";
			String numericalResult = "";
			String lowerBound = "";
			String upperBound = "";
			if (status.equals(LabProcedureBean.Completed)) {
	            commentary =       StringEscapeUtils.escapeHtml("" + (bean.getCommentary()));
	            numericalResult =  StringEscapeUtils.escapeHtml("" + (bean.getNumericalResult()));
	            lowerBound =       StringEscapeUtils.escapeHtml("" + (bean.getLowerBound()));
	            upperBound =       StringEscapeUtils.escapeHtml("" + (bean.getUpperBound()));
			}
%>
			<tr>
				<td><%= StringEscapeUtils.escapeHtml(hcpname) %></td>
                <td>
                    <a href="viewOfficeVisit.jsp?ovID=<%= ovid %>"><%= StringEscapeUtils.escapeHtml(ovdate) %></a>
                </td>
				<td><%= StringEscapeUtils.escapeHtml("" + (bean.getLoinc())) %></td>
		        <td><%= status %></td>
		        <td><%= commentary %></td>
		        <td><%= numericalResult %></td>
		        <td><%= lowerBound %></td>
		        <td><%= upperBound %></td>
		        <td><%= StringEscapeUtils.escapeHtml("" + (bean.getTimestamp())) %></td>
			</tr>
<%
		}
	}
	else {
%>
		<tr>
			<td colspan=9 align=center>No Data</td>
		</tr>
<%
	}
%>
</table>
<br />
<%@include file="/footer.jsp"%>
