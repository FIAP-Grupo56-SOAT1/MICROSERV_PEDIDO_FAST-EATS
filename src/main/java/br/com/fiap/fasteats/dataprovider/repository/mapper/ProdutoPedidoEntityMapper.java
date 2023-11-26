package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoIdEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProdutoEntityMapper.class, PedidoEntityMapper.class})
public interface ProdutoPedidoEntityMapper {
    @Mapping(source = "produto.nome", target = "nomeProduto")
    @Mapping(source = "id.idProduto", target = "idProduto")
    @Mapping(source = "id.idPedido", target = "idPedido")
    @Mapping(source = "produto.descricao", target = "descricaoProduto")
    ProdutoPedido toProdutoPedido(ProdutoPedidoEntity produtoPedidoEntityMapper);

    @Mapping(source = "idProduto",target = "id.idProduto")
    @Mapping(source = "idPedido",target = "id.idPedido")
    @Mapping(source = "idProduto",target = "produto.id")
    @Mapping(source = "idPedido",target = "pedido.id")
    ProdutoPedidoEntity toProdutoPedido(ProdutoPedido produtoPedido);

    @Mapping(source = "idProduto",target = "idProduto")
    @Mapping(source = "idPedido",target = "idPedido")
    ProdutoPedidoIdEntity toProdutoPedidoId(ProdutoPedido produtoPedido);

    List<ProdutoPedido> toProdutosPedido(List<ProdutoPedidoEntity> produtoPedidosEntity);
}
