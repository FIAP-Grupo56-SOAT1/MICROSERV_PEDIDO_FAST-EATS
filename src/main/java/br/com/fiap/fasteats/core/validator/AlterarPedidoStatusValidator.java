package br.com.fiap.fasteats.core.validator;


public interface AlterarPedidoStatusValidator {
    void validarAguardandoPagamento(Long pedidoId);

    void validarPago(Long pedidoId);

    void validarRecebido(Long pedidoId);

    void validarEmPreparo(Long pedidoId);

    void validarPronto(Long pedidoId);

    void validarFinalizado(Long pedidoId);

    void validarCancelado(Long pedidoId);
}
