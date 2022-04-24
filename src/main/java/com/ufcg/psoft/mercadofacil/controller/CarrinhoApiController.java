package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.DTO.ItemProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemProduto;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ClienteService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroCarrinho;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {

	@Autowired
	private CarrinhoService carrinhoService;
	
	@Autowired
	private LoteService loteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/carrinho/{idCarrinho}")
	public ResponseEntity<?> listaItens(@PathVariable Long idCarrinho) {
		Optional<Carrinho> carrinhoOp = this.carrinhoService.findCarrinhoById(idCarrinho);
		
		if (carrinhoOp.isEmpty()) {
			return ErroCarrinho.erroCarrinhoNaoCadastrado();
		}
		
		return new ResponseEntity<List<ItemProduto>>(carrinhoOp.get().getItens(), HttpStatus.CREATED);
	}
	
	@PostMapping("/carrinho/{id}")
	public ResponseEntity<?> addItens(@RequestBody ItemProdutoDTO itemProdutoDTO, @PathVariable Long id) {
	
		Optional<Carrinho> carrinhoOp = this.carrinhoService.findCarrinhoById(id);
		Optional<Produto> produtoOp = this.produtoService.getProdutoById(itemProdutoDTO.getProdutoId());
		List<Lote> loteList = this.loteService.listarPorProdutoId(itemProdutoDTO.getProdutoId());
		
		if (carrinhoOp.isEmpty()) {
			return ErroCarrinho.erroCarrinhoNaoCadastrado();
		} else if (produtoOp.isEmpty()) {
			return ErroProduto.erroProdutoNaoEnconrtrado(itemProdutoDTO.getProdutoId());
		} else if (loteList == null || loteList.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		} else if (getQtdProdutoEmLotes(loteList) < itemProdutoDTO.getQtdItens()) {
			return ErroLote.erroQuantidadeInsuficiente();
		}
		
		return new ResponseEntity<Carrinho>
			(
				this.carrinhoService.addItem(itemProdutoDTO, carrinhoOp, produtoOp, loteList)
				, HttpStatus.CREATED
			);
	}
	
	private int getQtdProdutoEmLotes(List<Lote> lotesDisponiveis) {
		int qtdProdutoEmLotes = 0;
		for (Lote lote : lotesDisponiveis) {
			qtdProdutoEmLotes += lote.getNumeroDeItens();
		}
		return qtdProdutoEmLotes;
	}
	
	@PutMapping("/carrinho/{id}/remover-item")
	public ResponseEntity<?> removeItem(@RequestBody ItemProdutoDTO itemProdutoDTO, @PathVariable Long id) {
		
		Optional<Carrinho> carrinhoOp = this.carrinhoService.findCarrinhoById(id);
		Optional<Produto> produtoOp = this.produtoService.getProdutoById(itemProdutoDTO.getProdutoId());
		
		if (carrinhoOp.isEmpty()) {
			return ErroCarrinho.erroCarrinhoNaoCadastrado();
		} else if (produtoOp.isEmpty()) {
			return ErroProduto.erroProdutoNaoEnconrtrado(itemProdutoDTO.getProdutoId());
		}
		
		Carrinho carrinho = carrinhoOp.get();
		ItemProduto itemNoCarrinho = getItemNoCarrinho(carrinho, itemProdutoDTO);
		if (itemNoCarrinho == null) {
			return ErroCarrinho.erroItemIndiponivelParaRemocao();
		}
		
		return new ResponseEntity<Carrinho>
			(
				this.carrinhoService.removeItem(carrinho, itemNoCarrinho, itemProdutoDTO.getQtdItens())
				, HttpStatus.OK
			);
	}
	
	private ItemProduto getItemNoCarrinho(Carrinho carrinho, ItemProdutoDTO itemProdutoDTO) {
		ItemProduto itemParaRemocao = null;
		for (ItemProduto item : carrinho.getItens()) {
			if (item.getProduto().getId().equals(itemProdutoDTO.getProdutoId()) &&
				item.getQtdItens() >= itemProdutoDTO.getQtdItens()) {
				itemParaRemocao = item;
				break;
			}
		}
		
		return itemParaRemocao;
	}
	
	@PostMapping("/carrinho/{idCarrinho}/finalizar-carrinho")
	public ResponseEntity<?> finalizarCarrinho(@PathVariable Long idCarrinho, CompraDTO compraDTO) {
		
		Optional<Carrinho> carrinhoOp = this.carrinhoService.findCarrinhoById(idCarrinho);
		Optional<Cliente> clienteOp = this.clienteService.getClienteByCarrinhoId(idCarrinho);
		
		if (carrinhoOp.isEmpty()) {
			return ErroCarrinho.erroCarrinhoNaoCadastrado();
		} else if (clienteOp.isEmpty()) {
			return ErroCliente.erroClienteNaoEnconrtrado(0);
		}
		
		Carrinho carrinho = carrinhoOp.get();
		if (carrinho.getItens().isEmpty()) {
			return ErroCarrinho.erroImpossivelFinalizarCarrinho();
		}
		
		Compra compra = this.compraService.criaCompra(carrinho, clienteOp.get(), compraDTO);
		return new ResponseEntity<Compra>(compra, HttpStatus.CREATED);
	}
	
}
