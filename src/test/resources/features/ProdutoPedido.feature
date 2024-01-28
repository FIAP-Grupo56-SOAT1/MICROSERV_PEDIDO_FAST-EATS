# language: pt

Funcionalidade: Operações relacionadas a Produtos em Pedidos

  Cenario: Adicionar produto a um pedido
    Dado que um cliente deseja adicionar um produto a um pedido
    Quando o cliente fornece as informações do produto e do pedido
    Entao o produto é adicionado ao pedido com sucesso

  Cenario: Atualizar produto em um pedido
    Dado que um cliente deseja atualizar um produto em um pedido
    Quando o cliente fornece as novas informações do produto e do pedido
    Entao o produto no pedido é atualizado com sucesso

  Cenario: Remover produto de um pedido
    Dado que um cliente deseja remover um produto de um pedido
    Quando o cliente fornece as informações do produto e do pedido
    Entao o produto é removido do pedido com sucesso

  Cenario: Remover produto que não exite no pedido
    Dado que um cliente deseja remover um produto de um pedido
    Quando o cliente fornece as informações do produto e do pedido
    Entao é exibida uma excessão informando produto não encontrado no pedido