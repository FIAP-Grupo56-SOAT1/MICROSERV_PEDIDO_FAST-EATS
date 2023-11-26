package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.dataprovider.repository.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProdutoPedidoEntityMapper.class)
public interface PedidoEntityMapper {
    @Mapping(source = "statusPedido", target = "statusEntity.id")
    @Mapping(source = "produtos", target = "listaProdutos")
    PedidoEntity toPedidoEntity(Pedido pedido);

    @Mapping(source = "statusEntity.id", target = "statusPedido")
    @Mapping(source = "statusEntity.nome", target = "nomeStatusPedido")
    @Mapping(source = "listaProdutos", target = "produtos")
    @Mapping(target = "tempoEspera", ignore = true)
    @Mapping(target = "identificaCliente", ignore = true)
    @Mapping(target = "idPagamentoExterno", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "urlPagamento", ignore = true)
    Pedido toPedido(PedidoEntity pedidoEntity);

    List<Pedido> toPedidos(List<PedidoEntity> pedidosEntity);
}
