package br.com.fiap.fasteats.core.domain.exception;

public class ValidarClienteException extends IllegalArgumentException {
    public ValidarClienteException(String mensagem) {
        super(mensagem);
    }
}
