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
</head>
<body>

	<script type="text/javascript">

		ol.proj.useGeographic();
		const center = [-3.6658006, 40.4185352];
		var point = [];
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("1")) { %>
		point = [-3.70275, 40.41831];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("2")) { %>
		point = [-3.69618, 40.40021];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("3")) { %>
		point = [-3.68307, 40.41317];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("4")) { %>
		point = [-3.68358653904917, 40.4273253];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("5")) { %>
		point = [-3.67753405826461, 40.4607638];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("6")) { %>
		point = [-3.6982806, 40.4605781];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("7")) { %>
		point = [-3.70383035345138, 40.43624735];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("8")) { %>
		point = [-3.7097222222222., 40.478611111111];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("9")) { %>
		point = [-3.73694444, 40.44361111];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("10")) { %>
		point = [-3.74569, 40.38897];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("11")) { %>
		point = [-3.7242000, 40.3909400];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("12")) { %>
		point = [-3.70035, 40.38866];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("13")) { %>
		point = [-3.662, 40.39354];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("14")) { %>
		point = [-3.64935, 40.40742];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("15")) { %>
		point = [-3.65132., 40.44505];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("16")) { %>
		point = [-3.64402712780169, 40.4808253];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("17")) { %>
		point = [-3.69411904172607, 40.34309205];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("18")) { %>
		point = [-3.6159427185388, 40.35127525];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("19")) { %>
		point = [-3.57719996488299, 40.3882402];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("20")) { %>
		point = [-3.6040024280774, 40.42891905];
		<% } %>
		<% if(request.getParameter("zone") != null && request.getParameter("zone").equals("21")) { %>
		point = [-3.5777700, 40.4736600];
		<% } %>

		// Style: red circle
		const final_style = new ol.style.Style({
			image : new ol.style.Circle({
				fill : new ol.style.Fill({
					color : 'rgba(0, 0, 230, 0.7)'
				}),
				stroke : new ol.style.Stroke({
					width : 2,
					color : 'rgba(0, 0, 255, 0.9)'
				}),
				radius : 25
			})
		});
		
		// Create vector with center point
		const features = [];
		features.push(new ol.Feature({geometry:new ol.geom.Point(point),}));
		const vectorSource = new ol.source.Vector({features});
		
		// Create map
		const map = new ol.Map({
			target: 'map',
			view: new ol.View({
				center: center,
				zoom: 11,
			}),
			layers: [
				new ol.layer.Tile({
					source: new ol.source.OSM(),
				}),
				new ol.layer.Vector({
					source: vectorSource,
					style: final_style,
				}),
			],
		});

	</script>
	<script src=show_coords.js></script>
</body>
</html>