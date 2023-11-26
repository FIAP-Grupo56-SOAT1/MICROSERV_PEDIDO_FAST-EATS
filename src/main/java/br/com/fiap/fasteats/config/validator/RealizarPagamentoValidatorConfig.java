package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.RealizarPagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealizarPagamentoValidatorConfig {
    @Bean
    public RealizarPagamentoValidatorImpl realizarPagamentoValidatorImpl(PedidoUseCase pedidoUseCase,
                                                                     StatusPedidoUseCase statusPedidoUseCase){
        return new RealizarPagamentoValidatorImpl(pedidoUseCase, statusPedidoUseCase);
    }
}
