# language: pt

Funcionalidade: Alterar Pedido Status

  Cenario: Alterar status do pedido para aguardando pagamento
    Dado que o pedido exista no sistema com status criado
    Quando o status do pedido for alterado para aguardando pagamento
    Entao o status do pedido deve ser alterado para aguardando pagamento

  Cenario: Alterar status do pedido para pago
    Dado que o pedido exista no sistema com status aguardando pagamento
    Quando o status do pedido for alterado para pago
    Entao o status do pedido deve ser alterado para pago

  Cenario: Alterar status do pedido para recebido
    Dado que o pedido exista no sistema com status pago
    Quando o status do pedido for alterado para recebido
    Entao o status do pedido deve ser alterado para recebido

  Cenario: Alterar status do pedido para em preparo
    Dado que o pedido exista no sistema com status recebido
    Quando o status do pedido for alterado para em preparo
    Entao o status do pedido deve ser alterado para em preparo

  Cenario: Alterar status do pedido para pronto
    Dado que o pedido exista no sistema com status em preparo
    Quando o status do pedido for alterado para pronto
    Entao o status do pedido deve ser alterado para pronto

  Cenario: Alterar status do pedido para finalizado
    Dado que o pedido exista no sistema com status pronto
    Quando o status do pedido for alterado para finalizado
    Entao o status do pedido deve ser alterado finalizado

  Cenario: Alterar status do pedido para cancelado
    Dado que o pedido exista no sistema com status diferente de cancelado
    Quando o status do pedido for alterado para cancelado
    Entao o status do pedido deve ser alterado cancelado