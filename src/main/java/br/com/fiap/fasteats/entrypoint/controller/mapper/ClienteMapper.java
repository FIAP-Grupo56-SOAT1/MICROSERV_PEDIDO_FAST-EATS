package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.entrypoint.controller.request.ClienteRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ClienteResponse;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toCliente(ClienteRequest clienteRequest);

    ClienteResponse toClienteResponse(Cliente cliente);

    List<ClienteResponse> toClientesResponse(List<Cliente> clientes);
}
