package br.com.produtos.importacoes.controller;


import java.util.UUID;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.service.EmailService;
import br.com.produtos.importacoes.service.UsuarioService;

@Controller
@RequestMapping("email")
public class EmailController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/info")
	@PermitAll
	public ModelAndView info() {
		ModelAndView modelAndView = new ModelAndView("usuarios/info");
		Usuario usuario = service.usuarioLogado();
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@PostMapping("alterUser")
	public String alteraUsuario(Usuario usuario, RedirectAttributes attributes, @RequestParam("senhaNovamente") String senhaNovamente){
		System.out.println(usuario);
		
		if(!usuario.getSenha().equals(senhaNovamente)){
			attributes.addFlashAttribute("erro", "Senhas não conferem");
			return "redirect:/email/info";
		}
		
		return "redirect:/email/info";
	}
	
	@PostMapping("esqueceuSenha")
	public String senha( @RequestParam("email") String email){
		
		Usuario usuario = service.verificarUsuario(email);
		
		if(usuario != null){
			
			UUID uuid = UUID.randomUUID();  
			String myRandom = uuid.toString();  
			String senha = myRandom.substring(0,6);
			usuario.setSenha(senha);
			service.salvar(usuario);
			System.out.println(usuario.getSenha());
			new EmailService().enviar(usuario.getNome(), email, senha);
			
		}
		
		return "usuarios/login";
	}

	
}
