package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;

public interface CompraService {
	
	Compra criaCompra(Carrinho carrinho, Cliente cliente, CompraDTO compraDTO);
	
	List<FormaDePagamento> getFormasDePagamentoDisponiveis();
	
	Optional<Compra> getCompraById(Long idCompra);
}
