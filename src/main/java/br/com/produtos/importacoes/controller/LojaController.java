package br.com.produtos.importacoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.produtos.importacoes.model.Loja;
import br.com.produtos.importacoes.service.LojaService;
import br.com.produtos.importacoes.service.UsuarioService;

@Controller
@RequestMapping("/loja")
public class LojaController {

	@Autowired
	private LojaService service;
	
	@Autowired
	private UsuarioService uservice;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping
	public ModelAndView list(Loja loja) {
		ModelAndView modelAndView = new ModelAndView("loja/form");
		modelAndView.addObject("loja", loja);
		modelAndView.addObject("lojas", service.obterTodos(uservice.usuarioLogado()));
		return modelAndView;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		attributes.addFlashAttribute("mensagem", "Loja removida com sucesso");
		service.delete(id);
		return new ModelAndView("redirect:/loja");
	}

	@GetMapping("/carregaEdicaoLoja/{id}")
	@ResponseBody
	public String carregaEdicao(@PathVariable("id") Long id) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(service.findOne(id));

	}
	
	@PostMapping
	public String save(Loja loja, RedirectAttributes attributes){
		loja.setUsuario(uservice.usuarioLogado());
		String mensagem = (loja.getId() == null) ? "Adicionada" : "Alterada";
		attributes.addFlashAttribute("mensagemSave", "Loja " + mensagem + " com sucesso");
		service.salvar(loja);
		return "redirect:/loja";
	}
	
	// @GetMapping("save")
	// public ModelAndView save(Produto produto){
	//
	// }

}
