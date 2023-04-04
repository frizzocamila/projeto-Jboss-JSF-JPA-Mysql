package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Filial {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nome;
	private String cnpj;

	@OneToOne (cascade = CascadeType.ALL)
	private Endereco endereco = new Endereco();
	
	@Transient
	private Long numFuncionarios;
	
	public Long getNumFuncionarios() {
		return numFuncionarios;
	}

	public void setNumFuncionarios(Long numFuncionarios) {
		this.numFuncionarios = numFuncionarios;
	}

	public Filial() {
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
