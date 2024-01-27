package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.response.FormaPagamentoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper {
    FormaPagamento toFormaPagamento(FormaPagamentoResponse formaPagamento);
}
