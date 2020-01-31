package com.spring.food.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.food.domain.entity.Restaurante;
import com.spring.food.domain.exception.ExcecaoEntidadeEmUsoException;
import com.spring.food.domain.exception.ExcecaoEntidadeNaoEncontradaException;
import com.spring.food.domain.service.RestauranteService;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

	@Autowired
	private RestauranteService service;

	@GetMapping
	public List<Restaurante> list() {
		return service.getList();
	}

	@GetMapping("/{idRestaurante}")
	public ResponseEntity<Restaurante> findById(@PathVariable("idRestaurante") Long idRestaurante) {
		Optional<Restaurante> restaurante = service.findById(idRestaurante);

		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Restaurante restaurante) {

		try {
			restaurante = service.save(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{idRestaurante}")
	public ResponseEntity<?> update(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Restaurante pRestaurante) {

		try {
			Optional<Restaurante> restaurante = service.findById(idRestaurante);

			if (restaurante.isPresent()) {
				// copia as propriedades de um Bean para o outro
				BeanUtils.copyProperties(pRestaurante, restaurante.get(), "id","formasPagamento","endereco","dataCadastro");

				Restaurante restauranteSalvo = service.save(restaurante.get());

				return ResponseEntity.ok(restauranteSalvo);

			}

			return ResponseEntity.notFound().build();
		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{idRestaurante}")
	public ResponseEntity<Restaurante> remove(@PathVariable("idRestaurante") Long idRestaurante) {

		try {

			service.remove(idRestaurante);

			return ResponseEntity.noContent().build();

		} catch (ExcecaoEntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (ExcecaoEntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PatchMapping("/{idRestaurante}")
	public ResponseEntity<?> updateParcial(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Map<String, Object> campos) {

		Optional<Restaurante> restauranteAtual = service.findById(idRestaurante);

		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		// atualiza campos via reflection
		merge(campos, restauranteAtual.get());

		return update(idRestaurante, restauranteAtual.get());

	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {

		ObjectMapper mapper = new ObjectMapper();
		Restaurante restauranteOrigem = mapper.convertValue(camposOrigem, Restaurante.class);

		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object valor = ReflectionUtils.getField(field, restauranteOrigem);

			ReflectionUtils.setField(field, restauranteDestino, valor);

		});

	}

}
