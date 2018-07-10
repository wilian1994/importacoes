package br.com.produtos.importacoes.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.produtos.importacoes.model.Categoria;
import br.com.produtos.importacoes.model.Encomenda;
import br.com.produtos.importacoes.model.Loja;
import br.com.produtos.importacoes.model.Produto;
import br.com.produtos.importacoes.model.Status;
import br.com.produtos.importacoes.service.EncomendaService;
import br.com.produtos.importacoes.service.UsuarioService;

@Controller
public class DashboardController {

	@Autowired
	private EncomendaService service;
	
	@Autowired
	private UsuarioService uservice;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("dashboards/index");
		return modelAndView;
	}

	@ModelAttribute("vendas")
	public String getTotalDeVendas() {
		List<Encomenda> valorPagoFinalizados = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		BigDecimal valor = valorPagoFinalizados.stream().map(Encomenda::getValorVenda).reduce(BigDecimal.ZERO, BigDecimal::add);
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	@ModelAttribute("gasto")
	public String getTotalDeGastos() {
		List<Encomenda> valorGastoFinalizados = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		BigDecimal valor = valorGastoFinalizados.stream().map(Encomenda::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	@ModelAttribute("lucro")
	public String getLucro() {
		List<Encomenda> valorGastoFinalizados = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		BigDecimal valor = valorGastoFinalizados.stream().map(Encomenda::getValorVenda).reduce(BigDecimal.ZERO, BigDecimal::add)
				.subtract(valorGastoFinalizados.stream().map(Encomenda::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add));
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	@ModelAttribute("topLoja")
	public Map<Loja, BigDecimal> getTopLoja() {

		/*
		 * getTopLoja result: Obtém todas os valores agrupados por loja,
		 * finalMap: Ordena o resultado pelo maior valor e limita(usando o
		 * limit) até 5 valores.
		 */
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		Map<Loja, BigDecimal> result = topEncomendas.stream().collect(
				Collectors.groupingBy(Encomenda::getLoja,
						Collectors.mapping(Encomenda::getValorVenda, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		Map<Loja, BigDecimal> finalMap = new LinkedHashMap<>();
		result.entrySet().stream().sorted(Map.Entry.<Loja, BigDecimal> comparingByValue().reversed()).limit(5)
				.forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

		return finalMap;
	}

	@ModelAttribute("topCategoria")
	public Map<Categoria, BigDecimal> getTopCategoria() {
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		Map<Categoria, BigDecimal> result = topEncomendas.stream().collect(
				Collectors.groupingBy(Encomenda::getCategoria,
						Collectors.mapping(Encomenda::getValorVenda, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		Map<Categoria, BigDecimal> finalMap = new LinkedHashMap<>();
		result.entrySet().stream().sorted(Map.Entry.<Categoria, BigDecimal> comparingByValue().reversed()).limit(5)
				.forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

		return finalMap;
	}

	@ModelAttribute("topProduto")
	public Map<Produto, BigDecimal> getTopProduto() {
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.FINALIZADA);
		Map<Produto, BigDecimal> result = topEncomendas.stream().collect(
				Collectors.groupingBy(Encomenda::getProduto,
						Collectors.mapping(Encomenda::getValorVenda, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		Map<Produto, BigDecimal> finalMap = new LinkedHashMap<>();
		result.entrySet().stream().sorted(Map.Entry.<Produto, BigDecimal> comparingByValue().reversed()).limit(5)
				.forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

		return finalMap;
	}

	@ModelAttribute("valorProdutoAguardando")
	public BigDecimal getValorProdutoAguardando() {
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.TRANSPORTE);
		BigDecimal valor = topEncomendas.stream().map(Encomenda::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);

		return valor;
	}

	@ModelAttribute("valorProdutoRecebido")
	public BigDecimal getValorProdutoRecebido() {
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(),Status.RECEBIDO);
		BigDecimal valor = topEncomendas.stream().map(Encomenda::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);
		return valor;
	}

	@ModelAttribute("valorProdutoAR")
	public BigDecimal getValorProdutoAR() {
		return getValorProdutoAguardando().add(getValorProdutoRecebido());
	}

	@ModelAttribute("estoqueSeguranca")
	public Map<Categoria, Long> getEstoqueSeguranca() {
		List<Encomenda> topEncomendas = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(), Status.TRANSPORTE);
		List<Encomenda> topEncomendas2 = (List<Encomenda>) service.findByUsuariosAndStatus(uservice.usuarioLogado(), Status.RECEBIDO);
		topEncomendas.addAll(topEncomendas2);
		Map<Categoria, Long> result = topEncomendas.stream().collect(
				Collectors.groupingBy(Encomenda::getCategoria,
						Collectors.counting()));
		return result;

	}
	

}
