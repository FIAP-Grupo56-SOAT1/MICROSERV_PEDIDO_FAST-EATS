package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoAdapter implements PagamentoOutputPort {
    private final IntegracaoPagamento integracaoPagamento;

    @Override
    public List<Pagamento> listar() {
        return integracaoPagamento.listar();
    }

    @Override
    public Optional<Pagamento> consultar(Long id) {
        return integracaoPagamento.consultar(id);
    }

    @Override
    public Optional<Pagamento> consultarPorPedidoId(long pedidoId) {
        return integracaoPagamento.consultarPorPedidoId(pedidoId);
    }

    @Override
    public Pagamento gerarPagamento(Long idPedido, Long idFormaPagamento) {
        return integracaoPagamento.gerarPagamento(idPedido,idFormaPagamento);
    }

    @Override
    public Pagamento consultarPorIdPagamentoExterno(Long idPagamentoExterno) {
        return integracaoPagamento.consultarPorIdPagamentoExterno(idPagamentoExterno);
    }
}
