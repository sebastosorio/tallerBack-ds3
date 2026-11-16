package co.edu.unbosque.taller3.dto;

public class PixabayHitDTO {

	private long id;
	private String tags;
	private String pageURL;
	private String webformatURL;

	public PixabayHitDTO() {
	}

	public PixabayHitDTO(long id, String tags, String pageURL, String webformatURL) {
		super();
		this.id = id;
		this.tags = tags;
		this.pageURL = pageURL;
		this.webformatURL = webformatURL;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public String getWebformatURL() {
		return webformatURL;
	}

	public void setWebformatURL(String webformatURL) {
		this.webformatURL = webformatURL;
	}

	@Override
	public String toString() {
		return "PixabayHitDTO [id=" + id + ", tags=" + tags + ", pageURL=" + pageURL + ", webformatURL=" + webformatURL
				+ "]";
	}
}