package br.com.fiap.fasteats.core.usecase.impl.pagamento;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.usecase.pedido.CozinhaPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.EmitirComprovantePagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pagamento.PagamentoInputPort;
import br.com.fiap.fasteats.core.validator.EmitirComprovantePagamentoValidator;

public class EmitirComprovantePagamentoUseCase implements EmitirComprovantePagamentoInputPort {
    private final PagamentoInputPort pagamentoInputPort;
    private final CozinhaPedidoInputPort cozinhaPedidoInputPort;
    private final EmitirComprovantePagamentoValidator emitirComprovantePagamentoValidator;

    public EmitirComprovantePagamentoUseCase(PagamentoInputPort pagamentoInputPort, CozinhaPedidoInputPort cozinhaPedidoInputPort, EmitirComprovantePagamentoValidator emitirComprovantePagamentoValidator) {
        this.pagamentoInputPort = pagamentoInputPort;
        this.cozinhaPedidoInputPort = cozinhaPedidoInputPort;
        this.emitirComprovantePagamentoValidator = emitirComprovantePagamentoValidator;
    }

    @Override
    public Pagamento emitir(Long pedidoId) {
        emitirComprovantePagamentoValidator.validarEmitirComprovantePagamento(pedidoId);
        cozinhaPedidoInputPort.receberPedido(pedidoId);
        return pagamentoInputPort.consultarPorIdPedido(pedidoId);
    }
}
