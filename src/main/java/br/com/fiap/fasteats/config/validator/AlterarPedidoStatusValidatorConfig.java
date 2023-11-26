package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.AlterarPedidoStatusValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlterarPedidoStatusValidatorConfig {
    @Bean
    public AlterarPedidoStatusValidatorImpl alterarPedidoStatusValidatorImpl(PedidoUseCase pedidoUseCase,
                                                                             StatusPedidoUseCase statusPedidoUseCase) {
        return new AlterarPedidoStatusValidatorImpl(pedidoUseCase, statusPedidoUseCase);
    }
}
