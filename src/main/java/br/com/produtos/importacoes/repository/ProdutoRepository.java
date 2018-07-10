package br.com.produtos.importacoes.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import br.com.produtos.importacoes.model.Produto;
import br.com.produtos.importacoes.model.Usuario;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
	
	
	List<Produto> findByNome(String nome);

	@Cacheable("produtos")
	List<Produto> findByUsuarios(Usuario usuario);
	
	

	Produto findOne(Long id);
	
	
}
