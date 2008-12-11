<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Please Select a Patient";
%>

<%@include file="/header.jsp" %>

<%
	String uid_pid = request.getParameter("UID_PATIENTID");
	session.setAttribute("pid", uid_pid);
	if (null != uid_pid && !"".equals(uid_pid)) {
		response.sendRedirect(request.getParameter("forward"));
	}
%>

<%@include file="/util/getUserFrame.jsp"%>
				
<form id="mainForm" action="getPatientID.jsp" method="post">
	<table>
		<tr>
			<td><b>Patient:</b></td>
			<td style="width: 150px; border: 1px solid Gray;">
				<input name="forward" type="hidden" value="<%=request.getParameter("forward") %>" />
				<input name="UID_PATIENTID" type="text" value="" />
				<span name="NAME_PATIENTID">Not specified</span>
				</td>
			<td>
				<input type="button" onclick="getUser('PATIENTID');" value="Find User" />
			</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="submit" value="Select Patient" />
		</td>
	</tr>
</table>
</form>

<%@include file="/footer.jsp" %>
