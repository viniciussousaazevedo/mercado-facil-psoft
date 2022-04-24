package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.enuns.TipoCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteDTO {

	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;
	
	private CarrinhoDTO carrinho;
	
	private TipoCliente tipoCliente;
}
