package com.ufcg.psoft.mercadofacil.model.taxaEmEntrega;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EntregaRetirada extends TaxaEmEntrega {

	@Override
	public BigDecimal adicionaTaxa() {
		return new BigDecimal(0);
	}

}
