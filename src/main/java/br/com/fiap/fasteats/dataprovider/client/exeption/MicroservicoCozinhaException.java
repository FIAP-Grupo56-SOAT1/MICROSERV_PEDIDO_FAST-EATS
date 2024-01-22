package br.com.fiap.fasteats.dataprovider.client.exeption;

public class MicroservicoCozinhaException extends IllegalArgumentException {
    public MicroservicoCozinhaException(String mensagem) {
        super(mensagem);
    }
}
