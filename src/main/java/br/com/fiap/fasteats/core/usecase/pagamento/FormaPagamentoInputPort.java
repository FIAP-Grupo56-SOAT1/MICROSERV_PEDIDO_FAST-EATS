package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;

public interface FormaPagamentoInputPort {

    FormaPagamento consultar(Long id);
    FormaPagamento consultarPorNome(String nome);
}