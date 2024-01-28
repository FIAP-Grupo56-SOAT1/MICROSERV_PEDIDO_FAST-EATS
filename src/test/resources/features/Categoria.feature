# language: pt
Funcionalidade: Categoria

  Cenario: Criar uma nova categoria
    Dado que uma nova categoria "LANCHE" deve ser criada
    Quando eu chamar o método de criação da categoria
    Entao a categoria criada deve estar ativa e com o nome em maiúsculas

  Cenario: Consultar uma categoria
    Dado que uma categoria com ID 1 existe
    Quando eu consultar a categoria pelo ID
    Entao devo receber a categoria esperada

  Cenario: Tentar consultar uma categoria inexistente
    Dado que uma categoria com ID 999 não existe
    Quando eu tentar consultar a categoria pelo ID 999
    Entao deve lançar uma exceção de categoria não encontrada

  Cenario: Atualizar uma categoria
    Dado que uma categoria com ID 1 existe
    Quando eu atualizar a categoria
    Entao a categoria deve estar ativa e com o nome em maiúsculas

  Cenario: Tentar atualizar uma categoria com ativo nulo
    Dado que uma categoria com ID 1 existe e com campo ativo nulo
    Quando eu tentar atualizar a categoria o campo ativo deve ser preenchido
    Entao a categoria antes com ativo nulo deve estar ativa e com o nome em maiúsculo

  Cenario: Deletar uma categoria
    Dado que uma categoria com ID 1 existe
    Quando eu deletar a categoria
    Entao a categoria deve ser removida

  Cenario: Listar todas as categorias
    Dado que existem categorias cadastradas
    Quando eu listar todas as categorias
    Entao devo receber a lista de categorias

  Cenario: Tentar listar categorias quando não existem
    Dado que não existem categorias cadastradas
    Quando eu tentar listar todas as categorias
    Entao deve lançar uma exceção de CategoriaNotFound

  Cenario: Consultar uma categoria pelo nome
    Dado que uma categoria com nome "LANCHE" existe
    Quando eu consultar a categoria pelo nome "LANCHE"
    Entao devo receber a categoria "LANCHE" como resultado

  Cenario: Tentar consultar uma categoria pelo nome inexistente
    Dado que uma categoria com nome "BEBIDA" não existe
    Quando eu consultar a categoria pelo nome "BEBIDA" deve lancar uma excecao
    Entao deve lançar uma exceção de CategoriaNotFound para nome "BEBIDA" consultado