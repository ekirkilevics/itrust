
<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Please Select a Personnel";
%>

<%@include file="/header.jsp" %>

<h1>Please Select a Personnel</h1>

<%	
String mid = request.getParameter("UID_PERSONNELID");
session.setAttribute("mid", mid);
if (null != mid && !"".equals(mid)) {
	response.sendRedirect(request.getParameter("forward"));
}
%>

<form method="post">
<input type="hidden" name="forward" value="<%=request.getParameter("forward") %>">
<table>
	<tr>
		<td><b>Personnel:</b></td>
		<td style="width: 150px; border: 1px solid Gray;">
			<input name="UID_PERSONNELID" value="" type="hidden">
			<span id="NAME_PERSONNELID" name="NAME_PERSONNELID">Not specified</span>
		</td>
		<td>
			<%@include file="/util/getUserFrame.jsp" %>
			<input type="button" onclick="getUser('PERSONNELID');" value="Find User" >
		</td>
	</tr>
	<tr>
		<td align=center colspan=2><input type="submit" value="Select Personnel"></td>
	</tr>
</table>
</form>

<%@include file="/footer.jsp" %>

