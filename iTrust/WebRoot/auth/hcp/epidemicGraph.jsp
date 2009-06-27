<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust" %>
<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@page import="edu.ncsu.csc.itrust.beans.forms.EpidemicForm"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.action.EpidemicDetectionAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.forms.EpidemicReturnForm"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Diagnostic Trends";
%>

<%@include file="/header.jsp" %>

<script>
function showEpidemic(){
	document.forms[0].useEpidemic.value="true";
	document.getElementById("epidemicTab").className="activeTab";
	document.getElementById("diagnosisTab").className="inactiveTab";
	document.getElementById("epidemicParams").style.display="inline";
	document.getElementById("diagnosisParams").style.display="none";
}
function showDiagnosis(){
	document.forms[0].useEpidemic.value="false";
	document.getElementById("epidemicTab").className="inactiveTab";
	document.getElementById("diagnosisTab").className="activeTab";
	document.getElementById("epidemicParams").style.display="none";
	document.getElementById("diagnosisParams").style.display="inline";
}
</script>
<%
	EpidemicDetectionAction action = new EpidemicDetectionAction(prodDAO, loggedInMID.longValue());
	EpidemicForm form;
	EpidemicReturnForm rForm = null;
	boolean showGraph = false;
	if("true".equals(request.getParameter("formIsFilled"))){
		form = new BeanBuilder<EpidemicForm>().build(request.getParameterMap(), new EpidemicForm());
		try{
			rForm = action.execute(form);
			showGraph = true;
		} catch(FormValidationException e){
			e.printHTML(pageContext.getOut());
		}
	} else{
		form = new EpidemicForm();
	}
%>

<div align=center>
<form action="epidemicGraph.jsp" method="post">
<input type=hidden name="formIsFilled" value="true">
<table class="fTable">
	<tr>
		<th colspan="100%">View Diagnosis Counts</th>
	</tr>
	<tr>
		<td>Show date ending in <input type="text" name="date" value="<%=form.getDate()%>" size=10 maxlength=10> 
			<input type="button" value="Pick Date" onclick="displayDatePicker('date');"></td>
	</tr>
	<tr>
		<td>Show and detect up to <input type="text" name="weeksBack" value="<%=form.getWeeksBack()%>" size=2 maxlength=2>
		weeks back</td>
	</tr>
	<tr>
		<td>Check in the region of zip code <input type="text" name="zip" value="<%=form.getZip()%>" size=5 maxlength="5"></td>
	</tr>
	<tr>
		<td>Show state counts in <itrust:state name="state" value="<%=form.getState()%>"/></td>
	</tr> 
</table>
<br />
<span id="epidemicTab"  onclick="javascript:showEpidemic();">Detect Epidemic</span>
&nbsp;&nbsp;&nbsp;
<span id="diagnosisTab"  onclick="javascript:showDiagnosis();">Display Diagnosis Counts</span>
<br />
<br />
<input type="hidden" name="useEpidemic" value="true">
<div id="epidemicParams" style="display: inline;">
	<select name="detector">
		<option value="">--Select Diagnosis--</option>
		<option value="malaria">Malaria (84.x)</option>
		<option value="influenza">Influenza (487.x)</option>
	</select>
</div>
<div id="diagnosisParams" style="display: none;">
	ICD9CM code is in the range <input type="text" name="icdLower" size=6 maxlength="6"> 
	to <input type="text" name="icdUpper" size=6 maxlength="6">
</div>
<br />
<br />
<br />
<input type="submit" value="View">
<br /><br />
<%
if(rForm!=null){
	if(rForm.isUsedEpidemic()){
		%><h2><%=rForm.getEpidemicMessage()%></h2><%
	}
	if(showGraph){ %>
		<br /><br />
				<img src="epidemicGraphImage.jsp?<%=rForm.getImageQuery()%>" alt="Epidemic Graph with diagnosis counts"><br />
	<%}
}
%>
</form>
</div>
<br />

<%@include file="/footer.jsp" %>
