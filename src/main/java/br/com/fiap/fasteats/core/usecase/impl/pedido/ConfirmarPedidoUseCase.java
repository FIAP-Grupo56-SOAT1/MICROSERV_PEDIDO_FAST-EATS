package br.com.fiap.fasteats.core.usecase.impl.pedido;

import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pagamento.GerarPagamentoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.ConfirmarPedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;

public class ConfirmarPedidoUseCase implements ConfirmarPedidoInputPort {
    private final PedidoInputPort pedidoInputPort;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private final GerarPagamentoInputPort gerarPagamentoInputPort;
    private final PedidoValidator pedidoValidator;

    public ConfirmarPedidoUseCase(PedidoInputPort pedidoInputPort,
                                  AlterarPedidoStatusInputPort alterarPedidoStatusInputPort,
                                  GerarPagamentoInputPort gerarPagamentoInputPort,
                                  PedidoValidator pedidoValidator) {
        this.pedidoInputPort = pedidoInputPort;
        this.alterarPedidoStatusInputPort = alterarPedidoStatusInputPort;
        this.gerarPagamentoInputPort = gerarPagamentoInputPort;
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
        gerarPagamentoInputPort.gerar(pedido, tipoPagamentoId);
    }
}
