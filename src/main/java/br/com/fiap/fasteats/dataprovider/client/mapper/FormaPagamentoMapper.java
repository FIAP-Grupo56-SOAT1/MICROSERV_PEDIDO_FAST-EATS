package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.FormaPagamento;
import br.com.fiap.fasteats.dataprovider.client.response.FormaPagamentoResponse;
import br.com.fiap.fasteats.entrypoint.controller.request.FormaPagamentoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormaPagamentoMapper {
    @Mapping(target = "id", ignore = true)
    FormaPagamento toFormaPagamento(FormaPagamentoRequest formaPagamentoResponse);
    FormaPagamento toFormaPagamento(FormaPagamentoResponse formaPagamento);
    List<FormaPagamentoResponse> toFormaPagamentoResponseList(List<FormaPagamento> formaPagamentoList);
}
