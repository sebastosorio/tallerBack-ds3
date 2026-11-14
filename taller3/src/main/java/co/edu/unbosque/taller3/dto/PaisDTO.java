package co.edu.unbosque.taller3.dto;

public class PaisDTO {

	private String nombre;
	private String capital;
	private String region;
	private String continentes;
	private long poblacion;
	private double extension;
	private String idiomas;
	private String monedas;
	private String fronteras;
	private double latitud;
	private double longitud;

	public PaisDTO() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getContinentes() {
		return continentes;
	}

	public void setContinentes(String continentes) {
		this.continentes = continentes;
	}

	public long getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(long poblacion) {
		this.poblacion = poblacion;
	}

	public double getExtension() {
		return extension;
	}

	public void setExtension(double extension) {
		this.extension = extension;
	}

	public String getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String idiomas) {
		this.idiomas = idiomas;
	}

	public String getMonedas() {
		return monedas;
	}

	public void setMonedas(String monedas) {
		this.monedas = monedas;
	}

	public String getFronteras() {
		return fronteras;
	}

	public void setFronteras(String fronteras) {
		this.fronteras = fronteras;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	@Override
	public String toString() {
		return "PaisDTO [nombre=" + nombre + ", capital=" + capital + ", region=" + region + ", continentes="
				+ continentes + ", poblacion=" + poblacion + ", extension=" + extension + ", idiomas=" + idiomas
				+ ", monedas=" + monedas + ", fronteras=" + fronteras + ", latitud=" + latitud + ", longitud="
				+ longitud + "]";
	}
}