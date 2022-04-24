package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCarrinho {

	static final String CARRINHO_NAO_CADASTRADO = "Carrinho não cadastrado";
	
	static final String ITEM_INDISPONIVEL_PARA_REMOCAO = "Este item está indiponível para remoção do carrinho.";
	
	static final String IMPOSSIVEL_FINALIZAR_CARRINHO = "Este carrinho não possui itens, por isso a compra não pode ser gerada.";
	
	public static ResponseEntity<CustomErrorType> erroCarrinhoNaoCadastrado() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.CARRINHO_NAO_CADASTRADO),
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroItemIndiponivelParaRemocao() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.ITEM_INDISPONIVEL_PARA_REMOCAO),
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroImpossivelFinalizarCarrinho() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.IMPOSSIVEL_FINALIZAR_CARRINHO),
				HttpStatus.BAD_REQUEST);
	}
}
