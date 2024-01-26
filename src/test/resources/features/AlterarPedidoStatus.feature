# language: pt
Funcionalidade: Alterar Pedido Status

  Cenario: Alterar status do pedido para aguardando pagamento
    Dado que o pedido exista no sistema com status criado
    Quando o status do pedido for alterado para aguardando pagamento
    Entao o status do pedido deve ser alterado para aguardando pagamento
