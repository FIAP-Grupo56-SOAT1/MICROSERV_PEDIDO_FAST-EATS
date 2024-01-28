# language: pt
Funcionalidade: Cliente

  Cenario: Criar um novo cliente
    Dado que um novo cliente "FIAP" com CPF valido deve ser criado
    Quando eu chamar o método de criação de cliente
    Entao o novo cliente deve criado

  Cenario: Nao deve criar um cliente existente
    Dado que um novo cliente "FIAP" com CPF valido ja cadastrado no sistema
    Quando eu chamar o método de criação de cliente que ja existe
    Entao devo receber uma exception RegraNegocioException para o cliente existente

  Cenario: Consultar um cliente existente
    Dado que um cliente com CPF valido existe no sistema
    Quando eu consultar o cliente pelo CPF
    Entao devo receber o cliente esperado

  Cenario: Nao deve consultar um cliente com CPF invalido
    Dado que eu tenho CPF invalido para consulta do cliente
    Quando eu consultar o cliente pelo CPF com CPF invalido
    Entao devo receber uma exception RegraNegocioException com CPF invalido

  Cenario: Nao deve consultar um cliente inexistente no sistema
    Dado que um cliente com CPF valido que nao existe no sistema
    Quando eu consultar o cliente pelo CPF inexistente no sistema
    Entao devo receber uma exception ClienteNotFound para o cliente inexistente

  Cenario: Listar todos os clientes
    Dado que existem clientes cadastrados
    Quando eu listar todos as clientes
    Entao devo receber a lista de clientes

  Cenario: Atualizar um cliente
    Dado que um cliente existe no sistema
    Quando eu atualizar o cliente
    Entao a cliente deve ser atualizado no sistema

  Cenario: Deletar um cliente
    Dado que um cliente com existe no sistema
    Quando eu deletar a cliente
    Entao a cliente deve ser removido

  Cenario: verificar se um cliente existe
    Dado que um cliente com CPF valido existe no sistema
    Quando eu consultar se existe com CPF valido no sistema
    Entao devo receber um resposta que existe

  Cenario: verificar que um cliente não existe
    Dado que um cliente com CPF valido nao existe no sistema
    Quando eu consultar se existe com CPF no sistema
    Entao devo receber um resposta que nao existe

  Cenario: validar um cliente com CPF vazio
    Dado que um CPF vazio de um cliente
    Quando eu consultar se existe com CPF vazio no sistema
    Entao devo receber uma exception IllegalArgumentException para a consulta

  Cenario: validar um cliente com CPF inválido
    Dado que um CPF invalido de um cliente
    Quando eu consultar se existe com CPF invalido no sistema
    Entao devo receber uma exception RegraNegocioException para a consulta

  Cenario: validar um cliente com email vazio
    Dado que um cliente com cpf valido
    Quando eu consultar se e valido com email vazio
    Entao devo receber uma exception RegraNegocioException para a consulta


