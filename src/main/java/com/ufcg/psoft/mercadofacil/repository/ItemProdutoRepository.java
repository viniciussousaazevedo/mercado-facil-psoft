package com.ufcg.psoft.mercadofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.ItemProduto;

@Repository
public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Long> {
	
}
