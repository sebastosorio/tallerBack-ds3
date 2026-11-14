package co.edu.unbosque.taller3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.taller3.dto.PaisDTO;

@Service
public class PaisService {

	public PaisService() {
	}

	
	public List<PaisDTO> buscarPorNombre(String nombre) {
		return ExternalHTTPRequestHandler.buscarPaisesPorNombre(nombre);
	}

	public List<PaisDTO> buscarPorCodigo(String codigo) {
		return ExternalHTTPRequestHandler.buscarPaisesPorCodigo(codigo);
	}

	public List<PaisDTO> buscarPorRegion(String region) {
		return ExternalHTTPRequestHandler.buscarPaisesPorRegion(region);
	}
}