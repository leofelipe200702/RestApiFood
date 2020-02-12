create table item_pedido (
  id bigint not null auto_increment, 
  observacao varchar(80), 
  preco_total decimal(19,2) not null, 
  preco_unitario decimal(19,2) not null, 
  quantidade integer not null, 
  id_pedido bigint not null, 
  id_produto bigint not null, 
  primary key (id)
  ) engine=InnoDB default charset=utf8;
  
create table pedido (
	id bigint not null auto_increment, 
    data_cancelamento datetime, 
    data_confirmacao datetime, 
    data_criacao datetime not null, 
    data_entrega datetime, 
    endereco_bairro varchar(30) not null, 
    endereco_cep varchar(20) not null, 
    endereco_complemento varchar(20), 
    endereco_logradouro varchar(100) not null, 
    endereco_numero varchar(20) not null, 
    status varchar(10) not null, 
    sub_total decimal(19,2) not null, 
    taxa_frete decimal(19,2) not null, 
    valor_total decimal(19,2) not null, 
    usuario_cliente_id bigint not null, 
    endereco_cidade_id bigint not null, 
    id_forma_pagamento bigint not null, 
    id_restaurante bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

alter table item_pedido add constraint fk_item_pedido foreign key (id_pedido) references pedido (id);
alter table item_pedido add constraint fk_item_produto foreign key (id_produto) references produto (id);
alter table pedido add constraint fk_item_cliente foreign key (usuario_cliente_id) references usuario (id);
alter table pedido add constraint fk_item_cidade foreign key (endereco_cidade_id) references cidade (id);
alter table pedido add constraint fk_item_forma_pagamento foreign key (id_forma_pagamento) references forma_pagamento (id);
alter table pedido add constraint fk_item_restaurante foreign key (id_restaurante) references restaurante (id);
