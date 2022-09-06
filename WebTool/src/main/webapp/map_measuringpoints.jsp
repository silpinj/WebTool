<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ page language="java"%>
<%@ page import="model.DBInteraction"%>
<%@ page import="model.Coordenates"%>
<%@ page import="java.util.ArrayList"%>

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
		<table width="85%">
			<tr>
				<td width="25%" align="left">
					<h3>Select the district to search for measuring points:</h3> <br>
					<form action="ServletMeasuringPoints" method=POST>
						<SELECT name="zone">
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
						</SELECT> <input type="submit" value="Search"> <br>
						</form>
						<br><br><br><br><br><br><br><br><br><br><br>
				</td>
				<td width="55%">
					<%
					String district;
					if(request.getParameter("district") == null) {
						district = "(Please select a district)";
					} else {
						district = request.getParameter("district");
					}
					%>
					<h3 align="center">Measuring points in: <%=district%></h3>

					<!-- DIV del mapa -->
				    <div id="map" class="map">
				    <div id="popup"></div>
   					</div>
					<jsp:include page="map_basic.jsp"></jsp:include>
				</td>
			</tr>
		</table>
	</center>
	<jsp:include page="/end.jsp"></jsp:include>
</body>
</html>