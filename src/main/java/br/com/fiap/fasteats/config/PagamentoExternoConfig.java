package br.com.fiap.fasteats.config;


import br.com.fiap.fasteats.core.usecase.impl.pagamento.AlterarPagamentoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.EmitirComprovantePagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoExternoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.validator.impl.CancelarPagamentoValidatorImpl;
import br.com.fiap.fasteats.dataprovider.PagamentoExternoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoExternoConfig {
    @Bean
    public PagamentoExternoUseCase pagamentoExternoUseCase(PagamentoUseCase pagamentoUseCase,
                                                           PagamentoExternoAdapter pagamentoExternoAdapter,
                                                           EmitirComprovantePagamentoUseCase emitirComprovantePagamentoUseCase,
                                                           AlterarPagamentoStatusUseCase alterarPagamentoStatusUseCase,
                                                           AlterarPedidoStatusUseCase alterarPedidoStatusUseCase,
                                                           CancelarPagamentoValidatorImpl cancelarPagamentoValidatorImpl) {
        return new PagamentoExternoUseCase(pagamentoUseCase, pagamentoExternoAdapter,
                                           emitirComprovantePagamentoUseCase, alterarPagamentoStatusUseCase,
                                           alterarPedidoStatusUseCase, cancelarPagamentoValidatorImpl);
    }
}
