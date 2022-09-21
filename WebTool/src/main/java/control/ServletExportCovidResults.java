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
 * Servlet implementation class ServletExportCovidResults
 * @author silviapinilla
 */

@WebServlet("/ServletExportCovidResults")

public class ServletExportCovidResults extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletExportCovidResults() {
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
		
		String district;
		String from_day, from_month, from_year;
		String to_day, to_month, to_year;
		String cases, tia, total_cases, total_tia;
		String type_export;
		
		//Retrieve hidden values from the form
		district = request.getParameter("district");
		from_day = request.getParameter("day1");
		from_month = request.getParameter("month1");
		from_year = request.getParameter("year1");
		to_day = request.getParameter("day2");
		to_month = request.getParameter("month2");
		to_year = request.getParameter("year2");
		cases = request.getParameter("cases");
		tia = request.getParameter("tia");
		total_cases = request.getParameter("total_cases");
		total_tia = request.getParameter("total_tia");
		
		//Retrieve value selected
		type_export = request.getParameter("export");
		
		try {
			
			File csv = new File("./workspace/WebTool/src/main/webapp/Covid-Results.csv");
			if(csv.createNewFile() == false) {
				csv.delete();
			}
			csv.createNewFile();
			FileWriter writer = new FileWriter(csv);
			
			DBInteraction db = new DBInteraction();
			
			// Data every 15 minutes for each measuring point
			if(type_export.equals("1")) {
				
				//Obtain covid data
				ArrayList<Object[]> covid_data = new ArrayList<Object[]>();
				if(Integer.valueOf(from_day) <= 26 && Integer.valueOf(from_month) <= 2 && Integer.valueOf(from_year) <= 2020) {
					covid_data = db.get_covid_by_district_all(district, to_day, to_month, to_year);
				} else {
					covid_data = db.get_covid_by_district_and_date(district, from_day, from_month, from_year, to_day, to_month, to_year);
				}
				writer.write("District;Date;Confirmed Cases last 14 days; Cumulative Incidence Rate last 14 days; Total Confirmed Cases; Total Cumulative Incidence Rate\n");
				for (int i = 0; i < covid_data.size(); i++) {
					Object[] values = covid_data.get(i);
					writer.write(district+";"+values[0]+";"+values[1]+";"+values[2]+";"+values[3]+";"+values[4]+"\n");
				}
			} 
			// Total average
			else if(type_export.equals("2")) { 
				writer.write("District;From;To;Confirmed Cases last 14 days; Cumulative Incidence Rate last 14 days; Total Confirmed Cases; Total Cumulative Incidence Rate\n");
				writer.write(district+";"+from_day+"/"+from_month+"/"+from_year +";"+ to_day+"/"+to_month+"/"+to_year+";"+cases+";"+tia+";"+total_cases+";"+total_tia+"\n");
			}
			
			// Close writer and redirect to next page
			writer.close();
			RequestDispatcher page = request.getRequestDispatcher("show_csv_data.jsp?covid=1");
	        page.include(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
