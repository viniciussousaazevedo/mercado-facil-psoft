package com.ufcg.psoft.mercadofacil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByCPF(long cpf);
	
	Optional<Cliente> getClienteByCarrinhoId(Long carrinhoId);
}
