package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelarPedidoConfig {
    @Bean
    public CancelarPedidoUseCase cancelarPedidoUseCase(PedidoUseCase pedidoUseCase,
                                                       AlterarPedidoStatusUseCase alterarPedidoStatusUseCase) {
        return new CancelarPedidoUseCase(pedidoUseCase, alterarPedidoStatusUseCase);
    }
}
