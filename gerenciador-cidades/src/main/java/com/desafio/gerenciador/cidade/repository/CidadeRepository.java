package com.desafio.gerenciador.cidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.gerenciador.cidade.dominio.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeCustomRepository  {

	@Query("select * from Cidade c where c.idIbge = :codIbge")
	public Cidade findByIbge(@Param("codIbge") Long codIbge);
	
}
