package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemProduto extends GenericModel {

	@OneToOne
	private Produto produto;
	private int qtdItens;
	
}
