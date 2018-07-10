package br.com.produtos.importacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Permissao;
import br.com.produtos.importacoes.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository repository;

	public Iterable<Permissao> obterTodos() {

		Iterable<Permissao> categorias = repository.findAll();

		return categorias;

	}

	public void salvar(Permissao categoria) {
		repository.save(categoria);
	}

	public void delete(Long id) {
		repository.delete(id);
	}


}
