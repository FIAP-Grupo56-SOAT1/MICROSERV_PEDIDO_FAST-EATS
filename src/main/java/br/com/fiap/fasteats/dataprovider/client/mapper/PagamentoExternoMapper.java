package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoExternoResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PagamentoExternoMapper {

    PagamentoExterno toPagamentoExternoResponse(PagamentoExternoResponse pagamentoExternoResponse);

}
