<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@main/dist/en/v6.14.1/css/ol.css"
	type="text/css">
<style>
.map {
	width: 95%;
	height: 450px;
}
</style>
<script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@main/dist/en/v6.14.1/build/ol.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.polyfill.io/v3/polyfill.min.js?features=fetch,requestAnimationFrame,Element.prototype.classList,TextDecoder"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/3.18.3/minified.js"></script>
<script src="https://unpkg.com/elm-pep@1.0.6/dist/elm-pep.js"></script>
<title>Web Tool</title>
</head>
<jsp:include page="/layoutgeneral.jsp"></jsp:include>
<body>
	<center>
		<h3>
			This application allows you to search for traffic in certain
			districts and areas of Madrid. <br>This traffic can be compared
			with the incidence of Covid-19 cases in that area.
		</h3>
		<br>
		<table width="95%">
			<tr>
				<td width="20%" align="center" valign="top">
					<h2>Search COVID-19 data:</h2> 
					<br>
					<form action="ServletCovid" method=POST>
						<table width="95%" height="100px" frame="box">
							<tr>
								<td width="35%" align="center">
									<p><b>Select Madrid district:</b></p> <SELECT name="zone">
										<option value="1" SELECTED>Centro
										<option value="2" SELECTED>Arganzuela
										<option value="3" SELECTED>Retiro
										<option value="4" SELECTED>Salamanca
										<option value="5" SELECTED>Chamartín
										<option value="6" SELECTED>Tetuán
										<option value="7" SELECTED>Chamberí
										<option value="8" SELECTED>Fuencarral-El Pardo
										<option value="9" SELECTED>Moncloa-Aravaca
										<option value="10" SELECTED>Latina
										<option value="11" SELECTED>Carabanchel
										<option value="12" SELECTED>Usera
										<option value="13" SELECTED>Puente de Vallecas
										<option value="14" SELECTED>Moratalaz
										<option value="15" SELECTED>Ciudad Lineal
										<option value="16" SELECTED>Hortaleza
										<option value="17" SELECTED>Villaverde
										<option value="18" SELECTED>Villa de Vallecas
										<option value="19" SELECTED>Vicálvaro
										<option value="20" SELECTED>San Blas-Canillejas
										<option value="21" SELECTED>Barajas
										<option value="22" SELECTED>All
								</SELECT>
								</td>
							</tr>
						</table>
						<br>
						<table width="95%" height="100px" frame="box">
							<tr>
								<td width="20%" align="right"><br>
									<p><b>View data from:</b></p></td>
								<td width="25%"><br> <SELECT name="from_day">
										<option value="01" SELECTED>1
										<option value="02" SELECTED>2
										<option value="03" SELECTED>3
										<option value="04" SELECTED>4
										<option value="05" SELECTED>5
										<option value="06" SELECTED>6
										<option value="07" SELECTED>7
										<option value="08" SELECTED>8
										<option value="09" SELECTED>9
										<option value="10" SELECTED>10
										<option value="11" SELECTED>11
										<option value="12" SELECTED>12
										<option value="13" SELECTED>13
										<option value="14" SELECTED>14
										<option value="15" SELECTED>15
										<option value="16" SELECTED>16
										<option value="17" SELECTED>17
										<option value="18" SELECTED>18
										<option value="19" SELECTED>19
										<option value="20" SELECTED>20
										<option value="21" SELECTED>21
										<option value="22" SELECTED>22
										<option value="23" SELECTED>23
										<option value="24" SELECTED>24
										<option value="25" SELECTED>25
										<option value="26" SELECTED>26
										<option value="27" SELECTED>27
										<option value="28" SELECTED>28
										<option value="29" SELECTED>29
										<option value="30" SELECTED>30
										<option value="31" SELECTED>31
								</SELECT> <SELECT name="from_month">
										<option value="01" SELECTED>January
										<option value="02" SELECTED>February
										<option value="03" SELECTED>March
										<option value="04" SELECTED>April
										<option value="05" SELECTED>May
										<option value="06" SELECTED>June
										<option value="07" SELECTED>July
										<option value="08" SELECTED>August
										<option value="09" SELECTED>September
										<option value="10" SELECTED>October
										<option value="11" SELECTED>November
										<option value="12" SELECTED>December
								</SELECT> <SELECT name="from_year">
										<option value="2020" SELECTED>2020
										<option value="2021" SELECTED>2021
										<option value="2022" SELECTED>2022
								</SELECT>
								</td>
								</tr>
								<tr>
								<td>
									<p align="right"><b>to:</b></p></td>
								<td width="25%">
									<SELECT name="to_day">
										<option value="01" SELECTED>1
										<option value="02" SELECTED>2
										<option value="03" SELECTED>3
										<option value="04" SELECTED>4
										<option value="05" SELECTED>5
										<option value="06" SELECTED>6
										<option value="07" SELECTED>7
										<option value="08" SELECTED>8
										<option value="09" SELECTED>9
										<option value="10" SELECTED>10
										<option value="11" SELECTED>11
										<option value="12" SELECTED>12
										<option value="13" SELECTED>13
										<option value="14" SELECTED>14
										<option value="15" SELECTED>15
										<option value="16" SELECTED>16
										<option value="17" SELECTED>17
										<option value="18" SELECTED>18
										<option value="19" SELECTED>19
										<option value="20" SELECTED>20
										<option value="21" SELECTED>21
										<option value="22" SELECTED>22
										<option value="23" SELECTED>23
										<option value="24" SELECTED>24
										<option value="25" SELECTED>25
										<option value="26" SELECTED>26
										<option value="27" SELECTED>27
										<option value="28" SELECTED>28
										<option value="29" SELECTED>29
										<option value="30" SELECTED>30
										<option value="31" SELECTED>31
								</SELECT> <SELECT name="to_month">
										<option value="01" SELECTED>January
										<option value="02" SELECTED>February
										<option value="03" SELECTED>March
										<option value="04" SELECTED>April
										<option value="05" SELECTED>May
										<option value="06" SELECTED>June
										<option value="07" SELECTED>July
										<option value="08" SELECTED>August
										<option value="09" SELECTED>September
										<option value="10" SELECTED>October
										<option value="11" SELECTED>November
										<option value="12" SELECTED>December
								</SELECT> <SELECT name="to_year">
										<option value="2020" SELECTED>2020
										<option value="2021" SELECTED>2021
										<option value="2022" SELECTED>2022
								</SELECT>
								</td>
							</tr>
						</table>
						<br> <br> <input type="submit" value="Confirmar">
					</form>
				</td>
				<td width="40%" align="center">
					<div id="map" class="map">
				    	<div id="popup"></div>
   					</div>
					<jsp:include page="map_covid.jsp"></jsp:include>
				</td>
				<% if(request.getParameter("district") != null) {%>
				<td width="20%" align="center" valign="top">
				<h2> Your search results: </h2>
				<p>
					<b>District specified: </b><br> <%= request.getParameter("district") %> 
				</p>
				<p>
					<b>Time interval specified:</b><br> <%=request.getParameter("day1") %>/<%=request.getParameter("month1") %>/<%=request.getParameter("year1") %> 
					- <%=request.getParameter("day2") %>/<%=request.getParameter("month2") %>/<%=request.getParameter("year2") %>
				</p>
				<!--  
				<br>
				<p>
					<b>Average traffic intensity: </b> <%=request.getParameter("intensity")%> vehicles/hour</p> 
					-->
				<h3>
					<b>Average COVID-19 data </b><br></h3>
					<p>
					<% if(request.getParameter("cases").equals("0.0") && request.getParameter("tia").equals("0.0")) {%>
						<p> There is no data for time interval specified. <br>
						COVID-19 data is available <br>from 26/02/2020 to 29/03/2022 </p>
						
					<%} else { %>
					<b>Confirmed Cases Last 14 days:  </b><%=request.getParameter("cases")%><br><br>
					<b>Cumulative Incidence Rate Last 14 days:   </b><%=request.getParameter("tia")%><br><br>
					<b>Total Confirmed Cases:   </b><%=request.getParameter("total_cases")%><br><br>
					<b>Total Cumulative Incidence Rate:   </b><%=request.getParameter("total_tia")%>
					<%} %>
					</p>
				<br><br>
				<form action="export_covid_results.jsp" method=POST> 
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
						<input type="submit" value="Export COVID-19 results as CSV ">
					</form> 
				</td>
				<%} %>
			</tr>
		</table>
	</center>
	<jsp:include page="/end.jsp"></jsp:include>
</body>
</html>