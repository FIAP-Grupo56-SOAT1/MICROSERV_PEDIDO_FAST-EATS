package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.dataprovider.FormaPagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.FormaPagamentoNotFound;
import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;

import java.util.List;

public class FormaPagamentoUseCase implements FormaPagamentoInputPort {
    FormaPagamentoOutputPort formaPagamentoOutputPort;

    public FormaPagamentoUseCase(FormaPagamentoOutputPort formaPagamentoOutputPort) {
        this.formaPagamentoOutputPort = formaPagamentoOutputPort;
    }

    @Override
    public FormaPagamento consultar(Long id) {
        return formaPagamentoOutputPort.consultar(id).orElseThrow(() -> new FormaPagamentoNotFound("Forma de pagamento não encontrada"));
    }

    @Override
    public FormaPagamento consultarPorNome(String nome) {
        return formaPagamentoOutputPort.consultarPorNome(nome).orElseThrow(() -> new FormaPagamentoNotFound("Forma de pagamento não encontrada"));
    }
}
