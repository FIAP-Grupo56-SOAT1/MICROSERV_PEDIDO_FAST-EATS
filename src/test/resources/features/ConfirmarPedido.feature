# language: pt

Funcionalidade: Confirmar Pedido

  Cenario: Confirmar pedido
    Dado que o pedido exista no sistema com status criado e com produtos adicionados
    Quando o pedido for confirmado deve ser alterado para novo status aguardando pagamento
    Entao o pedido confirmado deve estar com status de aguardando pagamento

  Cenario: Nao deve confirmar o pedido sem produto
    Dado que o pedido exista no sistema com status criado e sem produtos adicionados
    Quando eu tentar confirmar o pedido deve ser lancada uma exceção ProdutoNotFound
    Entao o pedido nao deve ser confirmado

