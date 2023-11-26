package br.com.fiap.fasteats.core.usecase.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface MetodoPagamentoInputPort {
    Pagamento pix(Pedido pedido);
    Pagamento mercadoPago(Pedido pedido);
}
