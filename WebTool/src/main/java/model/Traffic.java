package model;

/**
 * Class for managing traffic data.
 * @author silviapinilla
 *
 */

public class Traffic {

	// Identificación única del punto de medida.
	int id;
	
	// Formato yyyy-mm-dd hh:mi:ss
	String fecha;
	
	// Tipo: Urbano o M30.
	String tipo;
	
	// Vehículos/hora medidos en un periodo de 15 minutos.
	int intensidad;
	
	// Tiempo de ocupación en % medido en un periodo de 15 minutos.
	int ocupacion;
	
	// Grado de uso de la vía de 0 a 100 medido en un periodo de 15 minutos.
	int carga;
	
	// Velocidad media de los vehículos (km/h) medido en un periodo de 15 minutos.
	int vmed;
	
	// Indicación de si ha habido al menos una muestra errónea.
	// N: no ha habido errores ni sustituciones
	// E: los parámetros de calidad de alguna de las muestras integradas no son óptimos.
	// S: alguna de las muestras recibidas era totalmente errónea y no se ha integrado.
	String error;
	
	// Número de muestras recibidas y consideradas en el periodo de integración
	int periodo_integracion;
	
	/*
	 * Constructor.
	 */
	public Traffic(int id, String fecha, String tipo, int intensidad, 
			int ocupacion, int carga, int vmed, String error, int periodo_integracion) {
		
		this.id = id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.intensidad = intensidad;
		this.ocupacion = ocupacion;
		this.carga = carga;
		this.vmed = vmed;
		this.error = error;
		this.periodo_integracion = periodo_integracion;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getIntensidad() {
		return intensidad;
	}

	public void setIntensidad(int intensidad) {
		this.intensidad = intensidad;
	}

	public int getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(int ocupacion) {
		this.ocupacion = ocupacion;
	}

	public int getCarga() {
		return carga;
	}

	public void setCarga(int carga) {
		this.carga = carga;
	}

	public int getVmed() {
		return vmed;
	}

	public void setVmed(int vmed) {
		this.vmed = vmed;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getPeriodo_integracion() {
		return periodo_integracion;
	}

	public void setPeriodo_integracion(int periodo_integracion) {
		this.periodo_integracion = periodo_integracion;
	}
	
}
