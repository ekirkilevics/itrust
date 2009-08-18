<%@page import="java.net.URLEncoder" %>
<%@page import="java.net.URLDecoder" %>
<%@page import="java.util.List"%>

<%@page import="edu.ncsu.csc.itrust.beans.MedicationBean"%>
<%@page import="edu.ncsu.csc.itrust.action.UpdateNDCodeListAction"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Maintain ND Codes";
%>

<%@include file="/header.jsp" %>

<%
	UpdateNDCodeListAction ndUpdater = new UpdateNDCodeListAction(prodDAO, loggedInMID.longValue());
	
	String headerMessage = "Viewing Current ND Codes";
	String code1 = request.getParameter("code1") != null
			? request.getParameter("code1").trim()
			: "";
	String code2 = request.getParameter("code2") != null
			? request.getParameter("code2").trim()
			: "";
	String code = code1 + code2;
	
	if (request.getParameter("add") != null || request.getParameter("update") != null) {
		try {
			MedicationBean med =
				new MedicationBean(code, request.getParameter("description"));
			headerMessage = (request.getParameter("add") != null)
					? ndUpdater.addNDCode(med)
					: ndUpdater.updateInformation(med);
		} catch(FormValidationException e) {
%>
			<div align=center>
				<span class="iTrustError"><%=e.getMessage() %></span>
			</div>
<%
			headerMessage = "Validation Errors";
		}
		
	}
			
	String headerColor = (headerMessage.indexOf("Error") > -1)
			? "#ffcccc"
			: "#00CCCC";
%>

<br />
<div align=center>
<form name="mainForm" method="post">
<input type="hidden" id="updateID" name="updateID" value="">
<input type="hidden" id="oldDescrip" name="oldDescrip" value="">
<script type="text/javascript">
	function fillUpdate(code) {
		document.getElementById("code1").value = code.substring(0,5);
		document.getElementById("code2").value = code.substring(5);
		document.getElementById("description").value
			= unescape(document.getElementById("UPD" + code).value);
		document.getElementById("oldDescrip").value
			= unescape(document.getElementById(
					"UPD" + code).value);
	}
</script>


<span class="iTrustMessage"><%=headerMessage %></span>

<br />
<br />

<table class="fTable" align="center">
	<tr>
		<th colspan="3">Update ND Code List</th>
	</tr>
	<tr class="subHeader">
		<th>Code</th>
		<th>Description</th>
	</tr>
	<tr>
		<td style="padding-right: 10px;">
			<input  type="text"
					id="code1"
					name="code1"
					size="5"
					maxlength="5"
			/>-<input type="text"
					id="code2"
					name="code2"
					size="4"
					maxlength="4"/>
		</td>
		<td>
			<input  type="text"
					id="description"
					name="description"
					size="40"
					maxlength="50" />
		</td>
	</tr>
</table>
<br />
<input type="submit" name="add" value="Add Code" />
<input type="submit" name="update" value="Update Code" />

<br />
<br />

<table class="fTable" align="center">
	<tr>
		<th colspan="2">Current NDCs</th>
	</tr>
	<tr class="subHeader">
		<th>Code</th>
		<th>Description</th>
	</tr>
	<%
		List<MedicationBean> medList = prodDAO.getNDCodesDAO().getAllNDCodes();
		String tempCode = "";
		String tempDescrip = "";
		String escapedDescrip = "";
		for (MedicationBean medEntry : medList) {
			tempCode = medEntry.getNDCode();
			tempDescrip = medEntry.getDescription();
			escapedDescrip = URLEncoder.encode(tempDescrip, "UTF-8").replaceAll("\\+", "%20");
	%>
		<tr>
			<td><%=5 > tempCode.length() ? tempCode : tempCode.substring(0, 5)
				%>-<%=5 > tempCode.length() ? "" : tempCode.substring(5) %>
			</td>
			<td><a href="javascript:void(0)"
					onclick="fillUpdate('<%=tempCode %>')"
						><%=tempDescrip %></a>
				<input type="hidden"
						id="UPD<%=tempCode %>"
						name="UPD<%=tempCode %>"
						value="<%=escapedDescrip %>">
			</td>
		</tr>
	<% } %>
</table>
</form>
</div>
<br />

<%@include file="/footer.jsp" %>
