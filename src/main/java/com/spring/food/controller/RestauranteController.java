package com.spring.food.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.food.domain.entity.Restaurante;
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
	public Restaurante findById(@PathVariable("idRestaurante") Long idRestaurante) {
		return service.buscaRestauranteExistente(idRestaurante);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante save(@RequestBody Restaurante restaurante) {
		return service.save(restaurante);
	}

	@PutMapping("/{idRestaurante}")
	public Restaurante update(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Restaurante pRestaurante) {

		Restaurante restauranteAtual = service.buscaRestauranteExistente(idRestaurante);
		// copia as propriedades de um Bean para o outro
		BeanUtils.copyProperties(pRestaurante, restauranteAtual, "id","formasPagamento","endereco","dataCadastro");
		return service.save(restauranteAtual);
	}

	@DeleteMapping("/{idRestaurante}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("idRestaurante") Long idRestaurante) {
		service.remove(idRestaurante);
	}

	@PatchMapping("/{idRestaurante}")
	public Restaurante updateParcial(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Map<String, Object> campos) {

		Restaurante restauranteAtual = service.buscaRestauranteExistente(idRestaurante);

		// atualiza campos via reflection
		merge(campos, restauranteAtual);

		return update(idRestaurante, restauranteAtual);

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
