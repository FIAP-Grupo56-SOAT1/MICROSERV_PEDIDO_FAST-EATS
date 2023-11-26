package br.com.fiap.fasteats.core.usecase.impl.pagamento.paymentmethods;

import br.com.fiap.fasteats.core.domain.exception.PagamentoExternoException;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoMercadoPago;

import java.util.Objects;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;

public class MercadoPagoUseCase {
    private final PagamentoInputPort pagamentoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;
    private final IntegracaoMercadoPago integracaoMercadoPago;

    public MercadoPagoUseCase(PagamentoInputPort pagamentoInputPort,
                              FormaPagamentoInputPort formaPagamentoInputPort,
                              IntegracaoMercadoPago integracaoMercadoPago) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
        this.integracaoMercadoPago = integracaoMercadoPago;
    }

    public Pagamento gerarPagamento(Pedido pedido) {
        PagamentoExterno pagamentoExterno = integracaoMercadoPago.enviarSolicitacaoPagamento(pedido);

        if (Objects.isNull(pagamentoExterno.getId()))
            throw new PagamentoExternoException("Erro ao gerar pagamento");

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(formaPagamentoInputPort.consultarPorNome(MERCADO_PAGO));
        pagamento.setIdPagamentoExterno(pagamentoExterno.getId());
        pagamento.setQrCode(pagamentoExterno.getQrCode());
        pagamento.setUrlPagamento(pagamentoExterno.getUrlPagamento());
        return pagamentoInputPort.criar(pagamento);
    }
}
