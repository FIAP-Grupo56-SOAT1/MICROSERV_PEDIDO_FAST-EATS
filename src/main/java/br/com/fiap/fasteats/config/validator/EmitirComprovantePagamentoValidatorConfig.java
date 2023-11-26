package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.StatusPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.EmitirComprovantePagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmitirComprovantePagamentoValidatorConfig {
    @Bean
    public EmitirComprovantePagamentoValidatorImpl emitirComprovantePagamentoValidatorImpl(PedidoUseCase pedidoUseCase,
                                                                                           PagamentoUseCase pagamentoUseCase,
                                                                                           StatusPedidoUseCase statusPedidoUseCase,
                                                                                           StatusPagamentoUseCase statusPagamentoUseCase) {
        return new EmitirComprovantePagamentoValidatorImpl(pedidoUseCase, pagamentoUseCase,
                                                           statusPedidoUseCase, statusPagamentoUseCase);
    }
}
