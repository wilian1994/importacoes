package br.com.produtos.importacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.produtos.importacoes.model.Encomenda;
import br.com.produtos.importacoes.model.Status;
import br.com.produtos.importacoes.model.Usuario;
import br.com.produtos.importacoes.repository.EncomendaRepository;

@Service
public class EncomendaService {

	@Autowired
	private EncomendaRepository repository;

	
	public List<Encomenda> findByUsuarios(Usuario usuario) {

		List<Encomenda> encomendas = repository.findByUsuarios(usuario);

		return encomendas;

	}
	
	public List<Encomenda> findByUsuariosAndStatus(Usuario usuario, Status status) {
		
		List<Encomenda> encomendas = repository.findByUsuariosAndStatus(usuario, status);

		return encomendas;

	}

	@CacheEvict(value = "encomendas", allEntries = true)
	public void salvar(Encomenda encomenda) {
		repository.save(encomenda);
	}

	@CacheEvict(value = "encomendas", allEntries = true)
	public void delete(Long id) {
		repository.delete(id);
	}
	
	public Encomenda findOne(long id) {
		return repository.findOne(id);
	}
	

	//
	// public Categoria findOne(Long id) {
	// // TODO Auto-generated method stub
	// return repository.findOne(id);
	// }

}
