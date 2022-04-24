package com.ufcg.psoft.mercadofacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
	
}
