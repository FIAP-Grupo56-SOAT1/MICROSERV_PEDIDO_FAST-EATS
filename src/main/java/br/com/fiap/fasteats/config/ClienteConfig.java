package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.dataprovider.ClienteAdapter;
import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteConfig {
    @Bean
    public ClienteUseCase crudClienteUseCase(ClienteAdapter crudClienteAdapter) {
        return new ClienteUseCase(crudClienteAdapter);
    }
}
