package br.com.fiap.fasteats.dataprovider.client.exeption;

public class AwsSQSException extends RuntimeException {
    public AwsSQSException(String mensagem) {
        super(mensagem);
    }
}
