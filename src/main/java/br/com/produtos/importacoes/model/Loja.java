package br.com.produtos.importacoes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "loja")
public class Loja {

	@Id 
	@GeneratedValue(generator = "increment") 
	@GenericGenerator(name = "increment", strategy = "increment") 
	private Long id; 

	private String nome;
	
	@JsonBackReference
	@ManyToOne
	private Usuario usuario;

	public Loja() {
		// TODO Auto-generated constructor stub
	}
	
	public Loja(Long id, String nome, Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
