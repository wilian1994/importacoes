package br.com.produtos.importacoes.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.produtos.importacoes.model.Encomenda;
import br.com.produtos.importacoes.model.Status;
import br.com.produtos.importacoes.model.Usuario;

@Repository
public interface EncomendaRepository extends CrudRepository<Encomenda, Long> {

	public Iterable<Encomenda> findByStatus(Status status);

	@CacheEvict(value = "encomendas", beforeInvocation = true)
	@Cacheable("encomendas")
	public List<Encomenda> findByUsuariosAndStatus(Usuario usuario, Status status);

	
	public List<Encomenda> findByUsuarios(Usuario usuario);
	// @Query("select distinct(e.produto) from Encomenda e where e.usuario = ?1")
	// List<Produto> findProdutos (Long id);
}
