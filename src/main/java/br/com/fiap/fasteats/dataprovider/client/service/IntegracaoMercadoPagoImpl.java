package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.PagamentoExterno;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.dataprovider.client.IntegracaoMercadoPago;
import br.com.fiap.fasteats.entrypoint.controller.mapper.PagamentoExternoMapper;
import br.com.fiap.fasteats.entrypoint.controller.response.PagamentoExternoResponse;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class IntegracaoMercadoPagoImpl implements IntegracaoMercadoPago {

    private final Logger logger = LoggerFactory.getLogger(IntegracaoMercadoPagoImpl.class);

    private final PagamentoExternoMapper pagamentoExternoMapper;

    @Value("${pagamento.mercado.pago.email.empresa}")
    private String emailEmpresa;

    @Value("${pagamento.mercado.pago.credencial}")
    private String accessToken;

    @Value("${pagamento.mercado.pago.userid}")
    private String userIdAplicacaoMercadoPago;

    @Value("${pagamento.mercado.pago.tipo.pagamento}")
    private String tipoPagamentoMercadoPago;

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
        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        try {
            PagamentoExternoResponse pagamentoExternoResponse =  new PagamentoExternoResponse();
            pagamentoExternoResponse.setId(1L);

            //TODO requisicao pagamentoExterno

            String paymentString = pagamentoExternoResponse.toString();
            logger.info("retorno consultar mercado pago {}", paymentString);
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
        //TODO criar chamada para pagamento

        try {
            PagamentoExternoResponse pagamentoExternoResponse =  new PagamentoExternoResponse();
            pagamentoExternoResponse.setId(1L);

            //TODO requisicao pagamentoExterno

            logger.info("retorno criar pagamento mercado pago {}", pagamentoExternoResponse);
            return pagamentoExternoMapper.toPagamentoExternoResponse(pagamentoExternoResponse);
        } catch (Exception ex) {
            throw new RegraNegocioException("Erro ao gerar pagamento externo " + ex.getMessage());
        }

    }

    private PagamentoExterno cancelarPagamentoExterno(Long idPagamentoExterno) {
        try {
            PagamentoExternoResponse pagamentoExternoResponse =  new PagamentoExternoResponse();
            pagamentoExternoResponse.setId(1L);

            //TODO requisicao pagamentoExterno
            String paymentString = pagamentoExternoResponse.toString();
            logger.info("retorno cancelar pagamento mercado pago {}", paymentString);
            return pagamentoExternoMapper.toPagamentoExternoResponse(pagamentoExternoResponse);
        } catch (Exception ex) {
            throw new RegraNegocioException("Erro ao cancelar pagamento externo " + ex.getMessage());
        }
    }
}