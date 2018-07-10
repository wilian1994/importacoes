package br.com.produtos.importacoes.repository;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import br.com.produtos.importacoes.model.Loja;
import br.com.produtos.importacoes.model.Usuario;

public interface LojaRepository extends CrudRepository<Loja, Long> {
	
	Loja findOne(Long id);
	
	@Cacheable("lojas")
	List<Loja> findByUsuario(Usuario  usuario);
}
