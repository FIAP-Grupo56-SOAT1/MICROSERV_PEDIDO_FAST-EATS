package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.GerarPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ConfirmarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.PedidoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmarPedidoConfig {
    @Bean
    public ConfirmarPedidoUseCase confirmarPedidoUseCase(PedidoUseCase pedidoUseCase,
                                                         AlterarPedidoStatusUseCase alterarPedidoStatusUseCase,
                                                         GerarPagamentoUseCase gerarPagamentoUseCase,
                                                         PedidoValidatorImpl pedidoValidatorImpl) {
        return new ConfirmarPedidoUseCase(pedidoUseCase, alterarPedidoStatusUseCase,
                                          gerarPagamentoUseCase, pedidoValidatorImpl);
    }
}
