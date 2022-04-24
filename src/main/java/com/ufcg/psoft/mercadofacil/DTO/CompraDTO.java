package com.ufcg.psoft.mercadofacil.DTO;

import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.model.enuns.TipoCliente;
import com.ufcg.psoft.mercadofacil.model.enuns.TipoDeEntrega;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompraDTO {

	FormaDePagamento formaDePagamento;
	
	TipoDeEntrega tipoDeEntrega;
}
