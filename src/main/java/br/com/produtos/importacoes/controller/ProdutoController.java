package br.com.produtos.importacoes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.produtos.importacoes.model.Produto;
import br.com.produtos.importacoes.service.EncomendaService;
import br.com.produtos.importacoes.service.ProdutoService;
import br.com.produtos.importacoes.service.UsuarioService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@Autowired
	private UsuarioService uservice;

	@Autowired
	private EncomendaService eservice;

	@GetMapping("/produtos")
	public ModelAndView list(Produto produto, RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("produto", produto);
		modelAndView.addObject("produtos", service.obterTodos(uservice.usuarioLogado()));
		return modelAndView;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		attributes.addFlashAttribute("mensagem", "Produto removido com sucesso");
		service.delete(id);
		return new ModelAndView("redirect:/produtos");
	}

	@GetMapping("/carregaEdicaoProduto/{id}")
	@ResponseBody
	public String carregaEdicao(@PathVariable("id") Long id) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(service.findOne(id));

	}

	@PostMapping("/produtos")
	public ModelAndView save(Produto produto, RedirectAttributes attributes) {
		produto.setUsuarios(uservice.usuarioLogado());
		String mensagem = (produto.getId() == null) ? "Adicionado" : "Alterado";
		attributes.addFlashAttribute("mensagemSave", "Produto " + mensagem + " com sucesso");
		service.salvar(produto);
		return new ModelAndView("redirect:/produtos");
	}
	
//	@ExceptionHandler(Exception.class)
//    public ModelAndView handleError(HttpServletRequest request, Exception e)   {
//		System.out.println("Deu um erro 500");
//		return new ModelAndView("redirect:/produtos");
//    }

}
