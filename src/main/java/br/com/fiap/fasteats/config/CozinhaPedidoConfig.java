package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CozinhaPedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CozinhaPedidoConfig {
    @Bean
    public CozinhaPedidoUseCase cozinhaPedidoUseCase(AlterarPedidoStatusUseCase alterarPedidoStatusUseCase) {
        return new CozinhaPedidoUseCase(alterarPedidoStatusUseCase);
    }
}
