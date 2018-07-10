package br.com.produtos.importacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Loja;
import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.LojaRepository;

@Service
public class LojaService {

	@Autowired
	private LojaRepository repository;

	public Iterable<Loja> obterTodos(Usuario usuario) {

		Iterable<Loja> lojas = repository.findByUsuario(usuario);

		return lojas;

	}

	@CacheEvict(value = "lojas", allEntries = true)
	public void salvar(Loja loja) {
		repository.save(loja);
	}

	@CacheEvict(value = "lojas", allEntries = true)
	public void delete(Long id) {
		repository.delete(id);
	}

	public Loja findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
