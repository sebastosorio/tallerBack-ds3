package co.edu.unbosque.taller3.dto;

public class ImagenDTO {

	private long id;
	private String descripcion;
	private String urlImagen;
	private String urlPagina;

	public ImagenDTO() {
	}

	public ImagenDTO(long id, String descripcion, String urlImagen, String urlPagina) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.urlImagen = urlImagen;
		this.urlPagina = urlPagina;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrlPagina() {
		return urlPagina;
	}

	public void setUrlPagina(String urlPagina) {
		this.urlPagina = urlPagina;
	}

	@Override
	public String toString() {
		return "ImagenDTO [id=" + id + ", descripcion=" + descripcion + ", urlImagen=" + urlImagen + ", urlPagina="
				+ urlPagina + "]";
	}

}