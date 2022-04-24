package com.ufcg.psoft.mercadofacil.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarrinhoDTO {
	
	private Long id;
	
	private List<ItemProdutoDTO> itens;
	
}
