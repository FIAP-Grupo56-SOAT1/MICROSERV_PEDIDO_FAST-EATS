# language: pt

Funcionalidade: Andamento Pedido

  Cenario: Consultar um pedido por ID em andamento
    Dado que existe um pedido com ID de pedido 1 em andamento
    Quando eu consultar um pedido por ID 1
    Entao o pedido recuperado pelo ID deve ser retornado

  Cenario: Nao deve consultar um pedido em andamento
    Dado que nao existe um pedido com ID de pedido 1 em andamento
    Quando eu tentar consultar um pedido por ID 1
    Entao deve ser lancada uma excecao PedidoNotFound para a pesquisa por ID de pedido em andamento

  Cenario: Consultar pedidos com em andamento
    Dado que existe pedidos cadastrados com status em andamento
    Quando eu consultar os pedidos em andamento
    Entao uma lista de pedidos em andamento deve ser retornado