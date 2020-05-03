package com.spring.food.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.food.domain.entity.Restaurante;
import com.spring.food.domain.exception.EntidadeNaoEncontradaException;
import com.spring.food.domain.exception.NegocioException;
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
		
		if(true) {
			throw new IllegalArgumentException();
		}
		
		return service.buscaRestauranteExistente(idRestaurante);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante save(@RequestBody Restaurante restaurante) {
		
		try {
			return service.save(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}
		
	}

	@PutMapping("/{idRestaurante}")
	public Restaurante update(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Restaurante pRestaurante) {

		Restaurante restauranteAtual = service.buscaRestauranteExistente(idRestaurante);
		// copia as propriedades de um Bean para o outro
		BeanUtils.copyProperties(pRestaurante, restauranteAtual, "id","formasPagamento","endereco","dataCadastro");
		
		try {
			return service.save(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}		
	}

	@DeleteMapping("/{idRestaurante}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("idRestaurante") Long idRestaurante) {
		service.remove(idRestaurante);
	}

	@PatchMapping("/{idRestaurante}")
	public Restaurante updateParcial(@PathVariable("idRestaurante") Long idRestaurante,
			@RequestBody Map<String, Object> campos,HttpServletRequest request) {
		
		Restaurante restauranteAtual = service.buscaRestauranteExistente(idRestaurante);

		// atualiza campos via reflection
		merge(campos, restauranteAtual,request);

		return update(idRestaurante, restauranteAtual);

	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino,HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
		Restaurante restauranteOrigem = mapper.convertValue(camposOrigem, Restaurante.class);

		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);

			Object valor = ReflectionUtils.getField(field, restauranteOrigem);

			ReflectionUtils.setField(field, restauranteDestino, valor);

		});
		
		}catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}

	}

}
