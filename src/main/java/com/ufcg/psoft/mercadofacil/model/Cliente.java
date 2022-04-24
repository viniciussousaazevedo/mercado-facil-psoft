package com.ufcg.psoft.mercadofacil.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ufcg.psoft.mercadofacil.model.enuns.TipoCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente extends GenericModel {
	
	private Long CPF;
	
	private String nome;

	private Integer idade;

	private String endereco;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Carrinho carrinho;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Compra> compras;
	
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;
}
