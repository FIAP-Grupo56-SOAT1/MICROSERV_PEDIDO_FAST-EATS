package br.com.fiap.fasteats.core.domain.exception;

public class ValidarValorException extends IllegalArgumentException{
    public ValidarValorException(String mensagem) {
        super(mensagem);
    }
}
