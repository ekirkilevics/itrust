<%@page import="java.sql.*"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<html>
<head>
	<title>Display Database</title>
	<style type="text/css">
		body {
		margin: 4px;
		font-family: Arial;
		font-size: 0.8em;
		}
		.results { 
		 border-collapse: collapse;
		}
		.results tr th {
		 font-size: 0.9em;
		 padding: 0px 5px 0px 5px;
		 background-color: Navy;
		 color: White;
		}
		.results tr td {
		 font-size: 0.8em;
		 padding: 0px 5px 0px 5px;
		}
		.results tr th, .results tr td {
		 border: 1px solid Gray;
		}
	</style>
</head>
<body>
<a href="/iTrust">Back to iTrust</a> - <a href="displayWikiDatabase.jsp">View wiki format</a>
<h2>FOR TESTING PURPOSES ONLY</h2>
<%
	Connection conn = DAOFactory.getProductionInstance().getConnection();
	ResultSet tableRS = conn.createStatement().executeQuery("show tables");
	while(tableRS.next()){
		String tableName = tableRS.getString(1);
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM " + tableName);
		int numCol = rs.getMetaData().getColumnCount();
		%><b><%=tableName%></b><br /><table class="results"><tr><%
		for(int i=1; i<=numCol;i++){
			%><th><%=rs.getMetaData().getColumnName(i)%></th><%
		}
		%></tr><%
		while(rs.next()){
			%><tr><%
			for(int i=1;i<=numCol;i++){
				try{
					String data = rs.getString(i);
					%><td><%=data%></td><%
				} catch(SQLException e){
					%><td>--Error in date, might be empty--</td><%
				}
			}
			%></tr><%
		}
		%></table><br /><br /><%
	}
	conn.close();
%>
</body>
</html>
