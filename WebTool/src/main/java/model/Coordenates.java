package model;

/**
 * 
 * @author silviapinilla
 *
 */
public class Coordenates {

	double longitud;
	double latitud;
	
	
	/*
	 * Constructor.
	 */
	public Coordenates(double longitud, double latitud) {
		
		this.longitud = longitud;
		this.latitud = latitud;
		
	}

	public double getLong() {
		return longitud;
	}

	public void setLong(double longitud) {
		this.longitud = longitud;
	}

	public double getLat() {
		return latitud;
	}

	public void setLat(double latitud) {
		this.latitud = latitud;
	}
	
}
