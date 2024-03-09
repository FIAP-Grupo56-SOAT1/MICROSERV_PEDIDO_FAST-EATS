package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.dataprovider.GerarPagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;

public class ConfirmarPedidoUseCase implements ConfirmarPedidoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final GerarPagamentoOutputPort gerarPagamentoOutputPort;
    private final PedidoValidator pedidoValidator;

    public ConfirmarPedidoUseCase(PedidoInputPort pedidoInputPort,
                                  GerarPagamentoOutputPort gerarPagamentoOutputPort,
                                  PedidoValidator pedidoValidator) {
        this.pedidoInputPort = pedidoInputPort;
        this.gerarPagamentoOutputPort = gerarPagamentoOutputPort;
        this.pedidoValidator = pedidoValidator;
    }

    @Override
    public Pedido confirmar(Long idPedido, Long tipoPagamentoId) {
        Pedido pedido = pedidoInputPort.consultar(idPedido);

        if (pedido.getProdutos().isEmpty())
            throw new ProdutoNotFound("O pedido não pode ser confirmado, pois não contém produtos");

        pedidoValidator.validarAlterarPedido(pedido);
        return gerarPagamentoOutputPort.gerar(pedido.getId(),tipoPagamentoId);
    }
}
