package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.PedidoValidatorImpl;
import br.com.fiap.fasteats.dataprovider.PedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoValidatorConfig {
    @Bean
    public PedidoValidatorImpl pedidoValidatorImpl(StatusPedidoUseCase statusPedidoUseCase,
                                                   PedidoAdapter pedidoAdapter){
        return new PedidoValidatorImpl(statusPedidoUseCase, pedidoAdapter);
    }
}
