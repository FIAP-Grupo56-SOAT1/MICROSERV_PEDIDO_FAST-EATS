package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.dataprovider.CozinhaPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pagamento.EmitirComprovantePagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.validator.EmitirComprovantePagamentoValidator;

public class EmitirComprovantePagamentoUseCase implements EmitirComprovantePagamentoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final CozinhaPedidoOutputPort cozinhaPedidoOutputPort;
    private final EmitirComprovantePagamentoValidator emitirComprovantePagamentoValidator;

    public EmitirComprovantePagamentoUseCase(PagamentoInputPort pagamentoInputPort, CozinhaPedidoOutputPort cozinhaPedidoOutputPort, EmitirComprovantePagamentoValidator emitirComprovantePagamentoValidator) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.cozinhaPedidoOutputPort = cozinhaPedidoOutputPort;
        this.emitirComprovantePagamentoValidator = emitirComprovantePagamentoValidator;
    }

    @Override
    public Pagamento emitir(Long pedidoId) {
        emitirComprovantePagamentoValidator.validarEmitirComprovantePagamento(pedidoId);
        cozinhaPedidoOutputPort.receberPedido(pedidoId);
        return pagamentoInputPort.consultarPorIdPedido(pedidoId);
    }
}
