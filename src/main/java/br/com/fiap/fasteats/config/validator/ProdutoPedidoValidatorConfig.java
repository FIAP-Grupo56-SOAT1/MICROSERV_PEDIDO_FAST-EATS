package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.impl.ProdutoPedidoValidatorImpl;
import br.com.fiap.fasteats.dataprovider.ProdutoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoPedidoValidatorConfig {
    @Bean
    public ProdutoPedidoValidatorImpl produtoPedidoValidatorImpl(ProdutoAdapter produtoAdapter,
                                                                 StatusPedidoInputPort statusPedidoInputPort){
        return new ProdutoPedidoValidatorImpl(produtoAdapter, statusPedidoInputPort);
    }
}
