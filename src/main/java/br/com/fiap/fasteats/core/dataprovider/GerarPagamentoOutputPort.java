package br.com.fiap.fasteats.core.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Pedido;

public interface GerarPagamentoOutputPort {
    Pedido gerar(Long idPedido, Long idFormaPagamento);
}
