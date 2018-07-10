package br.com.produtos.importacoes.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(generator = "increment") 
	@GenericGenerator(name = "increment", strategy = "increment") 
	private Long id; 

	private String nome;

	private String login;

	private String senha;

	private boolean ativo;

	private String email;

	
	@ManyToMany(fetch=FetchType.EAGER)
	@JsonBackReference
	private List<Permissao> permissoes;

	@Transient
	private String permissoesStr;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public String getPermissoesStr() {
		getPermissoes().stream().forEach(b->permissoesStr=b.getNome());
		return permissoesStr;
	}

	public void setPermissoesStr(String permissoesStr) {
		this.permissoesStr = permissoesStr;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", ativo=" + ativo + ", email=" + email
				+ ", permissoes=" + permissoes + ", permissoesStr=" + permissoesStr + "]";
	}

	
	
}