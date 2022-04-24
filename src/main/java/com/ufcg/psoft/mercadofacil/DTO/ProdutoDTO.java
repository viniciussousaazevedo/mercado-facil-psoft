package com.ufcg.psoft.mercadofacil.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProdutoDTO {

	private Long id;
	
	private String nome;

	private BigDecimal preco;

	private String codigoBarra;

	private String fabricante;
	
	private String categoria;
	
	private boolean isRefrigerado;
	
	private boolean isFragil;
}
