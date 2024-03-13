package br.com.fiap.fasteats.core.usecase.pedido;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface AlterarPedidoStatusInputPort {
    Pedido criado(Long pedidoId);

    Pedido aguardandoPagamento(Long pedidoId);

    Pedido pago(Long pedidoId);

    Pedido recebido(Long pedidoId);

    Pedido emPreparo(Long pedidoId);

    Pedido pronto(Long pedidoId);

    Pedido finalizado(Long pedidoId);

    Pedido aguardandoCancelamento(Long pedidoId);

    Pedido cancelado(Long pedidoId);

    Pedido atualizarStatusPedido(Long pedidoId, Long idStatus);
}
