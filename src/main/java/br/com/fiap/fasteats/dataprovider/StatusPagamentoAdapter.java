package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.dataprovider.client.IntegracaoStatusPagamento;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.core.dataprovider.StatusPagamentoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusPagamentoAdapter implements StatusPagamentoOutputPort {
    private final IntegracaoStatusPagamento integracaoStatusPagamento;

    @Override
    public List<StatusPagamento> listar() {
        return integracaoStatusPagamento.listar();
    }

    @Override
    public StatusPagamento consultarPorNome(String nome) {
        return integracaoStatusPagamento.consultarPorNome(nome);
    }

    @Override
    public StatusPagamento consultar(Long id) {
        return integracaoStatusPagamento.consultar(id);
    }
}
