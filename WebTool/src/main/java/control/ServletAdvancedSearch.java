package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Coordenates;
import model.DBInteraction;

/**
 * Servlet implementation class ServletAdvancedSearch
 * @author silviapinilla
 */

@WebServlet("/ServletAdvancedSearch")

public class ServletAdvancedSearch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletAdvancedSearch() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		double lon, lat;
		int num_sensors;
		String type_sensors;
		String from_day, from_month, from_year;
		String to_day, to_month, to_year;
		
		//Retrieve values selected from the form
		lon = Double.parseDouble(request.getParameter("longitud"));
		lat = Double.parseDouble(request.getParameter("latitud"));
		num_sensors = Integer.parseInt(request.getParameter("num_sensors"));
		type_sensors = request.getParameter("type_sensors");
		from_day = request.getParameter("from_day");
		from_month = request.getParameter("from_month");
		from_year = request.getParameter("from_year");
		to_day = request.getParameter("to_day");
		to_month = request.getParameter("to_month");
		to_year = request.getParameter("to_year");
		
		try {
			
			DBInteraction db = new DBInteraction();
			
			//Obtain GPS coordinates of all the measuring points of the type specified
			ArrayList<Coordenates> coords_allpoints = db.get_coordinates_all_measuringpoints_by_type(type_sensors);
			
			// Calculate the distance between the coordenates specified and all of the rest
			ArrayList<Double> distancias = new ArrayList<Double>();
			for(int i = 0; i < coords_allpoints.size(); i++) {
				if (lon !=coords_allpoints.get(i).getLong() && lat != coords_allpoints.get(i).getLat()) {
					double new_distance = calculate_distance(lon, lat, coords_allpoints.get(i).getLong(), coords_allpoints.get(i).getLat());
					distancias.add(new_distance);
				}
			}
			
			//Look for the N positions with the minimum distances (num_sensors - the one specified)
			int posiciones[] = new int[num_sensors-1];
			for (int j = 0; j < num_sensors-1; j++) {
				//Get the minimum distance
				double min_distance = Collections.min(distancias);
				// Obtain the position of the array for min_distance
				int pos = distancias.indexOf(min_distance);
				posiciones[j] = pos;
				//Remove the minimum distance in order to obtain the second minimum distance.
				//As this is not possible without altering the current positions of the array, we are
				//going to set at that point the maximum value.
				distancias.set(pos, Collections.max(distancias));
			}
			
			//Now we know the N points with their coordinates nearer to the point specified

			//Obtain the traffic intensity values on these points for the time interval specified
			ArrayList<Object[]> traffic_intensity = new ArrayList<Object[]>();
			//We should first add the point specified to obtain the traffic
			traffic_intensity.addAll(db.get_traffic_by_measuringpoint_and_date(lon, lat, from_day, from_month, from_year, to_day, to_month, to_year));
			
			//Save all the coordinates to send them by URL
			String coordenadas_url = String.valueOf(lon) + "," + String.valueOf(lat) + ";";
			
			for (int k = 0; k < posiciones.length; k++) {
				Coordenates point = coords_allpoints.get(posiciones[k]);
				double lon_nearpoint = point.getLong();
				double lat_nearpoint = point.getLat();
				coordenadas_url += String.valueOf(lon_nearpoint) + "," + String.valueOf(lat_nearpoint) + ";";
				traffic_intensity.addAll(db.get_traffic_by_measuringpoint_and_date(lon_nearpoint, lat_nearpoint, from_day, from_month, from_year, to_day, to_month, to_year));
			}
			
			//Now we can calculate the mean of the traffic intensity
			int suma = 0;
			for (int i = 0; i < traffic_intensity.size(); i++) {
				suma += (int) traffic_intensity.get(i)[3];
			}
			int media_traffic_intensity = suma / traffic_intensity.size();
			
			
			RequestDispatcher traffic_map = request.getRequestDispatcher("search_advancedtraffic.jsp?day1="+from_day+"&month1="+from_month+"&year1="+from_year+
					"&day2="+to_day+"&month2="+to_month+"&year2="+to_year+"&intensity="+media_traffic_intensity+"&points="+coordenadas_url);
	        traffic_map.include(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Función que calcula la distancia entre dos coordenadas geográficas
	 */
	private double calculate_distance(double lon1, double lat1, double lon2, double lat2) {
		
		double R = 6371000; // metres
		double φ1 = (lat1 * Math.PI/180); // φ, λ in radians
		double φ2 = (lat2 * Math.PI/180);
		double Δφ = (lat2-lat1) * Math.PI/180;
		double Δλ = (lon2-lon1) * Math.PI/180;

		double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ/2) * Math.sin(Δλ/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		double distance = R * c; // in metres
		
		return distance;
	}
	
}
