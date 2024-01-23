package br.com.fiap.fasteats.dataprovider.client.exeption;

public class MicroservicoPagamentoException extends IllegalArgumentException {
    public MicroservicoPagamentoException(String mensagem) {
        super(mensagem);
    }
}
