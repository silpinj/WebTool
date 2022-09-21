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
	<center>
		<h4>
			This application allows you to search for traffic in certain
			districts and areas of Madrid in a specified time interval. <br>This
			traffic can be compared with the incidence of Covid-19 cases in that
			area.
		</h4>
		<br>
		<h2>What do you want to do?</h2>
		<table width="50%" border="1">
			<tr>
			<td width="25%" align="center">
				<form action="http://localhost:8080/WebTool/map_measuringpoints.jsp">
					<input type="submit" value="Look for measuring points"/>
				</form>
			</td>
			<td align="center">
			<p>See measuring points in a district specified and explore the map of the zone.</p>
			</td>
			</tr>
			<tr>
			<td width="25%" align="center">
				<form action="http://localhost:8080/WebTool/search_basictraffic.jsp">
					<input type="submit" value="Basic traffic search" />
				</form>
			</td>
			<td align="center">
			<p> Select a district of Madrid.
			<br> See the average traffic intensity obtained by the measurement points of the district.</p>
			</td>
			</tr>
			<tr>
			<td width="25%" align="center">
				<form action="http://localhost:8080/WebTool/search_advancedtraffic.jsp">
					<input type="submit" value="Advanced traffic search" />
				</form>
			</td>
			<td align="center">
			<p>Specify GPS coordenates, a number of measurement points, and the type of measurement point.
			<br> See the average traffic intensity on the circumference with center at the specified coordinates 
			<br> and radius encompassing at least the specified number and type of sensors specified.</p>
			</td>
			</tr>
			<tr>
			<td width="25%" align="center">
				<form action="http://localhost:8080/WebTool/search_covid_traffic.jsp">
					<input type="submit" value="COVID-19 search" />
				</form>
			</td>
			<td align="center">
			<p>Specify a district of Madrid.
			<br>See the average COVID-19 active cases and Cumulative Incidence Rate on the district.</p>
			</td>
			</tr>
		</table>
	</center>
	<br><br>
	<jsp:include page="/end.jsp"></jsp:include>
</body>
</html>