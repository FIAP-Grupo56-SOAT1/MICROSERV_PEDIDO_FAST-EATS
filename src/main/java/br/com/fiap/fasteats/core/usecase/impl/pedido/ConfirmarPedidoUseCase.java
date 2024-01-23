package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;

public class ConfirmarPedidoUseCase implements ConfirmarPedidoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private final PagamentoOutputPort gerarPagamentoOutPort;
    private final PedidoValidator pedidoValidator;

    public ConfirmarPedidoUseCase(PedidoInputPort pedidoInputPort,
                                  AlterarPedidoStatusInputPort alterarPedidoStatusInputPort,
                                  PagamentoOutputPort gerarPagamentoOutPort,
                                  PedidoValidator pedidoValidator) {
        this.pedidoInputPort = pedidoInputPort;
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
        this.gerarPagamentoOutPort = gerarPagamentoOutPort;
        this.pedidoValidator = pedidoValidator;
    }

    @Override
    public Pedido confirmar(Long idPedido, Long tipoPagamentoId) {
        Pedido pedido = pedidoInputPort.consultar(idPedido);

        if (pedido.getProdutos().isEmpty())
            throw new ProdutoNotFound("O pedido não pode ser confirmado, pois não contém produtos");

        pedidoValidator.validarAlterarPedido(pedido);
        gerarPagamento(pedido, tipoPagamentoId);
        return alterarPedidoStatusInputPort.aguardandoPagamento(pedido.getId());
    }

    private void gerarPagamento(Pedido pedido, Long tipoPagamentoId) {
        gerarPagamentoOutPort.gerarPagamento(pedido.getId(),tipoPagamentoId);
    }
}
