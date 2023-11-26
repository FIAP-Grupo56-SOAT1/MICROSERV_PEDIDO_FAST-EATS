package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface GerarPagamentoInputPort {
    Pagamento gerar(Pedido pedido, long tipoPagamentoId);
}
