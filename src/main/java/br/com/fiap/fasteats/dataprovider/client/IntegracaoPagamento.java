package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.Pagamento;

import java.util.Optional;


public interface IntegracaoPagamento {
    Optional<Pagamento> consultar(Long id);

    Optional<Pagamento> consultarPorPedidoId(long pedidoId);

    void gerarPagamento(Long idPedido, Long idFormaPagamento);

    void notificarPedidoPago(Long pedidoId);

    void cancelarPagamento(Long pedidoId);
}
