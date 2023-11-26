package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ProdutoPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.ProdutoUseCase;
import br.com.fiap.fasteats.core.validator.impl.PedidoValidatorImpl;
import br.com.fiap.fasteats.core.validator.impl.ProdutoPedidoValidatorImpl;
import br.com.fiap.fasteats.dataprovider.ProdutoPedidoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoPedidoConfig {
    @Bean
    public ProdutoPedidoUseCase produtoPedidoUseCase(PedidoUseCase pedidoUseCase, ProdutoPedidoAdapter produtoPedidoAdapter, ProdutoUseCase produtoUseCase,
                                                     PedidoValidatorImpl pedidoValidatorImpl, ProdutoPedidoValidatorImpl produtoPedidoValidatorImpl) {
        return new ProdutoPedidoUseCase(pedidoUseCase, produtoPedidoAdapter,
                                        produtoUseCase, pedidoValidatorImpl,
                                        produtoPedidoValidatorImpl);
    }
}
