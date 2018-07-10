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

import br.com.produtos.importacoes.model.Categoria;
import br.com.produtos.importacoes.service.CategoriaService;
import br.com.produtos.importacoes.service.UsuarioService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@Autowired
	private UsuarioService uservice;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping
	public ModelAndView list(Categoria categoria) {
		ModelAndView modelAndView = new ModelAndView("categorias/form");
		modelAndView.addObject("categoria", categoria);
		modelAndView.addObject("categorias", service.obterTodos(uservice.usuarioLogado()));
		return modelAndView;
	}

	@GetMapping("/carregaEdicaoCategoria/{id}")
	@ResponseBody
	public String carregaEdicao(@PathVariable("id") Long id) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(service.findOne(id));

	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		attributes.addFlashAttribute("mensagem", "Encomenda removida com sucesso");
		service.delete(id);
		return new ModelAndView("redirect:/categorias");
	}

	@PostMapping
	public String save(Categoria categoria, RedirectAttributes attributes) {
		categoria.setUsuario(uservice.usuarioLogado());
		String mensagem = (categoria.getId() == null) ? "Adicionada" : "Alterada";
		attributes.addFlashAttribute("mensagemSave", "Categoria " + mensagem + " com sucesso");
		service.salvar(categoria);
		return "redirect:/categorias";
	}

	// @GetMapping("save")
	// public ModelAndView save(Produto produto){
	//
	// }

}
