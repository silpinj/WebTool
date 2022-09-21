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

@WebServlet("/ServletExportBasicResults")

public class ServletExportBasicResults extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletExportBasicResults() {
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
		
		String zone,district;
		String from_day, from_month, from_year;
		String to_day, to_month, to_year;
		String intensity;
		String type_export;
		
		//Retrieve hidden values from the form
		zone = request.getParameter("zone");
		district = request.getParameter("district");
		from_day = request.getParameter("day1");
		from_month = request.getParameter("month1");
		from_year = request.getParameter("year1");
		to_day = request.getParameter("day2");
		to_month = request.getParameter("month2");
		to_year = request.getParameter("year2");
		intensity = request.getParameter("intensity");
		
		//Retrieve value selected
		type_export = request.getParameter("export");
		
		try {
			
			File csv = new File("./WebTool/src/main/webapp/Basic-Results.csv");
			if(csv.createNewFile() == false) {
				csv.delete();
			}
			csv.createNewFile();
			FileWriter writer = new FileWriter(csv);
			
			DBInteraction db = new DBInteraction();
			
			// Data every 15 minutes for each measuring point
			if(type_export.equals("1")) {
				
				ArrayList<Object[]> data = db.get_trafficdata_by_zone_and_date(zone, from_day, from_month, from_year, to_day, to_month, to_year);
				writer.write("District;Id;Date;Type;Intensity;Occupation;Charge;Vmed;Error;Integrity Period\n");	
				for(int i = 0; i < data.size(); i++) {
					writer.write(district+";"+data.get(i)[0]+";"+data.get(i)[1]+";"+data.get(i)[2]+";"+data.get(i)[3]+";"
							+data.get(i)[4]+";"+data.get(i)[5]+";"+data.get(i)[6]+";"+data.get(i)[7]+";"
							+data.get(i)[8]+"\n");
				}
			} 
			// Data every 15 minutes for all measuring point
			else if(type_export.equals("2")) { 
				
				ArrayList<Object[]> data = db.get_trafficdata_by_zone_and_date(zone, from_day, from_month, from_year, to_day, to_month, to_year);
				writer.write("District;Date;Type;Intensity;Occupation;Charge;Vmed;Integrity Period\n");
				//Obtain date of all measuring points obtained
				ArrayList<String> dates_hours = new ArrayList<String>();
				for(int i = 0; i < data.size(); i++) {
					String date_hour = (String) data.get(i)[1];
					if(dates_hours.contains(date_hour) == false) {
						dates_hours.add(date_hour);	
					}
				}
				// For each interval of 15 mins, calculate the average of all measuring points 
				for(int i = 0; i < dates_hours.size(); i++) {
					String date_hour = dates_hours.get(i);
					int intens = 0;
					int occupation = 0;
					int charge = 0;
					int vmed = 0;
					int integrity = 0;
					int elements = 0;
					for(int j = 0; j < data.size(); j++) {
						//If date and hour match, save values to calculate average
						if(date_hour.equals((String) data.get(j)[1])) {
							intens += (int) data.get(j)[3];
							occupation += (int) data.get(j)[4];
							charge += (int) data.get(j)[5];
							vmed += (int) data.get(j)[6];
							integrity += (int) data.get(j)[8];
							elements++;
						}
					}
					if(elements != 0) {
						intens = intens/elements;
						occupation = occupation/elements;
						charge = charge/elements;
						vmed = vmed/elements;
						integrity = integrity/elements;
					}
					writer.write(district+";"+date_hour+";"+data.get(i)[2]+";"+intens+";"+occupation+";"+charge+";"+vmed+";"+integrity+"\n");
				}
			} 
			// Data per day for each measuring point
			else if(type_export.equals("3")) { 
				
				ArrayList<Object[]> data = db.get_trafficdata_by_zone_and_date(zone, from_day, from_month, from_year, to_day, to_month, to_year);
				ArrayList<Object[]> data_per_day = calculate_average_per_day(data);
				writer.write("District;Id;Date;Average Intensity;Average Occupation;Average Charge;Average Vmed;Average Integrity Period\n");	
				for (int i = 0; i < data_per_day.size(); i++) {
					Object[] day = data_per_day.get(i);
					writer.write(district+";"+day[0]+";"+day[1]+";"+day[2]+";"+day[3]+";"+day[4]+";"+day[5]+";"+day[6]+"\n");
				}
			} 
			// Data per day for all measuring point
			else if(type_export.equals("4")) { 
				
				ArrayList<Object[]> data = db.get_trafficdata_by_zone_and_date(zone, from_day, from_month, from_year, to_day, to_month, to_year);
				ArrayList<Object[]> data_per_day = calculate_average_per_day(data);
				ArrayList<String> dates = new ArrayList<String>();
				
				writer.write("District;Date;Average Intensity;Average Occupation;Average Charge;Average Vmed;Average Integrity Period\n");	
				
				for( int i = 0; i < data_per_day.size(); i++) {
					String date = (String) data_per_day.get(i)[1];
					if(dates.contains(date) == false) {
						dates.add(date);
					}	
				}
				
				for (int i = 0; i < dates.size(); i++) {
					String date = dates.get(i);
					int intens = 0;
					int occupation = 0;
					int charge = 0;
					int vmed = 0;
					int integrity = 0;
					int elements = 0;
					for (int j = 0; j < data_per_day.size(); j++) {
						if(date.equals(data_per_day.get(i)[1])) {
							intens += (int) data.get(i+j)[3];
							occupation += (int) data.get(i+j)[4];
							charge += (int) data.get(+j)[5];
							vmed += (int) data.get(i+j)[6];
							integrity += (int) data.get(i+j)[8];
							elements++;
						}
					}
					if(elements != 0) {
						intens = intens/elements;
						occupation = occupation/elements;
						charge = charge/elements;
						vmed = vmed/elements;
						integrity = integrity/elements;
					}
					writer.write(district+";"+date+";"+intens+";"+occupation+";"+charge+";"+vmed+";"+integrity+"\n");	
				}
			} 
			// Total average
			else if(type_export.equals("5")) { 
				writer.write("District;From;To;Average Traffic\n");
				writer.write(district+";"+from_day+"/"+from_month+"/"+from_year + ";" + to_day+"/"+to_month+"/"+to_year+";"+intensity);
			}
			
			// Close writer and redirect to next page
			writer.close();
			RequestDispatcher page = request.getRequestDispatcher("show_csv_data.jsp?basic=1");
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
