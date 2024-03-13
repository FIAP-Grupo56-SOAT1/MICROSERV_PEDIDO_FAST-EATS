package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import br.com.fiap.fasteats.dataprovider.CancelarPedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelarPedidoConfig {
    @Bean
    public CancelarPedidoUseCase cancelarPedidoUseCase(CancelarPedidoAdapter cancelarPedidoAdapter,
                                                       AlterarPedidoStatusValidator alterarPedidoStatusValidator) {
        return new CancelarPedidoUseCase(cancelarPedidoAdapter, alterarPedidoStatusValidator);
    }
}
