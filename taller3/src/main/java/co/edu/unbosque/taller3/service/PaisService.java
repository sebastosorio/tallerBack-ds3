package co.edu.unbosque.taller3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.taller3.dto.PaisDTO;

@Service
public class PaisService {

	@Value("${paises.url}")
	private String baseUrl;

	public PaisService() {
	}

	public List<PaisDTO> buscarPorNombre(String nombre) {
		String urlCompleta = baseUrl + "name/" + ExternalHTTPRequestHandler.codificar(nombre);
		return ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
	}

	public List<PaisDTO> buscarPorCodigo(String codigo) {
		String urlCompleta = baseUrl + "alpha/" + ExternalHTTPRequestHandler.codificar(codigo);
		return ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
	}

	public List<PaisDTO> buscarPorRegion(String region) {
		String urlCompleta = baseUrl + "region/" + ExternalHTTPRequestHandler.codificar(region);
		return ExternalHTTPRequestHandler.doGetAndConvertToPaisList(urlCompleta);
	}
}