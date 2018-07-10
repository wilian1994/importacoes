package br.com.produtos.importacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity(name = "encomenda")
public class Encomenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(generator = "increment") 
	@GenericGenerator(name = "increment", strategy = "increment") 
	private Long id; 

	private String rastreio;

	@ManyToOne
	private Categoria categoria;

	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Loja loja;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataCompra;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataRecebida;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataVenda;

	private String descricao;

	@NumberFormat(pattern = "R$ #,###,###,###.##")
	private BigDecimal valorPago;

	@NumberFormat(pattern = "R$ #,###,###,###.##")
	private BigDecimal valorVenda;

	private Status status;

	private String pedido;

	@Transient
	private int qtd;

	@Transient
	@NumberFormat(pattern = "R$ #,###,###,###.##")
	private BigDecimal lucro;

	@ManyToMany
	private List<Usuario> usuarios;

	public Encomenda(BigDecimal valorVenda, Loja loja) {
		this.valorVenda = valorVenda;
		this.loja = loja;
	}

	public Encomenda() {
	}

	public Encomenda(String rastreio, Categoria categoria, Produto produto, Loja loja, Calendar dataCompra, Calendar dataRecebida,
			Calendar dataVenda, String descricao, BigDecimal valorPago, BigDecimal valorVenda, Status status, String pedido, int qtd,
			List<Usuario> usuarios) {
		super();
		this.rastreio = rastreio;
		this.categoria = categoria;
		this.produto = produto;
		this.loja = loja;
		this.dataCompra = dataCompra;
		this.dataRecebida = dataRecebida;
		this.dataVenda = dataVenda;
		this.descricao = descricao;
		this.valorPago = valorPago;
		this.valorVenda = valorVenda;
		this.status = status;
		this.pedido = pedido;
		this.qtd = qtd;
		this.usuarios = usuarios;
	}

	public Status getStatus() {
		return status;
	}

	public int getLimiteDataCompra() {
		
		int MILLIS_IN_DAY = 86400000;
		Calendar data = Calendar.getInstance();
		return (int) ((data.getTimeInMillis() - dataCompra.getTimeInMillis()) / MILLIS_IN_DAY);
	}

	public int getLimiteDataRecebida() {
		int MILLIS_IN_DAY = 86400000;
		Calendar data = Calendar.getInstance();
		return (int) ((data.getTimeInMillis() - dataRecebida.getTimeInMillis()) / MILLIS_IN_DAY);
	}
	
	public int getLimiteDataVenda() {
		int MILLIS_IN_DAY = 86400000;
		return (int) ((dataVenda.getTimeInMillis() - dataCompra.getTimeInMillis()) / MILLIS_IN_DAY);
	}

	public BigDecimal sugestaoDePreco() {
		BigDecimal sugestao = BigDecimal.ZERO;
		if (valorPago.compareTo(new BigDecimal("300")) < 1) {

			if (getLimiteDataRecebida() < 10) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("1.5")));

			} else if (getLimiteDataRecebida() < 20) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("1.0")));

			} else if (getLimiteDataRecebida() < 30) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("0.75")));

			} else if (getLimiteDataRecebida() < 40) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("0.5")));
			} else {
				sugestao = valorPago;
			}
		} else {
			if (getLimiteDataRecebida() < 10) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("0.75")));

			}
			if (getLimiteDataRecebida() < 20) {
				sugestao = valorPago.add(valorPago.multiply(new BigDecimal("0.5")));
			}
		}
		
		return sugestao;

	}



	public BigDecimal getLucro() {
		this.lucro = valorVenda.subtract(valorPago);
		return this.lucro;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRastreio() {
		return rastreio;
	}

	public void setRastreio(String rastreio) {
		this.rastreio = rastreio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Calendar getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Calendar dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Calendar getDataRecebida() {
		return dataRecebida;
	}

	public void setDataRecebida(Calendar dataRecebida) {
		this.dataRecebida = dataRecebida;
	}

	public Calendar getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Calendar dataVenda) {
		this.dataVenda = dataVenda;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public int getQtd() {
		qtd = qtd > 0 ? qtd : 1;
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "Encomenda [id=" + id + ", rastreio=" + rastreio + ", categoria=" + categoria + ", produto=" + produto + ", loja=" + loja
				+ ", dataCompra=" + dataCompra + ", dataRecebida=" + dataRecebida + ", dataVenda=" + dataVenda + ", descricao=" + descricao
				+ ", valorPago=" + valorPago + ", valorVenda=" + valorVenda + ", status=" + status + ", pedido=" + pedido + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((dataCompra == null) ? 0 : dataCompra.hashCode());
		result = prime * result + ((dataRecebida == null) ? 0 : dataRecebida.hashCode());
		result = prime * result + ((dataVenda == null) ? 0 : dataVenda.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loja == null) ? 0 : loja.hashCode());
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + qtd;
		result = prime * result + ((rastreio == null) ? 0 : rastreio.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorPago == null) ? 0 : valorPago.hashCode());
		result = prime * result + ((valorVenda == null) ? 0 : valorVenda.hashCode());
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
		Encomenda other = (Encomenda) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (dataCompra == null) {
			if (other.dataCompra != null)
				return false;
		} else if (!dataCompra.equals(other.dataCompra))
			return false;
		if (dataRecebida == null) {
			if (other.dataRecebida != null)
				return false;
		} else if (!dataRecebida.equals(other.dataRecebida))
			return false;
		if (dataVenda == null) {
			if (other.dataVenda != null)
				return false;
		} else if (!dataVenda.equals(other.dataVenda))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loja == null) {
			if (other.loja != null)
				return false;
		} else if (!loja.equals(other.loja))
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (qtd != other.qtd)
			return false;
		if (rastreio == null) {
			if (other.rastreio != null)
				return false;
		} else if (!rastreio.equals(other.rastreio))
			return false;
		if (status != other.status)
			return false;
		if (valorPago == null) {
			if (other.valorPago != null)
				return false;
		} else if (!valorPago.equals(other.valorPago))
			return false;
		if (valorVenda == null) {
			if (other.valorVenda != null)
				return false;
		} else if (!valorVenda.equals(other.valorVenda))
			return false;
		return true;
	}

}
