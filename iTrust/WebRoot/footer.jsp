				</div>
			</div>	
			<div style="clear: both;">
			</div>
		</div>

	
		<div class="iTrustFooter">
			<div style="float: left; width: 48%; margin-left: 10px;">
<%
			if( ! "true".equals(System.getProperty("itrust.production") ) ) { 
%>
				  <a class="iTrustNavlink" href="/iTrust/util/andystestutil.jsp">Test Data Generator</a>
				| <a class="iTrustNavlink" href="/iTrust/util/transactionLog.jsp">Transaction Log</a>
				| <a class="iTrustNavlink" href="/iTrust/util/displayDatabase.jsp">Display Database</a>
				| <a class="iTrustNavlink" href="/iTrust/util/showFakeEmails.jsp">Show Fake Emails</a>
<%
			}
%>
			</div>
			<div style="float: right; width: 48%; text-align: right; margin-right: 10px;">
				  <a class="iTrustNavlink" href="mailto:nobody@itrust.com">Contact</a>
				| <a class="iTrustNavlink" href="/iTrust/privacyPolicy.jsp">Privacy Policy</a>
				| <a class="iTrustNavlink" href="http://agile.csc.ncsu.edu/iTrust/">iTrust v6.0b</a>
			</div>
			<div style="clear: both;">
			</div>
		</div>

	</body>
</html>
