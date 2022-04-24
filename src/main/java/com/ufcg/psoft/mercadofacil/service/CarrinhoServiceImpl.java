package com.ufcg.psoft.mercadofacil.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.ItemProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.ItemProduto;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ItemProdutoRepository;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private ItemProdutoRepository itemProdutoRepository;
	
	@Override
	public Carrinho addItem(ItemProdutoDTO itemProdutoDTO, Optional<Carrinho> carrinhoOp, Optional<Produto> produtoOp, List<Lote> lotesDisponiveis) {
		Carrinho carrinho = carrinhoOp.get();
		if (carrinho.getItens() == null) 
			carrinho.setItens(new ArrayList<ItemProduto>());
		
		removeProdutoDoLote(itemProdutoDTO.getQtdItens(), lotesDisponiveis);
		return adicionaProdutoNoCarrinho(carrinho, itemProdutoDTO, produtoOp.get());
	}
	
	private void removeProdutoDoLote(int qtdItensRestantes, List<Lote> lotesDisponiveis) {
		for (Lote lote : lotesDisponiveis) {
			if (qtdItensRestantes >= lote.getNumeroDeItens()) {
				qtdItensRestantes -= lote.getNumeroDeItens();
				lote.setNumeroDeItens(0);
			} else {
				lote.setNumeroDeItens(lote.getNumeroDeItens() - qtdItensRestantes);
				qtdItensRestantes = 0;
			}
			
			if (qtdItensRestantes == 0) {
				break;
			}
			this.loteRepository.save(lote);
		}
	}
	
	private Carrinho adicionaProdutoNoCarrinho(Carrinho carrinho, ItemProdutoDTO itemProdutoDTO, Produto produto) {
		boolean isProdutoPresente = false;
		for (ItemProduto item : carrinho.getItens()) {
			if (item.getProduto().getId().equals(itemProdutoDTO.getProdutoId())) {
				item.setQtdItens(item.getQtdItens() + itemProdutoDTO.getQtdItens());
				isProdutoPresente = true;
				break;
			}
		}
		
		if (!isProdutoPresente) {
			ItemProduto itemProduto = new ItemProduto(produto, itemProdutoDTO.getQtdItens());
			carrinho.getItens().add(itemProduto);
		}
		
		return this.carrinhoRepository.save(carrinho);
	}

	public Carrinho removeItem(Carrinho carrinho, ItemProduto item, int qtde) {
		adicionaProdutoNoLote(item, qtde);
		return removeProdutoDoCarrinho(carrinho, item, qtde);
	}
	
	private void adicionaProdutoNoLote(ItemProduto item, int qtde) {
		Lote lote = this.loteRepository.findAllByProdutoId(item.getProduto().getId()).get(0);
		lote.setNumeroDeItens(lote.getNumeroDeItens() + qtde);
		this.loteRepository.save(lote);
	}
	
	private Carrinho removeProdutoDoCarrinho(Carrinho carrinho, ItemProduto item, int qtde) {
		if (item.getQtdItens() == qtde) {
			carrinho.getItens().remove(item);
		} else {
			item.setQtdItens(item.getQtdItens() - qtde);
			this.itemProdutoRepository.save(item);
		}
		
		return this.carrinhoRepository.save(carrinho);
	}
	
	public Optional<Carrinho> findCarrinhoById(Long id) {
		return this.carrinhoRepository.findById(id);
	}

	@Override
	public void esvaziaCarrinho(Carrinho carrinho) {
		carrinho.getItens().clear();
	}
	
}
