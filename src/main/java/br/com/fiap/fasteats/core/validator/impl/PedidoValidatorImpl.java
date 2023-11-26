package br.com.fiap.fasteats.core.validator.impl;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.exception.StatusPedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;

import java.util.ArrayList;
import java.util.List;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;

public class PedidoValidatorImpl implements PedidoValidator {
    private final StatusPedidoInputPort statusPedidoInputPort;
    private final PedidoOutputPort pedidoOutputPort;

    public PedidoValidatorImpl(StatusPedidoInputPort statusPedidoInputPort, PedidoOutputPort pedidoOutputPort) {
        this.statusPedidoInputPort = statusPedidoInputPort;
        this.pedidoOutputPort = pedidoOutputPort;
    }

    public void validarAlterarPedido(Pedido pedido) {
        List<StatusPedido> listaStatusPedido = new ArrayList<>(statusPedidoInputPort.listar());
        if (listaStatusPedido.isEmpty()) throw new StatusPedidoNotFound("Nenhum status de pedido encontrado");

        listaStatusPedido.removeIf(status -> status.getNome().equals(STATUS_PEDIDO_CRIADO));
        listaStatusPedido.removeIf(status -> status.getNome().equals(STATUS_PEDIDO_AGUARDANDO_PAGAMENTO));
        listaStatusPedido.removeIf(status -> status.getNome().equals(STATUS_PEDIDO_CANCELADO));

        listaStatusPedido
                .stream()
                .filter(statusPedido -> statusPedido.getId().equals(pedido.getStatusPedido()))
                .findAny()
                .ifPresent(s -> {
                    throw new RegraNegocioException("Este pedido não pode ser alterado, pois está " + pedido.getNomeStatusPedido());
                });
        validarAtualizacaoDados(pedido);
    }

    private void validarAtualizacaoDados(Pedido pedido) {
        Pedido pedidoSalvo = pedidoOutputPort.consultarPedido(pedido.getId()).orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        if (pedido.isIdentificaCliente()) {
            throw new RegraNegocioException("Identificação do cliente não permitida após início do pedido");
        }

        if (!pedido.getDataHoraCriado().equals(pedidoSalvo.getDataHoraCriado())) {
            throw new RegraNegocioException("Data e hora da criação do Pedido não pode ser alterada");
        }

        if (pedidoSalvo.getCliente() != null && (!pedidoSalvo.getCliente().getCpf().equals(pedido.getCliente().getCpf()))) {
            throw new RegraNegocioException("O Cliente informado no Pedido não pode ser alterado");
        }
    }
}
