package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.StatusPagamento;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.StatusPagamentoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusPagamentoMapper {
    StatusPagamentoResponse toStatusPagamentoResponse(StatusPagamento statusPagamento);

    StatusPagamento toStatusPagamento(StatusPagamentoResponse statusPagamento);


    List<StatusPagamento> toStatusPagamentos(List<StatusPagamentoResponse> pagamentos);
}
