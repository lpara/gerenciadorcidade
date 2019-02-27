package com.desafio.gerenciador.cidade.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.desafio.gerenciador.cidade.dominio.Cidade;
import com.desafio.gerenciador.cidade.dominio.Estado;

@Repository
public class CidadeCustomRepositoryImpl implements CidadeCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> findCapitais() {
		String sql = "SELECT * FROM cidade c WHERE c.ativa = true AND c.capital = true ORDER BY c.nome";
		
		Query query = entityManager.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findByEstado(String codigoUF) {
		String sql = "SELECT * FROM cidade c "
				+ "JOIN estado estado ON estado.id=c.uf "
				+ "WHERE c.ativa = true AND estado.codigoUF = :codigoUF";
		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("codigoUF", codigoUF);
		
		return query.getResultList();
	}
	
	@Override
	public Map<Estado, Integer> contQtdCidadesPorEstado() {
		String sql = "SELECT estado.id, estado.codigoUF, count(c.id) FROM Cidade c "
				+ "JOIN estado estado ON estado.id=c.uf "
				+ "WHERE c.ativa = true "
				+ "GROUP BY estado.id, estado.codigoUF" ;
		
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> result = query.getResultList();
		Map<Estado, Integer> resultMap = new HashMap<>();
		for(Object[] o : result) {
			if(resultMap.containsKey(o[0])) {
				continue;
			}
			Estado estado = new Estado();
			estado.setId((Long) o[0]);
			estado.setCodigoUF((String) o[1]);
			resultMap.put(estado, (Integer)o[2]);
		}
		
		return resultMap;
	}

}
