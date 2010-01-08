<%@include file="/global.jsp" %>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaImpl" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaResponse" %>


<%
pageTitle = "iTrust - Login";

/* Note: there are better ways of implementing this feature. See the comment in LoginFailureAction */
	LoginFailureAction action = new LoginFailureAction(prodDAO, request.getRemoteAddr());
	
	if("true".equals(request.getParameter("loginError"))) {
		loginMessage = action.recordLoginFailure();
	}
%>

<%@include file="/header.jsp" %>
<script type="text/javascript">
	function setCheck(){
		var chalVal = document.getElementById("recaptcha_challenge_field").value;
		var respVal = document.getElementById("recaptcha_response_field").value;
		var formVal = "true";
		document.getElementById("challengeField").value = chalVal;
		document.getElementById("responseField").value = respVal;
		document.getElementById("formFilled").value = formVal;
	}
</script>
<%
	if(action.isValidForLogin()) {
%>

<div style="text-align: center; font-size: +2em">
	Welcome to iTrust
</div>

<div style="margin-top: 15px; margin-right: 40px; height: 150px;">
iTrust is a medical application that provides patients with a means to keep up with their medical history and records as well as communicate with their doctors, including selecting which doctors to be their primary caregiver, seeing and sharing satisfaction results, and other tasks.
iTrust is also an interface for medical staff from various locations.  iTrust allows the staff to keep track of their patients through messaging capabilities, scheduling of office visits, diagnoses, prescribing medication, ordering and viewing lab results, among other funtions. 
</div>

<%
	} else { 
%>		
<form action="" method="post">
		<input type="hidden" id="formFilled" name="formFilled" value=<%=request.getParameter("formFilled") %> />
		<input type="hidden" id="challengeField" name="challengeField" value=<%=request.getParameter("challengeField") %> />
		<input type="hidden" id="responseField" name="responseField" value=<%=request.getParameter("responseField") %> />
<%		
		boolean checkCaptcha = false;
		if(request.getParameter("formFilled") != null){
			checkCaptcha = true;
		}
%>		
		
		You have too many failed logins times in a short span of time.<br />
		Please wait 15 minutes before logging in again or unlock the page by completing the CAPTCHA.

<%
	if(checkCaptcha == false){
%>

	<%
		/*This is from recaptcha.net, it provides a CAPTCHA client for our page to solve the lockout after three failed logins*/
		ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcxkQkAAAAAAJ7b-6b1AI8HXhKRmgZhoMbVfdIo ", "6LcxkQkAAAAAAPSUx5-r5rInTc8wry9IHnqo4nvo ", false);
		out.print(c.createRecaptchaHtml(null, null));
	%>
	<input type="submit" value="Submit" onclick="setCheck()" />
<%		
	} else{
       String remoteAddr = request.getRemoteAddr();
       ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
       reCaptcha.setPrivateKey("6LcxkQkAAAAAAPSUx5-r5rInTc8wry9IHnqo4nvo");

       String challenge = request.getParameter("challengeField");
       String uresponse = request.getParameter("responseField");
       System.out.println("Remote Addr is: " + remoteAddr + " Challenge is: " + challenge + " Uresponse is: " + uresponse);
       ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

       if (reCaptchaResponse.isValid()) {
         System.out.println("Answer was entered correctly!");
         System.out.println("Response: " + reCaptchaResponse);
         authDAO.resetLoginFailuresToZero(remoteAddr);
         response.sendRedirect("/iTrust/");
       } else {
             ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcxkQkAAAAAAJ7b-6b1AI8HXhKRmgZhoMbVfdIo ", "6LcxkQkAAAAAAPSUx5-r5rInTc8wry9IHnqo4nvo ", false);
             out.print(c.createRecaptchaHtml(null, null));
%>
       	  <input type="submit" value="Submit" onclick="setCheck()" />
</form>
<%
       }
	}
	}
%>

<%@include file="/footer.jsp" %>

