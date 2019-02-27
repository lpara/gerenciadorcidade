package com.desafio.gerenciador.cidade.dominio;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String codigoUF;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoUF() {
		return codigoUF;
	}

	public void setCodigoUF(String codigoUF) {
		this.codigoUF = codigoUF;
	}
}
