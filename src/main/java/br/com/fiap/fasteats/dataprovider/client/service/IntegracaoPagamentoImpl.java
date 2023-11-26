package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoPagamento;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoPagamentoImpl implements IntegracaoPagamento {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoPagamentoImpl.class);


    @Override
    public List<Pagamento> listar() {
        return null;
    }

    @Override
    public Optional<Pagamento> consultar(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Pagamento> consultarPorPedidoId(long pedidoId) {
        return Optional.empty();
    }

    @Override
    public Pagamento salvarPagamento(Pagamento pagamento) {
        return null;
    }

    @Override
    public Pagamento atualizarPagamento(Pagamento pagamento) {
        return null;
    }

    @Override
    public Optional<Pagamento> consultarPorIdPagamentoExterno(Long idPagamentoExterno) {
        return Optional.empty();
    }
}