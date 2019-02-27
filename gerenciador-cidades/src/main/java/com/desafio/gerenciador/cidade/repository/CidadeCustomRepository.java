package com.desafio.gerenciador.cidade.repository;

import java.util.List;
import java.util.Map;

import com.desafio.gerenciador.cidade.dominio.Cidade;
import com.desafio.gerenciador.cidade.dominio.Estado;

public interface CidadeCustomRepository {

	public List<Cidade> findCapitais();
	
	public List<String> findByEstado(String codigoUF);
	
	public Map<Estado,Integer> contQtdCidadesPorEstado();
}
