<%@page import="edu.ncsu.csc.itrust.DBUtil"%>

<%
		
if(!DBUtil.canObtainProductionInstance()){
	response.sendError(503);
}

if(request.getUserPrincipal() != null) {
	userName = authDAO.getUserName(Long.valueOf(request.getUserPrincipal().getName()));
}

if (request.getAuthType() != null) {
	
	if (request.getSession(false) != null) {
		boolean isValidLogin = loginFailureAction.isValidForLogin();
		if(!isValidLogin) {
			session.invalidate();
			return;
		}
		else {
		
			loggedInMID = request.getUserPrincipal()==null 
			            ? new Long(0L) 
			            : new Long(Long.valueOf(request.getUserPrincipal().getName()));
			
			session.setAttribute("loggedInMID", loggedInMID);
		}
	}

} 
%>
