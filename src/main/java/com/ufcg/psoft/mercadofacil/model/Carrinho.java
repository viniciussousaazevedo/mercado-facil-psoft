package com.ufcg.psoft.mercadofacil.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Carrinho extends GenericModel {
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemProduto> itens;
}
