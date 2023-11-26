package br.com.fiap.fasteats.core.domain.exception;

public class CategoriaNotFound extends RuntimeException{
    public CategoriaNotFound(String mensagem) {
            super(mensagem);
        }
}
