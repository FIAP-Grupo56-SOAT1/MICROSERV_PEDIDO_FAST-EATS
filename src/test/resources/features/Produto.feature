# language: pt
Funcionalidade: Produto

  Cenario: Criar um novo produto
    Dado que um novo produto "X-SALADA" deve ser criado
    Quando eu chamar o método de criação do produto
    Entao a produto criado deve estar ativo

  Cenario: Nao deve criar um produto com categoria inexistente
    Dado que um novo produto "X-SALADA" deve ser criado com categoria inexistente
    Quando eu chamar o método de criação do produto deve lancar uma excecao
    Entao deve receber uma excecao CategoriaNotFound na criacao do produto
    
  Cenario: Consultar um produto por ID
    Dado que um produto com ID 1 existe
    Quando eu consultar a produto pelo ID
    Entao devo receber a produto esperado

  Cenario: Tentar consultar um produto inexistente
    Dado que um produto com ID 999 não existe
    Quando eu tentar consultar a produto pelo ID 999
    Entao deve lancar uma exceção ProdutoNotCound para produto não encontrado

  Cenario: Atualizar um produto
    Dado que um produto com ID 1 existe
    Quando eu atualizar a produto
    Entao a produto deve estar ativo

  Cenario: Tentar atualizar um produto com categoria inexistente
    Dado que um produto com ID 1 existe e com categoria inexistente no sistema
    Quando eu tentar atualizar a produto com categoria inexistente
    Entao devo receber um excecao de CategoriaNotFound

  Cenario: Deletar um produto
    Dado que um produto com ID 1 existe
    Quando eu deletar a produto
    Entao a produto deve ser removida

  Cenario: Listar todos as produtos
    Dado que existem produtos cadastrados
    Quando eu listar todas as produtos
    Entao devo receber a lista de produtos

  Cenario: Tentar listar produtos quando não existem
    Dado que não existem produtos cadastradas
    Quando eu tentar listar todas as produtos
    Entao deve lançar uma exceção de ProdutoNotFound

  Cenario: Consultar um produto pelo nome
    Dado que um produto com nome "X-SALADA" existe
    Quando eu consultar a produto pelo nome "X-SALADA"
    Entao devo receber a produto "X-SALADA" como resultado

  Cenario: Tentar consultar um produto pelo nome inexistente
    Dado que um produto com nome "AGUA" não existe
    Quando eu consultar a produto pelo nome "AGUA" deve lancar uma excecao
    Entao deve lançar uma exceção de ProdutoNotFound para nome "AGUA" consultado

  Cenario: Tentar consultar um produto pelo ID da categoria
    Dado que um produto com ID 1 de categoria existe
    Quando eu consultar a produto pelo ID 1 da categoria
    Entao devo receber uma lista a produto como resultado

  Cenario: Tentar consultar um produto pelo ID da categoria enexistente
    Dado que um produto com ID 1 de categoria nao existe
    Quando eu consultar o produto pelo ID 1 da categoria inexistente
    Entao deve lançar uma exceção de ProdutoNotFound para consulta com categoria inexistente