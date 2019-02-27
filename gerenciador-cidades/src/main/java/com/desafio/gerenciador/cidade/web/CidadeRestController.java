package com.desafio.gerenciador.cidade.web;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.gerenciador.cidade.dominio.Cidade;
import com.desafio.gerenciador.cidade.dominio.Estado;
import com.desafio.gerenciador.cidade.repository.CidadeRepository;
import com.desafio.gerenciador.cidade.repository.EstadoRepository;

@RestController
@RequestMapping("/cidade")
public class CidadeRestController {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@PostMapping("/add")
	public Cidade inserirCidade(@RequestBody Cidade cidade) {
		Estado estadoDTO = estadoRepository.findByCodUf(cidade.getUf().getCodigoUF());
		if(estadoDTO == null || estadoDTO.getId() <= 0) {
			Estado estado = estadoRepository.save(estadoDTO);
			cidade.setUf(estado);
		}
		return cidadeRepo.save(cidade);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean removerCidade(@PathVariable("id") Long cidadeId) throws NoSuchElementException, Exception {
		Optional<Cidade> cidadeOpt = cidadeRepo.findById(cidadeId);
		if(cidadeOpt.isPresent()) {
			Cidade cidade = cidadeOpt.get();
			cidadeRepo.delete(cidade);
			return true;
		}
		
		return false;
	}
	
	@GetMapping
	public List<Cidade> buscarCidades(){
		return cidadeRepo.findAll();
	}
	
	@GetMapping("/capitais")
	public List<Cidade> buscarCapitais(){
		return cidadeRepo.findCapitais();
	}
	
	@GetMapping("/qtdcidade/estado/{id}")
	public Integer buscarQtdCidadesPorEstado(@PathVariable("id") Long idEstado) {
		Map<Estado,Integer> mapaQtdCidadeEstado = cidadeRepo.contQtdCidadesPorEstado();
		Optional<Estado> estadoSelecionadoOpt = estadoRepository.findById(idEstado);
		return mapaQtdCidadeEstado.get(estadoSelecionadoOpt.get());
	}
	
	

}
