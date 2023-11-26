package br.com.fiap.fasteats.core.domain.exception;

public class ProdutoNotFound extends RuntimeException {

    public ProdutoNotFound(String message) {
        super(message);
    }

}
