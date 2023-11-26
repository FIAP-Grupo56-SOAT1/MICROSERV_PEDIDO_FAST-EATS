package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.core.usecase.impl.pagamento.FormaPagamentoUseCase;
import br.com.fiap.fasteats.dataprovider.FormaPagamentoAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormaPagamentoConfig {
    @Bean
    public FormaPagamentoUseCase formaPagamentoUseCase(FormaPagamentoAdapter crudFormaPagamentoAdapter) {
        return new FormaPagamentoUseCase(crudFormaPagamentoAdapter);
    }
}
