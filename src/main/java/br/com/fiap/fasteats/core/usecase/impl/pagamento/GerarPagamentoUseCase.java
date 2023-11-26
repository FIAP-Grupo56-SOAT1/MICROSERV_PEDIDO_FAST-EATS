package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.exception.StatusPagametoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.FormaPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.GerarPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.MetodoPagamentoInputPort;
import br.com.fiap.fasteats.core.validator.GerarPagamentoValidator;

import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.MERCADO_PAGO;
import static br.com.fiap.fasteats.core.constants.FormaPagamentoConstants.PIX;

public class GerarPagamentoUseCase implements GerarPagamentoInputPort {
    private final FormaPagamentoInputPort formaPagamentoInputPort;
    private final GerarPagamentoValidator gerarPagamentoValidator;

    private final MetodoPagamentoInputPort metodoPagamentoInputPort;

    public GerarPagamentoUseCase(FormaPagamentoInputPort formaPagamentoInputPort,
                                 GerarPagamentoValidator gerarPagamentoValidator,
                                 MetodoPagamentoInputPort metodoPagamentoInputPort) {
        this.formaPagamentoInputPort = formaPagamentoInputPort;
        this.gerarPagamentoValidator = gerarPagamentoValidator;
        this.metodoPagamentoInputPort = metodoPagamentoInputPort;
    }

    @Override
    public Pagamento gerar(Pedido pedido, long formaPagamentoId) {
        gerarPagamentoValidator.validarPedidoStatus(pedido.getId());
        gerarPagamentoValidator.validarFormaPagamento(formaPagamentoId);

        switch (formaPagamentoInputPort.consultar(formaPagamentoId).getNome()) {
            case PIX -> {
                return metodoPagamentoInputPort.pix(pedido);
            }
            case MERCADO_PAGO -> {
                return metodoPagamentoInputPort.mercadoPago(pedido);
            }
            default -> throw new StatusPagametoNotFound("Forma de Pagamento não cadastrada");
        }
    }
}
