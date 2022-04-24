package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroLote {

	static final String SEM_LOTES_CADASTRADOS = "Não há lotes cadastrados";
	
	static final String QUANTIDADE_INSUFICIENTE = "Não há produtos suficientes nesse lote para adicionar no carrinho.";
	
	public static ResponseEntity<CustomErrorType> erroSemLotesCadastrados() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.SEM_LOTES_CADASTRADOS),
				HttpStatus.NO_CONTENT);
	}
	
	public static ResponseEntity<CustomErrorType> erroQuantidadeInsuficiente() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.QUANTIDADE_INSUFICIENTE),
				HttpStatus.BAD_REQUEST);
	}
	
}

