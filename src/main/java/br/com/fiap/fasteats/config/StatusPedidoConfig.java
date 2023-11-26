package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.dataprovider.StatusPedidoAdapter;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatusPedidoConfig {
    @Bean
    public StatusPedidoUseCase crudStatusPedidoUseCase(StatusPedidoAdapter crudStatusPedidoAdapter) {
        return new StatusPedidoUseCase(crudStatusPedidoAdapter);
    }
}
