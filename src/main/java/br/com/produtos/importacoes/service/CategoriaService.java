package br.com.produtos.importacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Categoria;
import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Iterable<Categoria> obterTodos(Usuario usuario) {

		Iterable<Categoria> categorias = repository.findByUsuario(usuario);

		return categorias;

	}

	@CacheEvict(value = "categorias", allEntries = true)
	public void salvar(Categoria categoria) {
		repository.save(categoria);
	}

	@CacheEvict(value = "categorias", allEntries = true)
	public void delete(Long id) {
		repository.delete(id);
	}

	public Categoria findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
