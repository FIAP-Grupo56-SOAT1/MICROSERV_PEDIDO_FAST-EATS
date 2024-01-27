package br.com.fiap.fasteats.dataprovider.repository.mapper;

import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.dataprovider.repository.entity.ClienteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteEntityMapper {
    ClienteEntity toClienteEntity(Cliente cliente);

    Cliente toCliente(ClienteEntity clienteEntity);

    List<Cliente> toClientes(List<ClienteEntity> clientesEntity);

}
