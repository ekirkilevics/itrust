<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust Logout";

session.invalidate();
validSession = false;
%>

<%@include file="/header.jsp" %>

<div style="text-align: center;">
	<h2>Goodbye <%=userName %>!</h2>
	You have been logged out of iTrust
</div>

<%@include file="/footer.jsp" %>
