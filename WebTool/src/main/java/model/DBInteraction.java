package model;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * Class for interaction with database.
 * @author silviapinilla
 *
 */
public class DBInteraction {

	Connection con;
	Statement stat;
	
	/*
	 * Constructor que conecta con base de datos.
	 */
	public DBInteraction() {
		
		String url = "jdbc:mysql://localhost:3306/datosTFM";
		
	    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
	    
        try {
            System.out.println("Trying to connect...");
            con = DriverManager.getConnection (url, "root", "password");
            System.out.println("Connected!");
            stat = con.createStatement();
		}
		catch(SQLException ex) {
            System.err.print("SQLException: ");
		}   
	}
	
	/**
	 * Function to close db connection.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		stat.close();
		con.close();
	}
	
	/**
	 * Function that, given a GPS coordinates return the ID of the measuring point.
	 * @param lon
	 * @param lat
	 * @return
	 * @throws SQLException
	 */
	public float get_id_measuringpoint_by_coords(double lon, double lat) throws SQLException {
		
		float id_measuringpoint = 0;
		String sql_point = "SELECT * FROM ZONAS WHERE LONGITUD=" + String.valueOf(lon) + " AND LATITUD=" + String.valueOf(lat) + ";";
		ResultSet rs = stat.executeQuery(sql_point);
		while(rs.next()) {
		id_measuringpoint = rs.getFloat(3);
		}
		return id_measuringpoint;
	}
	
	/**
	 * Function that returns the GPS coordinates of all measuring points on Madrid.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Coordenates> get_coordinates_all_measuringpoints() throws SQLException {

		ArrayList<Coordenates> coord_points = new ArrayList<Coordenates>();
		String sql_sentence;
		ResultSet rs;
		sql_sentence = "SELECT * FROM ZONAS;";
		rs = stat.executeQuery(sql_sentence);

		while(rs.next()) {
			double longitude = Double.parseDouble(rs.getString(8));
			double latitude = Double.parseDouble(rs.getString(9));
			Coordenates coords = new Coordenates(longitude, latitude);
			coord_points.add(coords);
		}
		return coord_points;
	}

	/**
	 * Function that returns the GPS coordinates of all measuring points of a type specified (URB, M30).
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Coordenates> get_coordinates_all_measuringpoints_by_type(String type) throws SQLException {

		String ids = "(";
		ArrayList<Coordenates> coord_points = new ArrayList<Coordenates>();
		String sql_sentence;
		ResultSet rs;
		
		if(type.equals("URB")) {
			sql_sentence = "SELECT DISTINCT ID FROM TRAFICO WHERE TIPO='URB';";
			rs = stat.executeQuery(sql_sentence);
			while(rs.next()) {
				String id = rs.getString(1);
				if(rs.next() == true) {
					ids += id + ",";
				} else {
					ids += id + ")";
				}
			}

		} else if (type.equals("M30")) {
			sql_sentence = "SELECT DISTINCT ID FROM TRAFICO WHERE TIPO='M30';";
			rs = stat.executeQuery(sql_sentence);
			while(rs.next()) {
				String id = rs.getString(1);
				if(rs.next() == true) {
					ids += id + ",";
				} else {
					ids += id + ")";
				}
			}
		}
		String sql_sentence2 = "SELECT * FROM ZONAS WHERE ID IN " + ids + ";";
		ResultSet rs2 = stat.executeQuery(sql_sentence2);
		while(rs2.next()) {
			double longitude = Double.parseDouble(rs2.getString(8));
			double latitude = Double.parseDouble(rs2.getString(9));
			Coordenates coords = new Coordenates(longitude, latitude);
			coord_points.add(coords);
		}
		return coord_points;
	}
	
	/**
	 * Function that, given a Madrid district, returns the GPS coordinates of all measuring points on that district.
	 * @param zone
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Coordenates> get_coordinates_measuringpoints_by_district(String zone) throws SQLException {

		ArrayList<Coordenates> coord_points = new ArrayList<Coordenates>();
		String sql_sentence;
		ResultSet rs;
		sql_sentence = "SELECT * FROM ZONAS WHERE DISTRITO=" + zone + ";";
		rs = stat.executeQuery(sql_sentence);

		while(rs.next()) {
			double longitude = Double.parseDouble(rs.getString(8));
			double latitude = Double.parseDouble(rs.getString(9));
			Coordenates coords = new Coordenates(longitude, latitude);
			coord_points.add(coords);
		}
		return coord_points;
	}
	
	/**
	 * Function that, given a Madrid district, returns the ID of all measuring points on that district.
	 * @param zone
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Float> get_id_measuringpoints_by_district(String zone) throws SQLException {
		
		ArrayList<Float> measuring_points = new ArrayList<Float>();
		String sql_sentence = "SELECT * FROM ZONAS WHERE DISTRITO=" + zone + ";";
		ResultSet rs = stat.executeQuery(sql_sentence);
		while(rs.next()) {
			float id_point = rs.getFloat(3);
			measuring_points.add(id_point);
		}
		return measuring_points;
	}
	
	/**
	 * Function that given a Madrid district and a time interval, returns all traffic intensity values for the district
	 * and time interval specified.
	 * @param zone
	 * @param from_day
	 * @param from_month
	 * @param from_year
	 * @param to_day
	 * @param to_month
	 * @param to_year
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> get_traffic_by_zone_and_date(String zone, String from_day, String from_month, 
			String from_year, String to_day, String to_month, String to_year) throws SQLException {
		
		//Obtaining IDs of measuring points of that point from selected basic health area.
		ArrayList<Float> measuring_points = get_id_measuringpoints_by_district(zone);
		//Creating an array with all the IDs to write the SQL sentence
		String values = "(";
		for (int i = 0; i < measuring_points.size(); i++) {
			if (i < measuring_points.size() - 1) {
			values += measuring_points.get(i).toString() + ",";
			} else {
				values += measuring_points.get(i).toString() + ")";
			}
		}
		
		//Obtaining traffic for time range specified measured on the IDs obtained.
		ArrayList<Integer> traffic_filtered = new ArrayList<Integer>();
		String from_yma = from_year + "-" + from_month + "-" + from_day + " 00:00:00";
		String to_yma = to_year + "-" + to_month + "-" + to_day + " 23:50:00";
		
		String sql_trafico = "SELECT * FROM TRAFICO WHERE FECHA>='"+ from_yma + "' and FECHA<='" + to_yma + "' and ID IN " + values + ";"; 
		ResultSet rs_trafico = stat.executeQuery(sql_trafico);
		while(rs_trafico.next()) {
			//int id = rs_trafico.getInt(1);
			//String fecha = rs_trafico.getString(2);
			//String tipo = rs_trafico.getString(3);
			int intensidad = rs_trafico.getInt(4);
			//int ocupacion = rs_trafico.getInt(5);
			//int carga = rs_trafico.getInt(6);
			//int vmed = rs_trafico.getInt(7);
			//String error = rs_trafico.getString(8);
			//int periodo = rs_trafico.getInt(9);
			traffic_filtered.add(intensidad);
		}
		return traffic_filtered;
	}
	
	/**
	 * Function that given a Madrid district and a time interval, returns all traffic data values for the district
	 * and time interval specified.
	 * 
	 * @param zone
	 * @param from_day
	 * @param from_month
	 * @param from_year
	 * @param to_day
	 * @param to_month
	 * @param to_year
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object[]> get_trafficdata_by_zone_and_date(String zone, String from_day, String from_month, 
			String from_year, String to_day, String to_month, String to_year) throws SQLException {
		
		//Obtaining IDs of measuring points of that point from selected basic health area.
		ArrayList<Float> measuring_points = get_id_measuringpoints_by_district(zone);
		//Creating an array with all the IDs to write the SQL sentence
		String values = "(";
		for (int i = 0; i < measuring_points.size(); i++) {
			if (i < measuring_points.size() - 1) {
			values += measuring_points.get(i).toString() + ",";
			} else {
				values += measuring_points.get(i).toString() + ")";
			}
		}
		
		//Obtaining traffic for time range specified measured on the IDs obtained.
		ArrayList<Object[]> traffic_data_filtered = new ArrayList<Object[]>();
		String from_yma = from_year + "-" + from_month + "-" + from_day + " 00:00:00";
		String to_yma = to_year + "-" + to_month + "-" + to_day + " 23:50:00";
		
		String sql_trafico = "SELECT * FROM TRAFICO WHERE FECHA>='"+ from_yma + "' and FECHA<='" + to_yma + "' and ID IN " + values + ";"; 
		ResultSet rs_trafico = stat.executeQuery(sql_trafico);
		while(rs_trafico.next()) {
			int id = rs_trafico.getInt(1);
			String fecha = rs_trafico.getString(2);
			String tipo = rs_trafico.getString(3);
			int intensidad = rs_trafico.getInt(4);
			int ocupacion = rs_trafico.getInt(5);
			int carga = rs_trafico.getInt(6);
			int vmed = rs_trafico.getInt(7);
			String error = rs_trafico.getString(8);
			int periodo = rs_trafico.getInt(9);
			Object data[] = {id,fecha,tipo,intensidad,ocupacion,carga,vmed,error,periodo};
			traffic_data_filtered.add(data);
		}
		return traffic_data_filtered;
	}
	
	/**
	 * Function that, given a GPS coordinates of a measuring point and a time interval, returns the traffic measured
	 * on that point in the time interval specified.
	 * @param lon
	 * @param lat
	 * @param from_day
	 * @param from_month
	 * @param from_year
	 * @param to_day
	 * @param to_month
	 * @param to_year
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object[]> get_traffic_by_measuringpoint_and_date(double lon, double lat, String from_day, String from_month, 
			String from_year, String to_day, String to_month, String to_year) throws SQLException {
		
		//Obtaining traffic for time range specified.
		ArrayList<Object[]> traffic_filtered = new ArrayList<Object[]>();
		String from_yma = from_year + "-" + from_month + "-" + from_day + " 00:00:00";
		String to_yma = to_year + "-" + to_month + "-" + to_day + " 23:50:00";
		float id_measuringpoint = get_id_measuringpoint_by_coords(lon, lat);
		
		if(id_measuringpoint == 0) {
			//ERROR
			return null;
		}
		
		String sql_trafico = "SELECT * FROM TRAFICO WHERE FECHA>='"+ from_yma + "' and FECHA<='" + to_yma + "' and ID=" + id_measuringpoint + ";"; 
		ResultSet rs_trafico = stat.executeQuery(sql_trafico);
		while(rs_trafico.next()) {
			int id = rs_trafico.getInt(1);
			String fecha = rs_trafico.getString(2);
			String tipo = rs_trafico.getString(3);
			int intensidad = rs_trafico.getInt(4);
			int ocupacion = rs_trafico.getInt(5);
			int carga = rs_trafico.getInt(6);
			int vmed = rs_trafico.getInt(7);
			String error = rs_trafico.getString(8);
			int periodo = rs_trafico.getInt(9);
			
			Object[] values = {id,fecha,tipo,intensidad,ocupacion,carga,vmed,error,periodo};
			traffic_filtered.add(values);
		}
		
		return traffic_filtered;
	}
	
	/**
	 * Function that returns covid cases and cumulative incidence rate on the district and
	 * time interval specified.
	 * @param zone
	 * @param from_day
	 * @param from_month
	 * @param from_year
	 * @param to_day
	 * @param to_month
	 * @param to_year
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object[]> get_covid_by_district_and_date(String district, String from_day, String from_month, 
			String from_year, String to_day, String to_month, String to_year) throws SQLException {
		
		ArrayList<Object[]> covid_data = new ArrayList<Object[]>();
		String from_yma = from_year + "/" + from_month + "/" + from_day + " 00:00:00";
		String to_yma = to_year + "/" + to_month + "/" + to_day + " 23:50:00";
		String full_district = "Madrid-"+district;
		
		String sql_covid = "SELECT * FROM INCIDENCIA_COVID WHERE DISTRITO='"+full_district +"' AND FECHA_INFORME>='"+ from_yma + "' AND FECHA_INFORME<='" + to_yma + "';"; 
		ResultSet rs_covid = stat.executeQuery(sql_covid);
		while(rs_covid.next()) {
			//int id = rs_trafico.getInt(1);
			String fecha = rs_covid.getString(2);
			float cases = rs_covid.getFloat(3);
			float tia = Float.valueOf(rs_covid.getString(4).replace(",","."));
			float total_cases = rs_covid.getFloat(5);
			float total_tia = Float.valueOf(rs_covid.getString(6).replace(",","."));
			
			Object total_data[] = {fecha,cases,tia,total_cases,total_tia};
			covid_data.add(total_data);
		}
		
		return covid_data;
	}
	
	/**
	 * Function that returns total covid cases and total cumulative incidence rate from a 
	 * date and district specified.
	 * @param zone
	 * @param to_day
	 * @param to_month
	 * @param to_year
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object[]> get_covid_by_district_all(String district, String to_day, String to_month, 
			String to_year) throws SQLException {
		
		ArrayList<Object[]> covid_data = new ArrayList<Object[]>();
		String to_yma = to_year + "-" + to_month + "-" + to_day + " 00:00:00";
		String full_district = "Madrid-"+district;
		
		String sql_covid = "SELECT * FROM INCIDENCIA_COVID WHERE DISTRITO='"+full_district+"' AND FECHA_INFORME='"+ to_yma + ";"; 
		ResultSet rs_covid = stat.executeQuery(sql_covid);
		while(rs_covid.next()) {

			float total_cases = rs_covid.getFloat(5);
			float total_tia = Float.valueOf(rs_covid.getString(6).replace(",","."));
			
			//In this case, it must be only one array position
			Float total_data[] = {total_cases,total_tia};
			covid_data.add(total_data);
		}
		
		return covid_data;
	}
	
}

