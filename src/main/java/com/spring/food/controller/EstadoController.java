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

import com.spring.food.domain.entity.Estado;
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
	public Estado findById(@PathVariable("idEstado") Long idEstado) {
		return serviceEstado.buscaEstadoeExistente(idEstado);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Estado criar(@RequestBody Estado estado) {
		return serviceEstado.save(estado);

	}

	@PutMapping("/{idEstado}")
	public Estado update(@PathVariable("idEstado") Long idEstado, @RequestBody Estado estado) {
		Estado estadoAtual = serviceEstado.buscaEstadoeExistente(idEstado);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return serviceEstado.save(estadoAtual);

	}

	@DeleteMapping("/{idEstado}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("idEstado") Long idEstado) {
		serviceEstado.remove(idEstado);
	}

}
