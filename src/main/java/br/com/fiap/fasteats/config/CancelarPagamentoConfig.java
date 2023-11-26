package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.AlterarPagamentoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.CancelarPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.CancelarPagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelarPagamentoConfig {
    @Bean
    public CancelarPagamentoUseCase cancelarPagamentoUseCase(PagamentoUseCase pagamentoUseCase,
                                                             AlterarPagamentoStatusUseCase alterarPagamentoStatusUseCase,
                                                             CancelarPedidoUseCase cancelarPedidoUseCase,
                                                             CancelarPagamentoValidatorImpl cancelarPagamentoValidatorImpl) {
        return new CancelarPagamentoUseCase(pagamentoUseCase, alterarPagamentoStatusUseCase,
                                            cancelarPedidoUseCase, cancelarPagamentoValidatorImpl);
    }
}
