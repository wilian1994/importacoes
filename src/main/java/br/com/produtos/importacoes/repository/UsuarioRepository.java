package br.com.produtos.importacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.produtos.importacoes.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLoginAndAtivo(String login, Boolean motivo);
	
	Usuario findByEmail(String email);
	
	
}