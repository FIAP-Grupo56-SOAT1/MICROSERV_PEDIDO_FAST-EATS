package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoMercadoPago;
import br.com.fiap.fasteats.dataprovider.client.mapper.PagamentoExternoMapper;
import br.com.fiap.fasteats.dataprovider.client.mapper.StatusPagamentoMapper;
import br.com.fiap.fasteats.dataprovider.client.request.PedidoPagamentoExternoRequest;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoExternoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.PagamentoResponse;
import br.com.fiap.fasteats.dataprovider.client.response.StatusPagamentoResponse;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class IntegracaoMercadoPagoImpl implements IntegracaoMercadoPago {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoMercadoPagoImpl.class);

    private final PagamentoExternoMapper pagamentoExternoMapper;

    private final RestTemplate restTemplate;

    @Value("${URL_PAGAMENTO_SERVICE}")
    private String URL_BASE;

    private final String URI = "/pagamento-externo";

    @Override
    public PagamentoExterno enviarSolicitacaoPagamento(Pedido pedido) {
        return criarPagamentoExterno(pedido);
    }

    @Override
    public PagamentoExterno consultarPagamento(PagamentoExterno pagamentoExterno) {
        return consultarPagamentoExterno(pagamentoExterno);
    }

    @Override
    public PagamentoExterno cancelarPagamento(Long idPagamentoExterno) {
        return cancelarPagamentoExterno(idPagamentoExterno);
    }

    private PagamentoExterno consultarPagamentoExterno(PagamentoExterno pagamentoExternoRequisicao) {
        try {
            //TODO precisa implementar endpoint no microservico de pagamento
            PagamentoExternoResponse pagamentoExternoResponse =
                    restTemplate.getForObject(URL_BASE + URI +"/{id}",
                            PagamentoExternoResponse.class,pagamentoExternoRequisicao.getId());

            PagamentoExterno pagamentoExterno = pagamentoExternoMapper.
                    toPagamentoExternoResponse(pagamentoExternoResponse);
            if (pagamentoExternoRequisicao.isSimulacaoPagamento()) {
                pagamentoExterno.setStatus(pagamentoExternoRequisicao.getStatus());
                logger.info("simulacao de pagamento em andamento {}", pagamentoExterno);
            }
            return pagamentoExterno;
        } catch (Exception ex) {
            throw new RegraNegocioException("Erro ao consultar pagamento externo " + ex.getMessage());
        }
    }

    private PagamentoExterno criarPagamentoExterno(Pedido pedido){
        try {
            PedidoPagamentoExternoRequest pedidoPagamentoExternoRequest =
                    pagamentoExternoMapper.toPagamentoExternoPedidoRequest(pedido);
            //TODO precisa implementar endpoint no microservico de pagamento
            PagamentoExternoResponse pagamentoExternoResponse =
                    restTemplate.postForObject(URL_BASE +
                            URI,pedidoPagamentoExternoRequest,PagamentoExternoResponse.class);

            logger.info("retorno criar pagamento mercado pago {}", pagamentoExternoResponse);
            return pagamentoExternoMapper.toPagamentoExternoResponse(pagamentoExternoResponse);
        } catch (Exception ex) {
            throw new RegraNegocioException("Erro ao gerar pagamento externo " + ex.getMessage());
        }

    }

    private PagamentoExterno cancelarPagamentoExterno(Long idPagamentoExterno) {
        try {
            PagamentoExternoResponse pagamentoExternoResponse =
                    restTemplate.getForObject(URL_BASE + URI+"/mercadopago/{idPagamentoExterno}/cancelar",
                            PagamentoExternoResponse.class,idPagamentoExterno);
            return pagamentoExternoMapper.toPagamentoExternoResponse(pagamentoExternoResponse);
        } catch (Exception ex) {
            throw new RegraNegocioException("Erro ao cancelar pagamento externo " + ex.getMessage());
        }
    }
}