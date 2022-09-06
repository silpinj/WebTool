package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletMeasuringPoints
 * @author silviapinilla
 */

@WebServlet("/ServletMeasuringPoints")

public class ServletMeasuringPoints extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletMeasuringPoints() {
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
		
		String zone;
		
		//Retrieve values selected from the form
		zone = request.getParameter("zone");
					
		String name_district = getNameDistrict(Integer.parseInt(zone));
		RequestDispatcher points_map = request.getRequestDispatcher("map_measuringpoints.jsp?district="+name_district);
		points_map.include(request, response);
	}

	/**
	 * Método que devuelve el nombre del distrito dado el identificador
	 */
	private String getNameDistrict(int id) {
		
		String name_district = "";
		
		if(id == 1) {
			name_district = "Madrid Centro";
		}
		if(id == 2) {
			name_district = "Arganzuela";
		}
		if(id == 3) {
			name_district = "Retiro";
		}
		if(id == 4) {
			name_district = "Barrio Salamanca";
		}
		if(id == 5) {
			name_district = "Chamartín";
		}
		if(id == 6) {
			name_district = "Tetuán";
		}
		if(id == 7) {
			name_district = "Chamberí";
		}
		if(id == 8) {
			name_district = "Fuencarral-El Pardo";
		}
		if(id == 9) {
			name_district = "Moncloa-Aravaca";
		}
		if(id == 10) {
			name_district = "Latina";
		}
		if(id == 11) {
			name_district = "Carabanchel";
		}
		if(id == 12) {
			name_district = "Usera";
		}
		if(id == 13) {
			name_district = "Puente de Vallecas";
		}
		if(id == 14) {
			name_district = "Moratalaz";
		}
		if(id == 15) {
			name_district = "Ciudad Lineal";
		}
		if(id == 16) {
			name_district = "Hortaleza";
		}
		if(id == 17) {
			name_district = "Villaverde";
		}
		if(id == 18) {
			name_district = "Villa de Vallecas";
		}
		if(id == 19) {
			name_district = "Vicálvaro";
		}
		if(id == 20) {
			name_district = "San Blas-Canillejas";
		}
		if(id == 21) {
			name_district = "Barajas";
		}
		if(id == 22) {
			name_district = "All";
		}
		return name_district;
	}
}
