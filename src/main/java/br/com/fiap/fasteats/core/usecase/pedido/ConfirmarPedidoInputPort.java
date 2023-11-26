package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface ConfirmarPedidoInputPort {
    Pedido confirmar(Long idPedido, Long tipoPagamentoId);
}
