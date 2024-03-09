package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;

import java.util.Optional;

public interface PagamentoOutputPort {
    Optional<Pagamento> consultarPorPedidoId(long pedidoId);

    void notificarPedidoPago(Long pedidoId);
}
