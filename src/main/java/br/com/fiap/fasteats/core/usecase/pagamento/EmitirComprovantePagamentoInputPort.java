package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;

public interface EmitirComprovantePagamentoInputPort {
    Pagamento emitir(Long pedidoId);
}
