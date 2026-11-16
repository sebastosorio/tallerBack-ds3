package co.edu.unbosque.taller3.dto;

import java.util.List;

public class PixabayRespuestaDTO {

	private int total;
	private int totalHits;
	private List<PixabayHitDTO> hits;

	public PixabayRespuestaDTO() {
	}

	public PixabayRespuestaDTO(int total, int totalHits, List<PixabayHitDTO> hits) {
		super();
		this.total = total;
		this.totalHits = totalHits;
		this.hits = hits;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List<PixabayHitDTO> getHits() {
		return hits;
	}

	public void setHits(List<PixabayHitDTO> hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "PixabayRespuestaDTO [total=" + total + ", totalHits=" + totalHits + ", hits=" + hits + "]";
	}
}