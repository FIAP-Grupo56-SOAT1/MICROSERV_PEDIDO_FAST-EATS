# language: pt
Funcionalidade: Status Pedido

  Cenario: Criar um novo status pedido
    Dado que um nov status pedido "APROVADO" deve ser criada
    Quando eu chamar o método de criação de status pedido
    Entao um status pedido criado deve estar ativa e com o nome em maiúsculas

  Cenario: Consultar status pedido
    Dado que um status pedido com ID 1 existe no sistema
    Quando eu consultar um status pedido pelo ID
    Entao devo receber um status pedido

  Cenario: Nao deve consultar status pedido inexistente
    Dado que um status pedido com ID 1 nao existe no sistema
    Quando eu consultar um status pedido pelo ID que nao existe
    Entao deve lancar uma exceção de StatusPedidoNotFound

  Cenario: Atualizar um status pedido
    Dado que um status pedido com ID 1 existe no sistema
    Quando eu atualizar o status pedido
    Entao o status pedido deve estar ativa e com o nome em maiúsculas

  Cenario: Deletar um status pedido
    Dado que um status pedido com ID 1 existe no sistema
    Quando eu deletar o status pedido
    Entao o status pedido deve ser removido

  Cenario: Listar todos os status pedido
    Dado que existem status pedido cadastrados
    Quando eu listar todos os status pedido
    Entao devo receber a lista de status pedido

  Cenario: Nao deve listar os status pedido nao cadastrado
    Dado que nao existem status pedido cadastrados
    Quando eu listar todos os status pedido do sistema
    Entao devo receber uma exception StatusPedidoNotFound

  Cenario: Consultar um status pedido pelo nome
    Dado que um status pedido com nome "APROVADO" existe
    Quando eu consultar o status pedido pelo nome "APROVADO"
    Entao devo receber o status pedido "APROVADO" como resultado

  Cenario: Nao deve consultar um status pedido pelo nome que nao existe
    Dado que um status pedido com nome "RECEBIDO" não existe
    Quando eu consultar o status pedido pelo nome "RECEBIDO" deve lancar uma excecao
    Entao deve lançar uma exceção de StatusPedidoNotFound para nome "RECEBIDO" consultado