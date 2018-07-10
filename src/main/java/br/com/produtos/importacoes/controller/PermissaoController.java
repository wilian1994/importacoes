package br.com.produtos.importacoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.produtos.importacoes.model.Permissao;
import br.com.produtos.importacoes.service.PermissaoService;

@Controller
@RequestMapping("/permissao")
public class PermissaoController {

	@Autowired
	private PermissaoService service;

	@GetMapping
	public ModelAndView formPermissao(Permissao permissao) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form_permissao");
		modelAndView.addObject("permissao", permissao);
		return modelAndView;
	}

	@PostMapping
	public String savePermissao(Permissao permissao, RedirectAttributes attributes) {
		String mensagem = (permissao.getId() == null) ? "Adicionada" : "Alterada";
		attributes.addFlashAttribute("mensagemSave", "Permissão " + mensagem + " com sucesso");
		service.salvar(permissao);
		return "redirect:/permissao";
	}

}
