package br.com.produtos.importacoes.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import br.com.produtos.importacoes.model.Categoria;
import br.com.produtos.importacoes.model.Usuario;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

	List<Categoria> findByNome(String nome);

	@Cacheable("categorias")
	List<Categoria> findByUsuario(Usuario usuario);

	Categoria findOne(Long id);

}
