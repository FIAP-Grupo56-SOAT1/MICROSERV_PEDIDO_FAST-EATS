package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.GerarPagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GerarPagamentoAdapter implements GerarPagamentoOutputPort {
    private final IntegracaoPagamento integracaoPagamento;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    @Override
    @Transactional
    public Pedido gerar(Long idPedido, Long idFormaPagamento) {
        Pedido pedido = alterarPedidoStatusInputPort.aguardandoPagamento(idPedido);
        integracaoPagamento.gerarPagamento(idPedido, idFormaPagamento);
        return pedido;
    }
}
