package com.spring.food.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include	
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subTotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@Column(nullable = false,columnDefinition = "datetime")
	@CreationTimestamp
	private LocalDateTime dataCriacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataCancelamento;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataEntrega;
	
	@ManyToOne
	@JoinColumn(name="id_forma_pagamento",nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(name="id_restaurante",nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name="usuario_cliente_id",nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itensPedido;
	
	private StatusPedido status;
	
	
	}
