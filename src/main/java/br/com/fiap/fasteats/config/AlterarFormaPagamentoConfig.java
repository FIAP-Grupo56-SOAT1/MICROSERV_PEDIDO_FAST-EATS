package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.FormaPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.AlterarFormaPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.GerarPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlterarFormaPagamentoConfig {
    @Bean
    public AlterarFormaPagamentoUseCase alterarFormaPagamentoUseCase(PagamentoUseCase pagamentoUseCase,
                                                                     FormaPagamentoUseCase formaPagamentoUseCase,
                                                                     GerarPagamentoUseCase gerarPagamentoUseCase) {
        return new AlterarFormaPagamentoUseCase(pagamentoUseCase, formaPagamentoUseCase, gerarPagamentoUseCase);
    }
}
