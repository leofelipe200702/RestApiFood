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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.food.domain.entity.Cozinha;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService service;

	@GetMapping
	public List<Cozinha> list() {
		return service.list();
	}

	@GetMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> find(@PathVariable("idCozinha") Long idCozinha) {

		Optional<Cozinha> cozinha = service.findById(idCozinha);

		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cozinha save(@RequestBody Cozinha cozinha) {
		return service.save(cozinha);
	}

	@PutMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> update(@PathVariable Long idCozinha, @RequestBody Cozinha pCozinha) {

		Optional<Cozinha> cozinha = service.findById(idCozinha);

		if (cozinha.isPresent()) {
			// copia as propriedades de um Bean para o outro
			BeanUtils.copyProperties(pCozinha, cozinha.get(), "id");

			Cozinha cozinhaSalva = service.save(cozinha.get());

			return ResponseEntity.ok(cozinhaSalva);

		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{idCozinha}")
	public ResponseEntity<Cozinha> delete(@PathVariable Long idCozinha) {

		try {

			service.remove(idCozinha);

			return ResponseEntity.noContent().build();

		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (ExcecaoEntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

}
