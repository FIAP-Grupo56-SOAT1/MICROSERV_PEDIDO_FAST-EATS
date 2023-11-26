package br.com.fiap.fasteats.config.validator;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.PagamentoUseCase;
import br.com.fiap.fasteats.core.usecase.impl.pagamento.StatusPagamentoUseCase;
import br.com.fiap.fasteats.core.validator.impl.AlterarPagamentoStatusValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlterarPagamentoStatusValidatorConfig {
    @Bean
    public AlterarPagamentoStatusValidatorImpl alterarPagamentoStatusValidatorImpl(PagamentoUseCase pagamentoUseCase,
                                                                                   StatusPagamentoUseCase statusPagamentoUseCase) {
        return new AlterarPagamentoStatusValidatorImpl(pagamentoUseCase, statusPagamentoUseCase);
    }
}
