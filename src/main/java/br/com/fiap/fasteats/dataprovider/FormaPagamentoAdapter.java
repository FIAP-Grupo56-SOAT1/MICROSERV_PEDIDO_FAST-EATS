package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.FormaPagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoFormaPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FormaPagamentoAdapter implements FormaPagamentoOutputPort {
    private final IntegracaoFormaPagamento integracaoFormaPagamento;

    @Override
    public Optional<FormaPagamento> consultar(Long id) {
        return integracaoFormaPagamento.consultar(id);
    }
    @Override
    public Optional<FormaPagamento> consultarPorNome(String nome) {
        return integracaoFormaPagamento.consultarPorNome(nome);
    }
}
