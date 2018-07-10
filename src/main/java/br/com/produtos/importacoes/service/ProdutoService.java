package br.com.produtos.importacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Produto;
import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public Iterable<Produto> obterTodos(Usuario usuario) {

		Iterable<Produto> produtos = repository.findByUsuarios(usuario);

		return produtos;

	}

	@CacheEvict(value = "produtos", allEntries = true)
	public void salvar(Produto produtos) {
		repository.save(produtos);
	}

	@CacheEvict(value = "produtos", allEntries = true)
	public Produto findOne(Long id) {
		return repository.findOne(id);
	}
	
	public void delete(Long id){
		repository.delete(id);
	}
	
}
