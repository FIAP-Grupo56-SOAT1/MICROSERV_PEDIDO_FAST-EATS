# language: pt

Funcionalidade: Operações relacionadas a Pedidos

  Cenario: Criar um novo pedido para um cliente não cadastrado e identificado
    Dado que um cliente não cadastrado mas identificado deseja fazer um pedido
    Quando o cliente fornece as informações do pedido e da sua identificação
    Entao o para pedido de um cliente antes não cadastrado, mas identificado é criado com sucesso

  Cenario: Criar um novo pedido para um cliente cadastrado e identificado
    Dado que um cliente cadastrado mas identificado deseja fazer um pedido
    Quando o cliente fornece as informações do pedido e da sua identificação
    Entao o para pedido de um cliente antes cadastrado, mas identificado é criado com sucesso

  Cenario: Criar um novo pedido não identificado
    Dado que um pedido não foi identicado
    Quando as informações do pedido são fornecidas
    Entao o pedido não identificado é criado com sucesso

  Cenario: Consultar um pedido existente
    Dado que um cliente deseja consultar um pedido existente
    Quando o cliente fornece o ID do pedido
    Entao as informações do pedido são exibidas corretamente

  Cenario: Consultar um pedido não existente
    Dado que um cliente deseja consultar um pedido não existente
    Quando o cliente fornece o ID do pedido que não existe
    Entao é exibida uma excessão informando que o pedido não existe

  Cenario: Listar todos os pedidos
    Dado que um cliente deseja ver a lista de todos os pedidos
    Quando o cliente solicita a listagem de pedidos
    Entao a lista de pedidos é exibida corretamente

  Cenario: Atualizar um pedido
    Dado que um cliente deseja atualizar um pedido existente
    Quando o cliente fornece as novas informações do pedido
    Entao o pedido é atualizado com sucesso

  Cenario: Deletar um pedido
    Dado que um cliente deseja deletar um pedido existente
    Quando o cliente fornece o ID do pedido a ser deletado
    Entao o pedido é removido com sucesso

  Cenario: Atualizar valor do pedido
    Dado que um cliente deseja atualizar o valor de um pedido
    Quando o cliente fornece as informações atualizadas do pedido
    Entao o valor do pedido é recalculado corretamente
