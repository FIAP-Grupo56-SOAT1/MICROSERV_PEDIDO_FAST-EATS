package br.com.fiap.fasteats.dataprovider.client.mapper;

import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.dataprovider.client.request.PedidoPagamentoExternoRequest;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoExternoResponse;
import com.mercadopago.resources.payment.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagamentoExternoMapper {

    @Mapping(source = "pointOfInteraction.transactionData.qrCode", target = "qrCode")
    @Mapping(source = "pointOfInteraction.transactionData.ticketUrl", target = "urlPagamento")
    @Mapping(source = "pointOfInteraction.transactionData.qrCodeBase64", target = "qrCodeBase64")
    @Mapping(target = "simulacaoPagamento", ignore = true)
    @Mapping(target = "mensagem", ignore = true)
    PagamentoExterno toPagamentoExterno(Payment payment);

    PagamentoExterno toPagamentoExternoResponse(PagamentoExternoResponse pagamentoExternoResponse);

    PedidoPagamentoExternoRequest toPagamentoExternoPedidoRequest(Pedido pedido);
}
