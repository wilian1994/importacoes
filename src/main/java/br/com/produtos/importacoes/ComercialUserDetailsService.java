package br.com.produtos.importacoes;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.produtos.importacoes.model.Permissao;
import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.PermissaoRepository;
import br.com.produtos.importacoes.repository.UsuarioRepository;

@Component
public class ComercialUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarios;

	@Autowired
	private PermissaoRepository permissoes;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarios.findByLoginAndAtivo(username, true);
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Permissao role : user.getPermissoes()) {
				grantedAuthorities.	add(new SimpleGrantedAuthority(role.getNome()));
			}

			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), grantedAuthorities);
	}

}