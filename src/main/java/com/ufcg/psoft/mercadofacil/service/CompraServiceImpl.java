package com.ufcg.psoft.mercadofacil.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.DTO.CompraDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemProduto;
import com.ufcg.psoft.mercadofacil.model.enuns.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.model.enuns.TipoCliente;
import com.ufcg.psoft.mercadofacil.model.enuns.TipoDeEntrega;
import com.ufcg.psoft.mercadofacil.model.taxaEmEndereco.EntegaEnderecoSemTaxa;
import com.ufcg.psoft.mercadofacil.model.taxaEmEndereco.EntregaNaoParaibano;
import com.ufcg.psoft.mercadofacil.model.taxaEmEndereco.EntregaParaibano;
import com.ufcg.psoft.mercadofacil.model.taxaEmEndereco.TaxaEmEndereco;
import com.ufcg.psoft.mercadofacil.model.taxaEmEntrega.EntregaExpress;
import com.ufcg.psoft.mercadofacil.model.taxaEmEntrega.EntregaPadrao;
import com.ufcg.psoft.mercadofacil.model.taxaEmEntrega.EntregaRetirada;
import com.ufcg.psoft.mercadofacil.model.taxaEmEntrega.TaxaEmEntrega;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.EntregaComum;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.EntregaFragil;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.EntregaProdutoSemTaxa;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.EntregaRefrigeracao;
import com.ufcg.psoft.mercadofacil.model.taxaEmProdutos.TaxaEmProdutos;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;

@Service
public class CompraServiceImpl implements CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	private TaxaEmEndereco taxaEmEndereco;
	
	private TaxaEmProdutos taxaEmProdutos;
	
	private TaxaEmEntrega taxaEmEntrega;
	
	@Override
	public List<FormaDePagamento> getFormasDePagamentoDisponiveis() {
		return Arrays.asList(FormaDePagamento.values());
	}
	
	@Override
	public Compra criaCompra(Carrinho carrinho, Cliente cliente, CompraDTO compraDTO) {
		Compra compra = new Compra(
				new Date(), 
				compraDTO.getFormaDePagamento(), 
				adicionaItensDoCarrinho(carrinho)
				);
		
		if (cliente.getCompras() == null)
			cliente.setCompras(new ArrayList<Compra>());
		
		calculaTaxas(compra, compraDTO.getTipoDeEntrega(), cliente.getEndereco());
		calculaValorTotal(compra, carrinho.getItens(), cliente, compraDTO.getFormaDePagamento());
		
		cliente.getCompras().add(compra);
		this.carrinhoService.esvaziaCarrinho(carrinho);
		this.clienteRepository.save(cliente);
		return compra;
	}
	
	private List<ItemProduto> adicionaItensDoCarrinho(Carrinho carrinho) {
		List<ItemProduto> listaItens = new ArrayList<>();
		for (ItemProduto item : carrinho.getItens()) {
			listaItens.add(new ItemProduto(item.getProduto(), item.getQtdItens()));
		}
		
		return listaItens;
	}
	
	private void calculaTaxas(Compra compra, TipoDeEntrega tipoDeEntrega, String enderecoCliente) {
		if (tipoDeEntrega == TipoDeEntrega.RETIRADA) {
			this.taxaEmEntrega = new EntregaRetirada();
			this.taxaEmProdutos = new EntregaProdutoSemTaxa();
			this.taxaEmEndereco = new EntegaEnderecoSemTaxa();
			return;
			
		} else if (tipoDeEntrega == TipoDeEntrega.PADRAO) {
			this.taxaEmEntrega = new EntregaPadrao();
			
		} else if (tipoDeEntrega == TipoDeEntrega.EXPRESS) {
			this.taxaEmEntrega = new EntregaExpress();
		}
		
		this.taxaEmProdutos = obtemTaxaEmProdutos(compra);
		this.taxaEmEndereco = obtemTaxaEmEndereco(enderecoCliente);
	}
	
	private TaxaEmProdutos obtemTaxaEmProdutos(Compra compra) {
		boolean haProdutosFrageis = false;
		for (ItemProduto item : compra.getItens()) {
			if (item.getProduto().isRefrigerado()) {
				return new EntregaRefrigeracao();
			} else if (item.getProduto().isFragil()) {
				haProdutosFrageis = true;
			}
		}
		
		if (haProdutosFrageis) {
			return new EntregaFragil();
		}
		
		return new EntregaComum();
	}
	
	private TaxaEmEndereco obtemTaxaEmEndereco(String enderecoCliente) {
		if (enderecoCliente.toLowerCase().contains("paraiba") ||
			enderecoCliente.toLowerCase().contains("pb")) {
			return new EntregaParaibano();
		}
		return new EntregaNaoParaibano();
	}

	private void calculaValorTotal(Compra compra, List<ItemProduto> itens, Cliente cliente, FormaDePagamento formaDePagamento) {
		// Soma dos produtos presentes
		int qtdItens = 0;
		BigDecimal valorTotal = new BigDecimal(0);
		for (ItemProduto item : itens) {
			valorTotal = valorTotal.add(
					item.getProduto().getPreco().multiply(new BigDecimal(item.getQtdItens())));
			qtdItens += item.getQtdItens();
		}
		
		// Taxa adicional com base na forma de pagamento
		if (formaDePagamento.equals(FormaDePagamento.PAYPAL)) {
			valorTotal = valorTotal.multiply(new BigDecimal(1.02));
		} else if (formaDePagamento.equals(FormaDePagamento.CREDITO)) {
			valorTotal = valorTotal.multiply(new BigDecimal(1.05));
		}
		// Desconto com base no tipo do cliente
		if (
				(qtdItens > 10 && cliente.getTipoCliente().equals(TipoCliente.ESPECIAL)) ||
				(qtdItens > 5 && cliente.getTipoCliente().equals(TipoCliente.PREMIUM))
			) {
			valorTotal = valorTotal.multiply(new BigDecimal(0.9));
		}
		
		// Adição de taxas de entrega no valor final
		valorTotal = valorTotal
				.add(this.taxaEmEntrega.adicionaTaxa())
				.add(this.taxaEmProdutos.adicionaTaxa())
				.add(this.taxaEmEndereco.adicionaTaxa());
		
		compra.setValorTotal(valorTotal);
	}

	@Override
	public Optional<Compra> getCompraById(Long idCompra) {
		return this.compraRepository.findById(idCompra);
	}
}
