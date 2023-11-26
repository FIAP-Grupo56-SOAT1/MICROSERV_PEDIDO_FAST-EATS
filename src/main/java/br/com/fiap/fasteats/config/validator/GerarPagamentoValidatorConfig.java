package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.FormaPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.GerarPagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GerarPagamentoValidatorConfig {
    @Bean
    public GerarPagamentoValidatorImpl gerarPagamentoValidatorImpl(StatusPedidoUseCase statusPedidoUseCase,
                                                                   FormaPagamentoUseCase formaPagamentoUseCase,
                                                                   PedidoUseCase pedidoUseCase) {
        return new GerarPagamentoValidatorImpl(statusPedidoUseCase, formaPagamentoUseCase, pedidoUseCase);
    }
}
