package com.ufcg.psoft.mercadofacil.model.taxaEmEntrega;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

@Component
public abstract class TaxaEmEntrega {

	public abstract BigDecimal adicionaTaxa();
	
}
