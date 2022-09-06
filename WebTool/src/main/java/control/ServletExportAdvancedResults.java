package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBInteraction;

/**
 * Servlet implementation class ServletExportData
 * @author silviapinilla
 */

@WebServlet("/ServletExportAdvancedResults")

public class ServletExportAdvancedResults extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletExportAdvancedResults() {
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
		
		String lon,lat;
		String num_sensors, type_sensors;
		String from_day, from_month, from_year;
		String to_day, to_month, to_year;
		String intensity;
		String[] points;
		String type_export;
		
		//Retrieve hidden values from the form
		lon = request.getParameter("longitud");
		lat = request.getParameter("latitud");
		num_sensors = request.getParameter("num_sensors");
		type_sensors = request.getParameter("type_sensors");
		from_day = request.getParameter("day1");
		from_month = request.getParameter("month1");
		from_year = request.getParameter("year1");
		to_day = request.getParameter("day2");
		to_month = request.getParameter("month2");
		to_year = request.getParameter("year2");
		intensity = request.getParameter("intensity");
		points = request.getParameter("points").split(";");
		
		//Retrieve value selected
		type_export = request.getParameter("export");
		
		try {
			
			File csv = new File("/Users/silviapinilla/SILVIA/Universidad/Master Teleco/TFM/workspace/ProjectTFM/src/main/webapp/Advanced-Results.csv");
			if(csv.createNewFile() == false) {
				csv.delete();
			}
			csv.createNewFile();
			FileWriter writer = new FileWriter(csv);
			
			DBInteraction db = new DBInteraction();
			
			// Data every 15 minutes for each measuring point
			if(type_export.equals("1")) {
				
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				for (int i = 0; i < points.length;i++) {
					String point = points[i];
					double lon_point = Double.valueOf(point.split(",")[0]);
					double lat_point = Double.valueOf(point.split(",")[1]);
					data.addAll(db.get_traffic_by_measuringpoint_and_date(lon_point, lat_point, from_day, from_month, from_year, to_day, to_month, to_year));
				}
				writer.write("Long specified;Lat specified;Number of sensors;Type of sensors;Id;Date;Average Intensity;Average Occupation;Average Charge;Average Vmed;Average Integrity Period\n");
				for (int j = 0; j < data.size(); j++) {
					Object[] values = data.get(j);
					writer.write(lon+";"+lat+";"+num_sensors+";"+type_sensors+";"+values[0]+";"+values[1]+";"+values[3]+";"+values[4]+";"+values[5]+";"+values[6]+";"+values[7]+"\n");
				}
				
			}  
			// Data per day for each measuring point
			else if(type_export.equals("2")) { 
				
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				for (int i = 0; i < points.length;i++) {
					String point = points[i];
					double lon_point = Double.valueOf(point.split(",")[0]);
					double lat_point = Double.valueOf(point.split(",")[1]);
					data.addAll(db.get_traffic_by_measuringpoint_and_date(lon_point, lat_point, from_day, from_month, from_year, to_day, to_month, to_year));
				}
				ArrayList<Object[]> data_per_day = calculate_average_per_day(data);
				writer.write("Long specified;Lat specified;Number of sensors;Type of sensors;Id;Date;Average Intensity;Average Occupation;Average Charge;Average Vmed;Average Integrity Period\n");
					
				for (int i = 0; i < data_per_day.size(); i++) {
					Object[] day = data_per_day.get(i);
					writer.write(lon+";"+lat+";"+num_sensors+";"+type_sensors+";"+day[0]+";"+day[1]+";"+day[2]+";"+day[3]+";"+day[4]+";"+day[5]+";"+day[6]+"\n");
				}
			} 
			// Total average
			else if(type_export.equals("3")) { 
				writer.write("Long;Lat;Number of sensors;Type of sensors;From;To;Average Traffic\n");
				writer.write(lon+";"+lat+";"+num_sensors+";"+type_sensors+";"+from_day+"/"+from_month+"/"+from_year+";"+to_day+"/"+to_month+"/"+to_year+";"+intensity);
			}
			
			// Close writer and redirect to next page
			writer.close();
			RequestDispatcher page = request.getRequestDispatcher("show_csv_data.jsp?advanced=1");
	        page.include(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that, given array of traffic data, calculate average values for a full day.
	 * A day, divided in 15 mins interval, has 96 intervals
	 * @param data
	 * @return
	 */
	private ArrayList<Object[]> calculate_average_per_day(ArrayList<Object[]> data) {
		
		ArrayList<Object[]> data_per_day = new ArrayList<Object[]>();
		
		// 1 day = 96 intervals of 15 minutes
		for(int i = 0; i < data.size(); i=i+96) {
			if(i < data.size()) {
				String date_hour = (String) data.get(i)[1];
				String date = date_hour.split("//.")[0];
				String id = String.valueOf(data.get(i)[0]);
				int intens = 0;
				int occupation = 0;
				int charge = 0;
				int vmed = 0;
				int integrity = 0;
				//Obtain 96 following values and save them
				for (int j = 0; j < 96; j++) {
					if( (i+j) < data.size() ) {
						intens += (int) data.get(i+j)[3];
						occupation += (int) data.get(i+j)[4];
						charge += (int) data.get(+j)[5];
						vmed += (int) data.get(i+j)[6];
						integrity += (int) data.get(i+j)[8];
					}
				}
				// Calculate average values
				intens = intens/96;
				occupation = occupation/96;
				charge = charge/96;
				vmed = vmed/96;
				integrity = integrity/96;
				
				Object[] values = {id,date,intens,occupation,charge,vmed,integrity};
				data_per_day.add(values);
			}
		}
		
		return data_per_day;
	}

}
