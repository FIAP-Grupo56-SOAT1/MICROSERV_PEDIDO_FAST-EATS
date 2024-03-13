package br.com.fiap.fasteats.core.validator;


public interface AlterarPedidoStatusValidator {
    void validarCriado(Long pedidoId);

    void validarAguardandoPagamento(Long pedidoId);

    void validarPago(Long pedidoId);

    void validarRecebido(Long pedidoId);

    void validarEmPreparo(Long pedidoId);

    void validarPronto(Long pedidoId);

    void validarFinalizado(Long pedidoId);

    void validarAguardandoCancelamento(Long pedidoId);

    void validarCancelado(Long pedidoId);
}
