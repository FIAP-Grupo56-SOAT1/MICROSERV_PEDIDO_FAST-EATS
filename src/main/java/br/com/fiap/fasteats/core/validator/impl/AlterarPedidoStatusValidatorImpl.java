package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;

public class AlterarPedidoStatusValidatorImpl implements AlterarPedidoStatusValidator {
    private final PedidoInputPort pedidoInputPort;
    private final StatusPedidoInputPort statusPedidoInputPort;

    public AlterarPedidoStatusValidatorImpl(PedidoInputPort pedidoInputPort, StatusPedidoInputPort statusPedidoInputPort) {
        this.pedidoInputPort = pedidoInputPort;
        this.statusPedidoInputPort = statusPedidoInputPort;
    }

    @Override
    public void validarCriado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO))
            throw new RegraNegocioException("O Pedido voltar para o status criado se estiver com o status " + STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);
    }

    @Override
    public void validarAguardandoPagamento(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        String statusPedido = recuperarNomeStatusPedido(pedido);
        if (!statusPedido.equals(STATUS_PEDIDO_CRIADO) && !statusPedido.equals(STATUS_PEDIDO_PAGO))
            throw new RegraNegocioException("O Pedido só pode ir para o status " + STATUS_PEDIDO_AGUARDANDO_PAGAMENTO +
                    " se estiver com o status " + STATUS_PEDIDO_CRIADO + " ou " + STATUS_PEDIDO_PAGO);
    }

    @Override
    public void validarPago(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO))
            throw new RegraNegocioException("O Pedido só pode ser pago se estiver com o status " + STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);
    }

    @Override
    public void validarRecebido(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_PAGO))
            throw new RegraNegocioException("O Pedido só pode ser recebido se estiver com o status " + STATUS_PEDIDO_PAGO);
    }

    @Override
    public void validarEmPreparo(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_RECEBIDO))
            throw new RegraNegocioException("O Pedido só pode ser iniciado se estiver com o status " + STATUS_PEDIDO_RECEBIDO);
    }

    @Override
    public void validarPronto(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_EM_PREPARO))
            throw new RegraNegocioException("O Pedido só pode ser finalizado se estiver com o status " + STATUS_PEDIDO_EM_PREPARO);
    }

    @Override
    public void validarFinalizado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (!recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_PRONTO))
            throw new RegraNegocioException("O Pedido só pode ser retirado se estiver com o status " + STATUS_PEDIDO_PRONTO);
    }

    @Override
    public void validarCancelado(Long pedidoId) {
        Pedido pedido = recuperarPedido(pedidoId);
        if (recuperarNomeStatusPedido(pedido).equals(STATUS_PEDIDO_CANCELADO))
            throw new RegraNegocioException("O Pedido não pode ser cancelado, pois já está com status " + STATUS_PEDIDO_CANCELADO);
    }

    private Pedido recuperarPedido(Long pedidoId) {
        return pedidoInputPort.consultar(pedidoId);
    }

    private String recuperarNomeStatusPedido(Pedido pedido) {
        return statusPedidoInputPort.consultar(pedido.getStatusPedido()).getNome();
    }
}
