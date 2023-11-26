package br.com.fiap.fasteats.entrypoint.controller.mapper;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.entrypoint.controller.response.PagamentoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FormaPagamentoMapper.class,
                                           StatusPagamentoMapper.class,
                                           PedidoMapper.class,
                                           ClienteMapper.class})
public interface PagamentoMapper {
    @Mapping(source = "pedido.cliente", target = "cliente")
    @Mapping(source = "formaPagamento", target = "formaPagamento")
    @Mapping(source = "pedido", target = "pedido")
    @Mapping(source = "statusPagamento", target = "statusPagamento")
    PagamentoResponse toPagamentoResponse(Pagamento pagamento);

    @Mapping(source = "pedido.cliente", target = "cliente")
    @Mapping(source = "formaPagamento", target = "formaPagamento")
    @Mapping(source = "pedido", target = "pedido")
    @Mapping(source = "statusPagamento", target = "statusPagamento")

    List<PagamentoResponse> toPagamentosResponse(List<Pagamento> pagamentos);
}
