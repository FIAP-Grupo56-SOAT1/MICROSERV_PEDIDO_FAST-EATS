package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;


public interface IntegracaoMercadoPago {
    PagamentoExterno enviarSolicitacaoPagamento(Pedido pedido);

    PagamentoExterno consultarPagamento(PagamentoExterno pagamentoExterno);

    PagamentoExterno cancelarPagamento(Long idPagamentoExterno);
}
