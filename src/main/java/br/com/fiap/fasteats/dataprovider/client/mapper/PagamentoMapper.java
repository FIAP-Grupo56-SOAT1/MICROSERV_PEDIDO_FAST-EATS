package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import br.com.fiap.fasteats.entrypoint.controller.mapper.ClienteMapper;
import br.com.fiap.fasteats.entrypoint.controller.mapper.FormaPagamentoMapper;
import br.com.fiap.fasteats.entrypoint.controller.mapper.PedidoMapper;
import br.com.fiap.fasteats.entrypoint.controller.mapper.StatusPagamentoMapper;
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

    List<Pagamento> toPagamentos(List<PagamentoResponse> pagamentos);

    @Mapping(target = "pedido.cliente", source = "cliente")
    @Mapping(target = "formaPagamento", source = "formaPagamento")
    @Mapping(target = "pedido", source = "pedido")
    @Mapping(target = "statusPagamento", source = "statusPagamento")
    Pagamento toPagamento(PagamentoResponse pagamento);
}
