package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.paymentmethods.MercadoPagoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.paymentmethods.PixUseCase;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.MetodoPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;

public class MetodoPagamentoUseCase implements MetodoPagamentoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final FormaPagamentoInputPort formaPagamentoInputPort;

    public MetodoPagamentoUseCase(PagamentoInputPort pagamentoInputPort,
                                  FormaPagamentoInputPort formaPagamentoInputPort) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.formaPagamentoInputPort = formaPagamentoInputPort;
    }

    @Override
    public Pagamento pix(Pedido pedido) {
        final PixUseCase pixUseCase = new PixUseCase(pagamentoInputPort, formaPagamentoInputPort);
        return pixUseCase.gerarPagamento(pedido);
    }

    @Override
    public Pagamento mercadoPago(Pedido pedido) {
        final MercadoPagoUseCase mercadoPagoUseCase = new MercadoPagoUseCase(pagamentoInputPort, formaPagamentoInputPort);
        return mercadoPagoUseCase.gerarPagamento(pedido);
    }
}
