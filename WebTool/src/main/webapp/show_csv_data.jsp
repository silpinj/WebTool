<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.File" %>
<%@page import="java.io.FileReader" %> 
<%@page import="java.io.BufferedReader" %> 
<%@page import="java.util.ArrayList" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Web Tool</title>
</head>
<jsp:include page="/layoutgeneral.jsp"></jsp:include>
<body>

<h2 align="center"> Your CSV file: </h2>
<br><br>
<table width="60%" border=1 align="center">
	<%
	File csv = null;
	if(request.getParameter("basic")!=null && request.getParameter("basic").equals("1")) {
	csv = new File("/Users/silviapinilla/SILVIA/Universidad/Master Teleco/TFM/workspace/ProjectTFM/src/main/webapp/Basic-Results.csv");
	}
	if(request.getParameter("advanced")!=null && request.getParameter("advanced").equals("1")) {
	csv = new File("/Users/silviapinilla/SILVIA/Universidad/Master Teleco/TFM/workspace/ProjectTFM/src/main/webapp/Advanced-Results.csv");
	}
	if(request.getParameter("covid")!=null && request.getParameter("covid").equals("1")) {
	csv = new File("/Users/silviapinilla/SILVIA/Universidad/Master Teleco/TFM/workspace/ProjectTFM/src/main/webapp/Covid-Results.csv");
	}
	if(csv != null) {
	BufferedReader reader = new BufferedReader(new FileReader(csv));
	String line = reader.readLine();
	int count = 0;
	while(line != null) {
		String[] values_separated = line.split(";");
		for (int j = 0; j < values_separated.length; j++) { %>
			<td align="center"> <%=values_separated[j] %> </td>
		<%	
		}
		%> </tr> <%
		count++;
		if(count == 10) {
			%>
			<tr> <%
			for (int j = 0; j < values_separated.length; j++) { %>
			<td align="center"> ... </td>
			<% } %>
			</tr> <%
			break;
			}
		%>
		<tr>
		<%
		line = reader.readLine();
	}
	reader.close();
	}
	%>
</table>
<p align="center"> For full results, click on "Download CSV" </p>
<br><br><br>
<center>
<%
	if(request.getParameter("basic")!=null && request.getParameter("basic").equals("1")) { %>
		<input type="button" value="Download CSV" onclick="downloadCSVBasic()">
	<%}
	if(request.getParameter("advanced")!=null && request.getParameter("advanced").equals("1")) {%>
		<input type="button" value="Download CSV" onclick="downloadCSVAdvanced()">
	<%}
	if(request.getParameter("covid")!=null && request.getParameter("covid").equals("1")) {%>
	<input type="button" value="Download CSV" onclick="downloadCSVCovid()">
<%}
	%>

</center>

<script type=text/javascript>
		    
	function downloadURI(uri, name) {
		  var link = document.createElement("a");
		  link.download = name;
		  link.href = uri;
		  document.body.appendChild(link);
		  link.click();
		  document.body.removeChild(link);
		  delete link;
	}
	
	function downloadCSVBasic() {
		downloadURI("http://localhost:8080/ProjectTFM/Basic-Results.csv","Basic-Results.csv");
	}
	function downloadCSVAdvanced() {
		downloadURI("http://localhost:8080/ProjectTFM/Advanced-Results.csv","Advanced-Results.csv");
	}
	function downloadCSVCovid() {
		downloadURI("http://localhost:8080/ProjectTFM/Covid-Results.csv","Covid-Results.csv");
	}
			
</script>

<jsp:include page="/end.jsp"></jsp:include>
</body>
</html>