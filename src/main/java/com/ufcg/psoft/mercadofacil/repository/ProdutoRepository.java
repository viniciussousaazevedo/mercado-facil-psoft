package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	List<Produto> findByCodigoBarra(String codigoBarra);
}
