package com.desafio.gerenciador.cidade.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long idIbge;
	
	@ManyToOne
	@JoinColumn(name="id_estado")	
	private Estado uf;

	private String nome;
	
	private Boolean capital;
	
	private String longitude;
	
	private String latitude;
	
	private String nomeAscii;
	
	private String nomeAlternativo;
	
	private String microregiao;
	
	private String macroregiao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdIbge() {
		return idIbge;
	}

	public void setIdIbge(Long idIbge) {
		this.idIbge = idIbge;
	}

	public Estado getUf() {
		return uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getCapital() {
		return capital;
	}

	public void setCapital(Boolean capital) {
		this.capital = capital;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getNomeAscii() {
		return nomeAscii;
	}

	public void setNomeAscii(String momeAscii) {
		this.nomeAscii = momeAscii;
	}

	public String getNomeAlternativo() {
		return nomeAlternativo;
	}

	public void setNomeAlternativo(String nomeAlternativo) {
		this.nomeAlternativo = nomeAlternativo;
	}

	public String getMicroregiao() {
		return microregiao;
	}

	public void setMicroregiao(String microregiao) {
		this.microregiao = microregiao;
	}

	public String getMacroregiao() {
		return macroregiao;
	}

	public void setMacroregiao(String macroregiao) {
		this.macroregiao = macroregiao;
	}
}
