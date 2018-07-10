package br.com.produtos.importacoes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.produtos.importacoes.model.Encomenda;
import br.com.produtos.importacoes.model.Status;
import br.com.produtos.importacoes.service.CategoriaService;
import br.com.produtos.importacoes.service.EncomendaService;
import br.com.produtos.importacoes.service.LojaService;
import br.com.produtos.importacoes.service.ProdutoService;
import br.com.produtos.importacoes.service.UsuarioService;

@Controller
@RequestMapping("/encomendas")
public class EncomendaController {

	@Autowired
	private EncomendaService service;

	@Autowired
	private ProdutoService pservice;

	@Autowired
	private LojaService lservice;

	@Autowired
	private CategoriaService cservice;

	@Autowired
	private UsuarioService uservice;

	@GetMapping
	public ModelAndView form(Encomenda encomenda) {

		ModelAndView modelAndView = new ModelAndView("encomendas/form");
		modelAndView.addObject("encomenda", encomenda);
		modelAndView.addObject("produtos", pservice.obterTodos(uservice.usuarioLogado()));
		modelAndView.addObject("lojas", lservice.obterTodos(uservice.usuarioLogado()));
		modelAndView.addObject("categorias", cservice.obterTodos(uservice.usuarioLogado()));
		return modelAndView;
	}

	@PostMapping
	public ModelAndView save(Encomenda encomenda, RedirectAttributes attributes) {
		encomenda.setUsuarios(Arrays.asList(uservice.usuarioLogado()));
		if (encomenda.getId() != null) {
			service.salvar(encomenda);
			attributes.addFlashAttribute("mensagem", "Encomenda alterada com sucesso");
			return new ModelAndView("redirect:/encomendas");
		}
		encomenda.setDataCompra(Calendar.getInstance());
		encomenda.setStatus(Status.TRANSPORTE);
		Encomenda encomendaQtd = null;

		for (int i = 0; i < encomenda.getQtd(); i++) {
			encomendaQtd = new Encomenda();
			BeanUtils.copyProperties(encomenda, encomendaQtd, "getId");
			service.salvar(encomendaQtd);
		}
		attributes.addFlashAttribute("mensagem", "Encomendas(s) adicionada com sucesso");
		return new ModelAndView("redirect:/encomendas");
	}

	@GetMapping("/aguardando")
	public ModelAndView aguardando() {
		ModelAndView modelAndView = new ModelAndView("encomendas/aguardando");
		modelAndView.addObject("encomendas", service.findByUsuariosAndStatus(uservice.usuarioLogado(), Status.TRANSPORTE));
		return modelAndView;
	}

	@GetMapping("/recebidos")
	public ModelAndView recebidos() {
		ModelAndView modelAndView = new ModelAndView("encomendas/recebidos");
		modelAndView.addObject("encomendas", service.findByUsuariosAndStatus(uservice.usuarioLogado(), Status.RECEBIDO));
		return modelAndView;
	}

	@GetMapping("/finalizados")
	public ModelAndView finalizados() {
		ModelAndView modelAndView = new ModelAndView("encomendas/finalizados");
		modelAndView.addObject("encomendas", service.findByUsuariosAndStatus(uservice.usuarioLogado(), Status.FINALIZADA));
		return modelAndView;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "redirect:/encomendas/aguardando";
	}

	@GetMapping("/edit/{id}")
	@CacheEvict(value = "encomendas", allEntries = true)
	public ModelAndView editar(@PathVariable("id") Long id) {
		return form(service.findOne(id));
	}

	@GetMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Long id) throws IOException {
		ModelAndView modelAndView = new ModelAndView("encomendas/view");
		Encomenda encomenda = service.findOne(id);
		modelAndView.addObject("encomenda", encomenda);
		modelAndView.addObject("rastreios", getBuscaRastreio(encomenda.getRastreio()));
		return modelAndView;
	}

	@GetMapping("/recebida/{id}")
	public String encomendaRecebida(@PathVariable("id") Long id) {
		Encomenda encomenda = service.findOne(id);
		encomenda.setStatus(Status.RECEBIDO);
		encomenda.setDataRecebida(Calendar.getInstance());
		service.salvar(encomenda);
		return "redirect:/encomendas/aguardando";
	}

	@PostMapping("finalizada")
	public String encomendaFinalizada(Encomenda encomenda) {
		Encomenda encomendaNova = service.findOne(encomenda.getId());
		encomendaNova.setStatus(Status.FINALIZADA);
		encomendaNova.setValorVenda(encomenda.getValorVenda());
		encomendaNova.setDataVenda(Calendar.getInstance());
		service.salvar(encomendaNova);
		return "redirect:/encomendas/recebidos";
	}

	public List<String> getBuscaRastreio(String rastreio) throws IOException {

		List<String> rastreios = new ArrayList<String>();
		Document document;
		document = Jsoup.connect("https://correiosrastrear.com/?tracking_field=" + rastreio).get();
		Elements elements = document.getElementsByTag("tr");

		for (Element element : elements) {

			rastreios.add(element.text());

		}

		return rastreios;

	}

}
