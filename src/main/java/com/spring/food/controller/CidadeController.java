package com.spring.food.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.food.domain.entity.Cidade;
import com.spring.food.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService service;

	@GetMapping
	public List<Cidade> listar() {
		return service.getList();
	}

	@GetMapping("/{idCidade}")
	public Cidade findById(@PathVariable("idCidade") Long idCidade) {
		return service.buscaCidadeExistente(idCidade);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cidade criar(@RequestBody Cidade cidade) {
		return service.save(cidade);
	}

	@PutMapping("/{idCidade}")
	public Cidade update(@PathVariable("idCidade") Long idCidade, @RequestBody Cidade pCidade) {
		Cidade cidadeAtual = service.buscaCidadeExistente(idCidade);
		// copia as propriedades de um Bean para o outro
		BeanUtils.copyProperties(pCidade, cidadeAtual, "id");
		return service.save(cidadeAtual);
	}
	
	@DeleteMapping("/{idCidade}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("idCidade") Long idCidade) {
		service.remove(idCidade);
	}

}
