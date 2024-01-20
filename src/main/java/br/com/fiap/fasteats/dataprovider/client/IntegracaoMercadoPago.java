package br.com.fiap.fasteats.dataprovider.client;

import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;


public interface IntegracaoMercadoPago {
    PagamentoExterno consultarPagamento(PagamentoExterno pagamentoExterno);

    PagamentoExterno cancelarPagamento(Long idPagamentoExterno);
}
