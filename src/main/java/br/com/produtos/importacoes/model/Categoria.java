package br.com.produtos.importacoes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "categoria")
public class Categoria {

	@Id 
	@GeneratedValue(generator = "increment") 
	@GenericGenerator(name = "increment", strategy = "increment") 
	private Long id; 

	private String nome;

	@ManyToOne
	@JsonBackReference
	private Usuario usuario;
	
	private int estoqueMinimo;


	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public Categoria(Long id, String nome, Usuario usuario, int estoqueMinimo) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.estoqueMinimo = estoqueMinimo;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}
	
	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
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
