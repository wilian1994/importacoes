package br.com.produtos.importacoes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.service.PermissaoService;
import br.com.produtos.importacoes.service.UsuarioService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private PermissaoService pservice;

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		Object loginError = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (loginError != null) {
			model.addAttribute("loginError", "Usuário ou senha inválida");
			request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		return "usuarios/login";
	}

	@GetMapping("/logout")
	public String getLogoutPage(HttpServletRequest request, HttpServletResponse response) {

		return "usuario/login?logout";
	}

	@GetMapping("/users")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("usuarios/listagem");
		modelAndView.addObject("usuarios", service.list());
		return modelAndView;
	}

	@GetMapping
	public ModelAndView form(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form");
		modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("permissoes", pservice.obterTodos());
		return modelAndView;
	}

	@PostMapping
	public String save(Usuario usuario, RedirectAttributes attributes) {
		String mensagem = (usuario.getId() == null) ? "Adicionado" : "Alterado";
		attributes.addFlashAttribute("mensagemSave", "Usuario " + mensagem + " com sucesso");
		service.salvar(usuario);
		return "redirect:/usuario";
	}

	@GetMapping("/isAtivo/{id}")
	public String isAtivo(@PathVariable("id") Long id) {
		Usuario usuario = service.findOne(id);
		boolean isAtivo = usuario.isAtivo() ? false : true;
		usuario.setAtivo(isAtivo);
		service.salvar(usuario);
		return "redirect:/usuario/users";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "redirect:/usuario/users";
	}

	@GetMapping("/edit/{id}")
	@CacheEvict(value = "usuarios", allEntries = true)
	public ModelAndView editar(@PathVariable("id") Long id) {
		return form(service.findOne(id));
	}

	@ResponseBody
	@RequestMapping("finalizaTarefa")
	public String finalizaTarefa(Long id) throws JsonProcessingException {
		Usuario usuario = service.findOne(id);
		boolean isAtivo = usuario.isAtivo() ? false : true;
		usuario.setAtivo(isAtivo);
		service.salvar(usuario);
		return new ObjectMapper().writeValueAsString(usuario);
	}

}
