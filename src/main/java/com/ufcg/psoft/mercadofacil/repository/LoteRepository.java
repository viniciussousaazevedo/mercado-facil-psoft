package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Lote;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long>{

	List<Lote> findAllByProdutoId(Long id);
}