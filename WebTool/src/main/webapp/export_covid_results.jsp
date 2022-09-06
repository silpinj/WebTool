<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Web Tool</title>
</head>
<jsp:include page="/layoutgeneral.jsp"></jsp:include>
<body>
	
	<table width="95%">
		<tr>
			<td align="center" valign="top">
				<br><br>
				<h2> Specify the type of data to export:</h2> 
				<br>
				<form action="ServletExportCovidResults" method=POST>
				<SELECT name="export">
					<option value="1" SELECTED> Export available data for the days specified
					<option value="2" SELECTED> Export average total data
				</SELECT>
				<input type="hidden" name="zone" value=<%= request.getParameter("zone") %> />
				<input type="hidden" name="district" value=<%= request.getParameter("district") %> />
				<input type="hidden" name="day1" value=<%= request.getParameter("day1") %> />
				<input type="hidden" name="day2" value=<%= request.getParameter("day2") %> />
				<input type="hidden" name="month1" value=<%= request.getParameter("month1") %> />
				<input type="hidden" name="month2" value=<%= request.getParameter("month2") %> />
				<input type="hidden" name="year1" value=<%= request.getParameter("year1") %> />
				<input type="hidden" name="year2" value=<%= request.getParameter("year2") %> />
				<input type="hidden" name="cases" value=<%= request.getParameter("cases") %> />
				<input type="hidden" name="tia" value=<%= request.getParameter("tia") %> />
				<input type="hidden" name="total_cases" value=<%= request.getParameter("total_cases") %> />
				<input type="hidden" name="total_tia" value=<%= request.getParameter("total_tia") %> />
				<input type="submit" value="Export CSV" />  
				</form>
			</td>
		</tr>
	</table>
	<br><br><br><br><br><br><br><br><br><br><br><br>

<jsp:include page="/end.jsp"></jsp:include>
</body>
</html>