package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.entrypoint.controller.response.GerenciarPedidoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GerenciarPedidoMapper {
    @Mapping(source = "nomeStatusPedido", target = "statusPedido")
    GerenciarPedidoResponse toGerenciarPedidoResponse(Pedido pedido);

    List<GerenciarPedidoResponse> toGerenciarPedidosResponse(List<Pedido> pedidos);
}
