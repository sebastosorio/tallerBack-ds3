package co.edu.unbosque.taller3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.unbosque.taller3.dto.PaisDTO;
import co.edu.unbosque.taller3.exception.PaisApiException;
import co.edu.unbosque.taller3.exception.PaisNotFoundException;

@Service
public class PaisService {

	@Value("${paises.url}")
	private String baseUrl;

	public PaisService() {
	}

	public List<PaisDTO> buscarPorNombre(String nombre) throws PaisApiException, PaisNotFoundException {
		String urlCompleta = baseUrl + "name/" + ExternalHTTPRequestHandler.codificar(nombre);
		List<PaisDTO> lista = ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
		if (lista.isEmpty()) {
			throw new PaisNotFoundException("No se encontro ningun pais con ese nombre (escribelo en ingles, ej: germany)");
		}
		return lista;
	}

	public List<PaisDTO> buscarPorCodigo(String codigo) throws PaisApiException, PaisNotFoundException {
		String urlCompleta = baseUrl + "alpha/" + ExternalHTTPRequestHandler.codificar(codigo);
		List<PaisDTO> lista = ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
		if (lista.isEmpty()) {
			throw new PaisNotFoundException("No se encontro ningun pais con ese codigo");
		}
		return lista;
	}

	public List<PaisDTO> buscarPorRegion(String region) throws PaisApiException, PaisNotFoundException {
		String urlCompleta = baseUrl + "region/" + ExternalHTTPRequestHandler.codificar(region);
		List<PaisDTO> lista = ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
		if (lista.isEmpty()) {
			throw new PaisNotFoundException("Region no valida. Usa: Africa, Americas, Asia, Europe, Oceania o Polar");
		}
		return lista;
	}
}