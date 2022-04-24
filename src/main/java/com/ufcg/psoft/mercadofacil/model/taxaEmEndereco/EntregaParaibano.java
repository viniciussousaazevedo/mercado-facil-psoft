package com.ufcg.psoft.mercadofacil.model.taxaEmEndereco;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EntregaParaibano extends TaxaEmEndereco {

	@Override
	public BigDecimal adicionaTaxa() {
		return new BigDecimal(4);
	}

}
