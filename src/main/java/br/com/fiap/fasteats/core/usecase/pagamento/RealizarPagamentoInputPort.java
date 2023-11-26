package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;

public interface RealizarPagamentoInputPort {
    Pagamento realizarPagamento(Long pedidoId);
}
