package com.spring.food.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Table(name = "item_pedido")
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private BigDecimal precoUnitario;
	
	@Column( nullable = false)
	private BigDecimal precoTotal;
	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name="id_pedido",nullable = false)
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="id_produto",nullable = false)
	private Produto produto;
	
}
