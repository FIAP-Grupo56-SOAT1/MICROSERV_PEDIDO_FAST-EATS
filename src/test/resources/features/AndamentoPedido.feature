# language: pt

Funcionalidade: Andamento Pedido

  Cenario: Consultar um pedido por ID com status em andamento
    Dado que existe um pedido com ID de pedido 999 com status em andamento
    Quando eu tentar consultar um pedido por ID de pedido 999
    Entao o pedido recuperado pelo ID de pedido deve ser retornado
