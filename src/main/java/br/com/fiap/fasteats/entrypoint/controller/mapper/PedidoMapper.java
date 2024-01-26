package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.entrypoint.controller.request.PedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoPedidoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.PedidoResponse;
import br.com.fiap.fasteats.entrypoint.controller.response.ProdutoPedidoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomeStatusPedido", ignore = true)
    @Mapping(target = "statusPedido", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "dataHoraCriado", ignore = true)
    @Mapping(target = "dataHoraRecebimento", ignore = true)
    @Mapping(target = "dataHoraFinalizado", ignore = true)
    @Mapping(target = "tempoEspera", ignore = true)
    @Mapping(target = "idPagamentoExterno", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "urlPagamento", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "valor", ignore = true)
    Pedido toPedido(PedidoRequest pedidoRequest);

    @Mapping(target = "descricaoProduto", ignore = true)
    @Mapping(target = "nomeProduto", ignore = true)
    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "valor", ignore = true)
    ProdutoPedido toProdutoPedido(ProdutoPedidoRequest produtoPedidoRequest);

    List<ProdutoPedido> toProdutosPedidoRequest(List<ProdutoPedidoRequest> produtosPedidoRequest);

    @Mapping(source = "nomeStatusPedido", target = "statusPedido")
    PedidoResponse toPedidoResponse(Pedido pedido);

    List<PedidoResponse> toPedidosResponse(List<Pedido> pedidos);

    ProdutoPedidoResponse toProdutoPedidoResponse(ProdutoPedido produtoPedido);

    List<ProdutoPedidoResponse> toProdutosPedidoResponse(List<ProdutoPedido> produtosPedido);
}
