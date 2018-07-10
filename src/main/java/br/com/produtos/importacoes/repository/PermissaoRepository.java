package br.com.produtos.importacoes.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.produtos.importacoes.model.Permissao;
import br.com.produtos.importacoes.model.Usuario;

public interface PermissaoRepository extends CrudRepository<Permissao, Long> {
	
	List<Permissao> findByUsuariosIn(Usuario usuario);

}
