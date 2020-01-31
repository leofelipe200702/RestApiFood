package com.spring.food.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.food.domain.entity.Cidade;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
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
	public ResponseEntity<Cidade> findById(@PathVariable("idCidade") Long idCidade) {
		Optional<Cidade> cidade = service.findById(idCidade);

		if (cidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(cidade.get());
	}

	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Cidade cidade) {
		try {
			cidade = service.save(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{idCidade}")
	public ResponseEntity<?> update(@PathVariable("idCidade") Long idCidade, @RequestBody Cidade pCidade) {

		try {
			Optional<Cidade> cidade = service.findById(idCidade);

			if (cidade.isPresent()) {
				// copia as propriedades de um Bean para o outro
				BeanUtils.copyProperties(pCidade, cidade.get(), "id");
				Cidade cidadeSalva = service.save(cidade.get());
				return ResponseEntity.ok(cidadeSalva);
			}
			return ResponseEntity.notFound().build();
		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{idCidade}")
	public ResponseEntity<Cidade> remove(@PathVariable("idCidade") Long idCidade) {
		
		try {

			service.remove(idCidade);
			
			return ResponseEntity.noContent().build();
			
		}catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

		
	}

}
