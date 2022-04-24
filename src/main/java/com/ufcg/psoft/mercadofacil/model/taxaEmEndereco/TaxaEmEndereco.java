package com.ufcg.psoft.mercadofacil.model.taxaEmEndereco;

import java.math.BigDecimal;


import org.springframework.stereotype.Component;

@Component
public abstract class TaxaEmEndereco {

	public abstract BigDecimal adicionaTaxa();
}
