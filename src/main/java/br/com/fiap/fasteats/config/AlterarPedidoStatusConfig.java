package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.AlterarPedidoStatusValidatorImpl;
import br.com.fiap.fasteats.dataprovider.PagamentoAdapter;
import br.com.fiap.fasteats.dataprovider.PedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlterarPedidoStatusConfig {
    @Bean
    public AlterarPedidoStatusUseCase alterarPedidoStatusUseCase(
            AlterarPedidoStatusValidatorImpl alterarPedidoStatusValidatorImpl,
            StatusPedidoUseCase statusPedidoUseCase,
            PedidoAdapter pedidoAdapter,
            PagamentoAdapter pagamentoAdapter) {
        return new AlterarPedidoStatusUseCase(alterarPedidoStatusValidatorImpl, statusPedidoUseCase,
                pedidoAdapter, pagamentoAdapter);
    }
}
