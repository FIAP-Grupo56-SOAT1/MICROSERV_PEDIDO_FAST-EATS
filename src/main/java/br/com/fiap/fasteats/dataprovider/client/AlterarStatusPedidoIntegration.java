package br.com.fiap.fasteats.dataprovider.client;

public interface AlterarStatusPedidoIntegration {
    void criado(String mensagem);
    void aguardandoPagamento(String mensagem);
    void pago(String mensagem);
    void recebido(String mensagem);
    void emPreparo(String mensagem);
    void pronto(String mensagem);
    void finalizado(String mensagem);
    void cancelado(String mensagem);
}
