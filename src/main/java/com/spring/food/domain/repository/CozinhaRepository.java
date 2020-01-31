package com.spring.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.food.domain.entity.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	// criando o método com o nome da propriedade, irá ser filtrado por essa
	// propriedade ou pode utilizar findByNome
	// o código abaixo irá filtrar cozinhas pelo nome
	List<Cozinha> nome(String name);
	//a palavra reservada containing irá fazer o papel do like 
	List<Cozinha> findByNomeContaining(String name);
	
	boolean existsByNome(String nome);

}
