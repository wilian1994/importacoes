package br.com.produtos.importacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Iterable<Usuario> obterTodos() {

		Iterable<Usuario> categorias = repository.findAll();

		return categorias;

	}

	public Usuario findByLogin(String login) {
		return repository.findByLoginAndAtivo(login, true);
	}

	public List<Usuario> list() {
		return repository.findAll();
	}

	public void salvar(Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		repository.save(usuario);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	public Usuario usuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = repository.findByLoginAndAtivo(authentication.getName(), true);
		return usuario;
	}
	
	public Usuario verificarUsuario(String email){
		return repository.findByEmail(email);
	}

}
