package com.ufcg.psoft.mercadofacil.model.taxaEmProdutos;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EntregaRefrigeracao extends TaxaEmProdutos {

	@Override
	public BigDecimal adicionaTaxa() {
		return new BigDecimal(6);
	}

}
