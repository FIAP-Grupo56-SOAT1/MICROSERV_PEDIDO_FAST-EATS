package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.PedidoValidatorImpl;
import br.com.fiap.fasteats.dataprovider.PagamentoAdapter;
import br.com.fiap.fasteats.dataprovider.PedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfig {
    @Bean
    public PedidoUseCase crudPedidoUseCase(PedidoAdapter pedidoAdapter, ClienteUseCase crudClienteUseCase,
            StatusPedidoUseCase statusPedidoUseCase, PagamentoAdapter pagamentoAdapter,
            PedidoValidatorImpl pedidoValidatorImpl) {
        return new PedidoUseCase(pedidoAdapter, crudClienteUseCase,
                statusPedidoUseCase, pagamentoAdapter,
                pedidoValidatorImpl);
    }
}
