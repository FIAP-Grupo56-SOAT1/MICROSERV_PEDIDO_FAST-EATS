# language: pt
Funcionalidade: Cancelar Pedido

  Cenario: Cancelar pedido
    Dado que o pedido exista no sistema
    Quando o pedido for cancelado
    Entao o status do pedido deve ser alterado para cancelado
    E a data e hora de finalizacao do pedido deve ser preenchida