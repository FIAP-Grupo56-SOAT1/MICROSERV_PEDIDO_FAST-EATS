package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.EmitirComprovantePagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CozinhaPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.EmitirComprovantePagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmitirComprovantePagamentoConfig {
    @Bean
    public EmitirComprovantePagamentoUseCase emitirComprovantePagamentoUseCase(PagamentoUseCase pagamentoUseCase,
                                                                               CozinhaPedidoUseCase cozinhaPedidoUseCase,
                                                                               EmitirComprovantePagamentoValidatorImpl emitirComprovantePagamentoValidator) {
        return new EmitirComprovantePagamentoUseCase(pagamentoUseCase, cozinhaPedidoUseCase, emitirComprovantePagamentoValidator);
    }
}
