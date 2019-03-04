package com.desafio.gerenciador.cidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.gerenciador.cidade.dominio.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Query("select estado from Estado estado where estado.codigoUF = :codigoUf")
	public Estado findByCodUf(@Param("codigoUf") String codigoUf);
}
