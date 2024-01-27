package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.AndamentoPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AndamentoPedidoInputPort;
import br.com.fiap.fasteats.dataprovider.PedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AndamentoPedidoConfig {
    @Bean
    public AndamentoPedidoInputPort andamentoPedidoUseCase(PedidoAdapter pedidoAdapter) {
        return new AndamentoPedidoUseCase(pedidoAdapter);
    }
}
