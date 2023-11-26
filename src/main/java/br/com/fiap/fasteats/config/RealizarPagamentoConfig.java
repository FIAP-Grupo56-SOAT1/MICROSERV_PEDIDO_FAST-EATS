package br.com.fiap.fasteats.config;


import br.com.fiap.fasteats.core.usecase.impl.pagamento.*;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.validator.impl.RealizarPagamentoValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RealizarPagamentoConfig {
    @Bean
    public RealizarPagamentoUseCase realizarPagamentoUseCase(PedidoUseCase pedidoUseCase,
                                                             FormaPagamentoUseCase formaPagamentoUseCase,
                                                             AlterarPagamentoStatusUseCase alterarPagamentoStatusUseCase,
                                                             AlterarPedidoStatusUseCase alterarPedidoStatusUseCase,
                                                             PagamentoUseCase pagamentoUseCase,
                                                             EmitirComprovantePagamentoUseCase emitirComprovantePagamentoUseCase,
                                                             RealizarPagamentoValidatorImpl realizarPagamentoValidatorImpl) {

        return new RealizarPagamentoUseCase(pedidoUseCase, formaPagamentoUseCase, alterarPagamentoStatusUseCase,
                                            alterarPedidoStatusUseCase, pagamentoUseCase, emitirComprovantePagamentoUseCase,
                                            realizarPagamentoValidatorImpl);
    }
}
