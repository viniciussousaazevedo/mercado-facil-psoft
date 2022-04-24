package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.mercadofacil.DTO.ItemProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemProduto;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;

public interface CarrinhoService {
	
	public Carrinho addItem(ItemProdutoDTO itemProduto, Optional<Carrinho> carrinhoOp, Optional<Produto> produtoOp, List<Lote> lotesDisponiveis);
	
	public Optional<Carrinho> findCarrinhoById(Long id);
	
	public Carrinho removeItem(Carrinho carrinho, ItemProduto item, int qtde);
	
	public void esvaziaCarrinho(Carrinho carrinho);
	
}
