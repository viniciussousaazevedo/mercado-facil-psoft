package com.ufcg.psoft.mercadofacil.model.taxaEmProdutos;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;


@Component
public abstract class TaxaEmProdutos {

	public abstract BigDecimal adicionaTaxa();
	
}
