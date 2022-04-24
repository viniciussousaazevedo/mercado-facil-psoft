package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroCompra;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraApiController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	CompraService compraService;
	
	@GetMapping("/compras/{idCliente}/cliente")
	public ResponseEntity<?> listaCompras(@PathVariable Long idCliente) {
		
		Optional<Cliente> cliente = this.clienteService.getClienteById(idCliente);
		
		if (cliente.isEmpty()) {
			return ErroCliente.erroSemClientesCadastrados();
		}
		
		return new ResponseEntity<List<Compra>>(cliente.get().getCompras(), HttpStatus.OK);
	}
	
	@GetMapping("/compras")
	public ResponseEntity<?> listaFormasDePagamentoDisponiveis() {
		return new ResponseEntity<List<FormaDePagamento>>(compraService.getFormasDePagamentoDisponiveis(), HttpStatus.OK);
	}
	
	@GetMapping("/compras/{idCompra}")
	public ResponseEntity<?> getCompra(@PathVariable Long idCompra) {
		Optional<Compra> compra = this.compraService.getCompraById(idCompra);
		
		
		if (compra.isEmpty()) {
			return ErroCompra.erroCompraInexistente();
		}
		
		return new ResponseEntity<Compra>(compra.get(), HttpStatus.OK);
	}
	
}
