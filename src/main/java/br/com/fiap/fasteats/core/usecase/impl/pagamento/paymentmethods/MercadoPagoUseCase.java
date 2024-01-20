package br.com.fiap.fasteats.core.usecase.impl.pagamento.paymentmethods;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;

public class MercadoPagoUseCase {


    private final PagamentoInputPort pagamentoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;

    public MercadoPagoUseCase(PagamentoInputPort pagamentoInputPort, FormaPagamentoInputPort formaPagamentoInputPort) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
    }

    public Pagamento gerarPagamento(Pedido pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(formaPagamentoInputPort.consultarPorNome(MERCADO_PAGO));
        return pagamentoInputPort.criar(pagamento);
    }
}
