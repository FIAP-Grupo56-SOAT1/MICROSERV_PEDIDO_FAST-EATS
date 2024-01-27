package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.dataprovider.repository.entity.StatusPedidoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusPedidoEntityMapper {
    StatusPedidoEntity toStatusPedidoEntity(StatusPedido statusPedido);

    StatusPedido toStatusPedido(StatusPedidoEntity statusPedidoEntity);
}
