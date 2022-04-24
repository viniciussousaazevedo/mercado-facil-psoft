package com.ufcg.psoft.mercadofacil.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.model.taxaEmEndereco.TaxaEmEndereco;
import com.ufcg.psoft.mercadofacil.model.taxaEmEntrega.TaxaEmEntrega;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.TaxaEmProdutos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Compra extends GenericModel {
	
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ItemProduto> itens;
	
	private BigDecimal valorTotal;
	
	public Compra(Date data, FormaDePagamento formaDePagamento, List<ItemProduto> itens) {
		this.data = data;
		this.formaDePagamento = formaDePagamento;
		this.itens = itens;
	}
	
}
