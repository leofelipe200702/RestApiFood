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

import com.spring.food.domain.entity.Estado;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService serviceEstado;

	@GetMapping
	public List<Estado> listar() {
		return serviceEstado.listar();
	}

	@GetMapping("/{idEstado}")
	public ResponseEntity<Estado> findById(@PathVariable("idEstado") Long idEstado) {

		Optional<Estado> estado = serviceEstado.findById(idEstado);

		if (estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Estado criar(@RequestBody Estado estado) {
		return serviceEstado.save(estado);

	}

	@PutMapping("/{idEstado}")
	public ResponseEntity<?> update(@PathVariable("idEstado") Long idEstado, @RequestBody Estado estado) {

		Optional<Estado> estadoAtual = serviceEstado.findById(idEstado);

		if (estadoAtual.isPresent()) {
			
			BeanUtils.copyProperties(estado, estadoAtual.get(), "id");

			Estado estadoSalvo = serviceEstado.save(estadoAtual.get());

			return ResponseEntity.ok(estadoSalvo);
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{idEstado}")
	public ResponseEntity<Estado> remove(@PathVariable("idEstado") Long idEstado) {
		try {

			serviceEstado.remove(idEstado);
			return ResponseEntity.noContent().build();

		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (ExcecaoEntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
