<%@page contentType="image/png" %>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.*"%>
<%@page import="edu.ncsu.csc.itrust.*"%>
<%@page import="edu.ncsu.csc.itrust.action.*"%>
<%@page import="edu.ncsu.csc.itrust.dao.*"%>

<%
	try {
		EpidemicForm form = new BeanBuilder<EpidemicForm>().build(request.getParameterMap(), new EpidemicForm());
		EpidemicDetectionImageAction action = new EpidemicDetectionImageAction(DAOFactory.getProductionInstance());
		ImageIO.write(action.createGraph(form), "png", response.getOutputStream());
	} catch (Throwable t) {
		t.printStackTrace();
		return;
	}
%>