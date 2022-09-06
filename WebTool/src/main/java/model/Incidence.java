package model;

/**
 * Class for managing Covid-19 incidence data.
 * @author silviapinilla
 *
 */

public class Incidence {

	String zona_basica_salud;
	
	// Fecha de generación del fichero de datos.
	String fecha_informe;
	
	// Casos confirmados en los últimos 14 días y que siguen activos.
	// A partir del 13 de octubre de 2020 este campo aparece nulo.
	String casos_confirmados_activos_14;
	
	// Tasa de incidencia acumulada en los últimos 14 días de casos activos.
	// Cociente entre los casos_confirmados_activos_14 entre la población.
	// A partir del 13 de octubre de 2020 este campo aparece nulo.
	String tia_activos_14;
	
	// Casos confirmados en los últimos 14 días.
	float casos_confirmados_14;
	
	// Tasa de incidencia acumulada en los últimos 14 días.
	// Cociente de los casos_confirmados_14 entre la población.
	String tia_14;
	
	float casos_confirmados_totales;
	
	String tia_total;
	
	// Código de la zona básica de salud para la representación geográfica.
	String codigo_geometria;
	
	/*
	 * Constructor.
	 */
	public Incidence (String zona_basica_salud, String fecha_informe, String casos_confirmados_activos_14,
			String tia_activos_14, float casos_confirmados_14, String tia_14, float casos_confirmados_totales,
			String tia_total, String codigo_geometria) {
		
		this.zona_basica_salud = zona_basica_salud;
		this.fecha_informe = fecha_informe;
		this.casos_confirmados_activos_14 = casos_confirmados_activos_14;
		this.tia_activos_14 = tia_activos_14;
		this.casos_confirmados_14 = casos_confirmados_14;
		this.tia_14 = tia_14;
		this.casos_confirmados_totales = casos_confirmados_totales;
		this.tia_total = tia_total;
		this.codigo_geometria = codigo_geometria;
	}

	public String getZona_basica_salud() {
		return zona_basica_salud;
	}

	public void setZona_basica_salud(String zona_basica_salud) {
		this.zona_basica_salud = zona_basica_salud;
	}

	public String getFecha_informe() {
		return fecha_informe;
	}

	public void setFecha_informe(String fecha_informe) {
		this.fecha_informe = fecha_informe;
	}

	public String getCasos_confirmados_activos_14() {
		return casos_confirmados_activos_14;
	}

	public void setCasos_confirmados_activos_14(String casos_confirmados_activos_14) {
		this.casos_confirmados_activos_14 = casos_confirmados_activos_14;
	}

	public String getTia_activos_14() {
		return tia_activos_14;
	}

	public void setTia_activos_14(String tia_activos_14) {
		this.tia_activos_14 = tia_activos_14;
	}

	public float getCasos_confirmados_14() {
		return casos_confirmados_14;
	}

	public void setCasos_confirmados_14(float casos_confirmados_14) {
		this.casos_confirmados_14 = casos_confirmados_14;
	}

	public String getTia_14() {
		return tia_14;
	}

	public void setTia_14(String tia_14) {
		this.tia_14 = tia_14;
	}

	public float getCasos_confirmados_totales() {
		return casos_confirmados_totales;
	}

	public void setCasos_confirmados_totales(float casos_confirmados_totales) {
		this.casos_confirmados_totales = casos_confirmados_totales;
	}

	public String getTia_total() {
		return tia_total;
	}

	public void setTia_total(String tia_total) {
		this.tia_total = tia_total;
	}

	public String getCodigo_geometria() {
		return codigo_geometria;
	}

	public void setCodigo_geometria(String codigo_geometria) {
		this.codigo_geometria = codigo_geometria;
	}
	
	
}
