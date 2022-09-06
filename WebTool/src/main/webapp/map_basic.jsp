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
	
	<%
	DBInteraction db = new model.DBInteraction();
	ArrayList<Coordenates> coords_points;
	if(request.getParameter("district") != null && !request.getParameter("district").equals("All")) {
		//Obtain measuring points of the district
		coords_points = db.get_coordinates_measuringpoints_by_district(request.getParameter("zone"));
	} else {
		//Obtain all measuring points
		coords_points = db.get_coordinates_all_measuringpoints();
	}
	double longitudes[] = new double[coords_points.size()];
	double latitudes[] = new double[coords_points.size()];
		
	for (int i = 0; i < coords_points.size(); i++) {
		double longitud = coords_points.get(i).getLong();
		double latitud = coords_points.get(i).getLat();
		longitudes[i] = longitud;
		latitudes[i] = latitud;
	}%>
	
		ol.proj.useGeographic();
		const madrid = [-3.6658006, 40.4185352];
		
		// Style: red circle with text (hidden)
		const final_style = new ol.style.Style({
			image : new ol.style.Circle({
				fill : new ol.style.Fill({
					color : 'rgba(255, 0, 0, 0.7)'
				}),
				stroke : new ol.style.Stroke({
					width : 2,
					color : 'rgba(255, 0, 0, 0.9)'
				}),
				radius : 8
			}),
			text: new ol.style.Text({
				font: '9px Calibri,sans-serif',
				overflow: true,
				placement: 'point',
				fill: new ol.style.Fill({
					color: '#000'
				}),
				stroke: new ol.style.Stroke({
					color: '#fff',
					width: 3
				 })
			})
		});
	
		// Add points to features array
		const features = [];
			<%for (int j = 0; j < longitudes.length; j++) {%>
				features.push(new ol.Feature({
					geometry: new ol.geom.Point([<%=longitudes[j]%>, <%=latitudes[j]%>]),
					name: <%=longitudes[j]%>+','+ <%=latitudes[j]%> ,
				}));
			<%}%>
	
		// Create vector with all the points
		const vectorSource = new ol.source.Vector({features});
		
		// Create map
		const map = new ol.Map({
			target: 'map',
			view: new ol.View({
				center: madrid,
				zoom: 12,
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

		//Popup
		const element = document.getElementById('popup');

		const popup = new ol.Overlay({
			element: element,
			positioning: 'bottom-center',
			stopEvent: false,
			offset: [0, -10],
		});
		map.addOverlay(popup);

		map.on('click', function(event) {
			$(element).popover('dispose');

			const feature = map.getFeaturesAtPixel(event.pixel)[0];
			if (feature) {
				const coordinate = feature.getGeometry().getCoordinates();
				const text = feature.get('name');
				console.log(text);
				popup.setPosition([
					coordinate[0] + Math.round(event.coordinate[0] / 360) * 360,
					coordinate[1],
				]);
				$(element).popover({
					container: element.parentElement,
					html: true,
					sanitize: false,
					content: formatCoordinate(text),
					placement: 'top',
				});
				$(element).popover('show');
			}
		});

		map.on('pointermove', function(event) {
			if (map.hasFeatureAtPixel(event.pixel)) {
				map.getViewport().style.cursor = 'pointer';
			} else {
				map.getViewport().style.cursor = 'inherit';
			}
		});

	</script>
	<script src=show_coords.js></script>
</body>
</html>