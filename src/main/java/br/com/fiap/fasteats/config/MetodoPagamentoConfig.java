package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.FormaPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.MetodoPagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetodoPagamentoConfig {
    @Bean
    public MetodoPagamentoUseCase metodoPagamentoUseCase(PagamentoUseCase pagamentoUseCase,
                                                         FormaPagamentoUseCase formaPagamentoUseCase) {
        return new MetodoPagamentoUseCase(pagamentoUseCase, formaPagamentoUseCase);
    }
}
