package br.com.fiap.fasteats.dataprovider.client.service;

import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.dataprovider.client.AlterarStatusPedidoIntegration;
import br.com.fiap.fasteats.dataprovider.client.request.CozinhaPedidoRequest;
import br.com.fiap.fasteats.dataprovider.client.request.PagamentoCancelarPedidoRequest;
import br.com.fiap.fasteats.dataprovider.client.request.PagamentoPedidoRequest;
import br.com.fiap.fasteats.dataprovider.client.response.AlterarStatusPedidoResponse;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlterarStatusPedidoIntegrationImpl implements AlterarStatusPedidoIntegration {
    @Value("${sqs.queue.pedido.criado}")
    private String filaPedidoCriado;
    @Value("${sqs.queue.pedido.aguardando-pagamento}")
    private String filaPedidoAguardandoPagamento;
    @Value("${sqs.queue.pedido.pago}")
    private String filaPedidoPago;
    @Value("${sqs.queue.pedido.recebido}")
    private String filaPedidoRecebido;
    @Value("${sqs.queue.pedido.em-preparo}")
    private String filaPedidoEmPreparo;
    @Value("${sqs.queue.pedido.pronto}")
    private String filaPedidoPronto;
    @Value("${sqs.queue.pedido.finalizado}")
    private String filaPedidoFinalizado;
    @Value("${sqs.queue.pedido.cancelado}")
    private String filaPedidoCancelado;
    @Value("${sqs.queue.pagamento.erro.pagamento-pedido}")
    private String filaPagamentoErroPagamentoPedido;
    @Value("${sqs.queue.cozinha.erro.pedido.recebido}")
    private String filaCozinhaErroPedidoRecebido;
    @Value("${sqs.queue.cozinha.erro.pedido.em-preparo}")
    private String filaCozinhaErroPedidoEmPreparo;
    @Value("${sqs.queue.cozinha.erro.pedido.pronto}")
    private String filaCozinhaErroPedidoPronto;
    @Value("${sqs.queue.cozinha.erro.pedido.finalizado}")
    private String filaCozinhaErroPedidoFinalizado;
    @Value("${sqs.queue.pagamento.erro.pedido.cancelar}")
    private String filaPagamentoErroPedidoCancelado;
    private final SqsTemplate sqsTemplate;
    private final AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;
    private static final String MENSAGEM_SUCESSO = "Alteração de status do pedido %d para %s recebido da fila %s com sucesso!";
    private static final String MENSAGEM_ERRO = "Erro ao processar pedido %d da fila %s: {}";

    @Override
    @SqsListener("${sqs.queue.pedido.criado}")
    public void criado(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.criado(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_CRIADO, filaPedidoCriado);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoCriado), ex.getMessage());
            throw ex;
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.aguardando-pagamento}")
    public void aguardandoPagamento(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.aguardandoPagamento(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO, filaPedidoAguardandoPagamento);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoAguardandoPagamento), ex.getMessage());
            throw ex;
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.pago}")
    public void pago(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.pago(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_PAGO, filaPedidoPago);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoPago), ex.getMessage());
            PagamentoPedidoRequest pagamentoPedidoRequest = new PagamentoPedidoRequest(pedidoId);
            String req = new Gson().toJson(pagamentoPedidoRequest);
            sqsTemplate.send(filaPagamentoErroPagamentoPedido, req);
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.recebido}")
    public void recebido(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.recebido(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_RECEBIDO, filaPedidoRecebido);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoRecebido), ex.getMessage());
            CozinhaPedidoRequest cozinhaPedidoRequest = new CozinhaPedidoRequest(pedidoId);
            String req = new Gson().toJson(cozinhaPedidoRequest);
            sqsTemplate.send(filaCozinhaErroPedidoRecebido, req);
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.em-preparo}")
    public void emPreparo(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.emPreparo(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_EM_PREPARO, filaPedidoEmPreparo);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoEmPreparo), ex.getMessage());
            CozinhaPedidoRequest cozinhaPedidoRequest = new CozinhaPedidoRequest(pedidoId);
            String req = new Gson().toJson(cozinhaPedidoRequest);
            sqsTemplate.send(filaCozinhaErroPedidoEmPreparo, req);
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.pronto}")
    public void pronto(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.pronto(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_PRONTO, filaPedidoPronto);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoPronto), ex.getMessage());
            CozinhaPedidoRequest cozinhaPedidoRequest = new CozinhaPedidoRequest(pedidoId);
            String req = new Gson().toJson(cozinhaPedidoRequest);
            sqsTemplate.send(filaCozinhaErroPedidoPronto, req);
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.finalizado}")
    public void finalizado(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.finalizado(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_FINALIZADO, filaPedidoFinalizado);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoFinalizado), ex.getMessage());
            CozinhaPedidoRequest cozinhaPedidoRequest = new CozinhaPedidoRequest(pedidoId);
            String req = new Gson().toJson(cozinhaPedidoRequest);
            sqsTemplate.send(filaCozinhaErroPedidoFinalizado, req);
        }
    }

    @Override
    @SqsListener("${sqs.queue.pedido.cancelado}")
    public void cancelado(String mensagem) {
        Long pedidoId = pedidoIdFromJson(mensagem);
        try {
            alterarPedidoStatusInputPort.cancelado(pedidoId);
            String mensagemLog = String.format(MENSAGEM_SUCESSO, pedidoId, STATUS_PEDIDO_CANCELADO, filaPedidoCancelado);
            log.info(mensagemLog);
        } catch (Exception ex) {
            log.error(String.format(MENSAGEM_ERRO, pedidoId, filaPedidoCancelado), ex.getMessage());
            PagamentoCancelarPedidoRequest pagamentoCancelarPedidoRequest = new PagamentoCancelarPedidoRequest(pedidoId);
            String req = new Gson().toJson(pagamentoCancelarPedidoRequest);
            sqsTemplate.send(filaPagamentoErroPedidoCancelado, req);
        }
    }

    private Long pedidoIdFromJson(String mensagem) {
        AlterarStatusPedidoResponse alterarStatusPedidoResponse = new Gson().fromJson(mensagem, AlterarStatusPedidoResponse.class);
        return  alterarStatusPedidoResponse.getPedidoId();
    }
}
