package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoAdapter implements PagamentoOutputPort {
    private final IntegracaoPagamento integracaoPagamento;

    @Override
    public Optional<Pagamento> consultarPorPedidoId(long pedidoId) {
        return integracaoPagamento.consultarPorPedidoId(pedidoId);
    }

    @Override
    public void notificarPedidoPago(Long pedidoId) {
        integracaoPagamento.notificarPedidoPago(pedidoId);
    }

    @Override
    public void cancelarPagamento(Long pedidoId) {
        integracaoPagamento.cancelarPagamento(pedidoId);
    }
}
