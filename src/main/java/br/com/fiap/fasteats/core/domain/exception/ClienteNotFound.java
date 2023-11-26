package br.com.fiap.fasteats.core.domain.exception;

public class ClienteNotFound extends RuntimeException {

    public ClienteNotFound(String mensagem) {
        super(mensagem);
    }

}
