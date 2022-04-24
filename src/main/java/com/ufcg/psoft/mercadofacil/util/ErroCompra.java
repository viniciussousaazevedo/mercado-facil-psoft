package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCompra {

static final String COMPRA_INEXISTENTE = "Compra inexistente.";
	
	public static ResponseEntity<CustomErrorType> erroCompraInexistente() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCompra.COMPRA_INEXISTENTE),
				HttpStatus.NOT_FOUND);
	}
	
}
