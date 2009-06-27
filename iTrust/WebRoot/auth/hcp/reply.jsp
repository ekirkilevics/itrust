<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.SendMessageAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.MessageBean"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Reply";
%>

<%@include file="/header.jsp" %>

<%
	SendMessageAction action = new SendMessageAction(prodDAO, loggedInMID);
	MessageBean original = null;
	if (request.getParameter("msg") != null) {
		String msgParameter = request.getParameter("msg");
		int msgIndex = 0;
		try {
			msgIndex = Integer.parseInt(msgParameter);
		} catch (NumberFormatException nfe) {
			response.sendRedirect("viewMyMessages.jsp");
		}
		List<MessageBean> messages = null; 
		if (session.getAttribute("messages") != null) {
			messages = (List<MessageBean>) session.getAttribute("messages");
		} else {
			response.sendRedirect("viewMyMessages.jsp");
		}
		original = messages.get(msgIndex);
		session.removeAttribute("messages");
		session.setAttribute("original", original);
	} else if (request.getParameter("messageBody") != null) {
		if (session.getAttribute("original") != null) {
			original = (MessageBean) session.getAttribute("original");
			MessageBean message = new MessageBean();
			message.setBody(request.getParameter("messageBody"));
			message.setFrom(loggedInMID);
			message.setTo(original.getFrom());
			action.sendMessage(message);
			session.removeAttribute("original");
			response.sendRedirect("home.jsp");
		} else {
			response.sendRedirect("viewMyMessages.jsp");
		}
	}
%>

	<h2>Reply</h2>
	<h4>to a message from <%= action.getPatientName(original.getFrom()) %>:</h4>
	<form method="post" action="reply.jsp">
		<textarea name="messageBody" cols="100" rows="10"></textarea><br />
		<br />
		<input type="submit" value="Send" name="sendMessage"/>
	</form>
	<br />
	<br />


<%@include file="/footer.jsp" %>
