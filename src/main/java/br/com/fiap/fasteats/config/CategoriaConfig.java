package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.dataprovider.CategoriaAdapter;
import br.com.fiap.fasteats.core.usecase.impl.CategoriaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriaConfig {
    @Bean
    public CategoriaUseCase crudCategoriaUseCase(CategoriaAdapter crudCategoriaAdapter) {
        return new CategoriaUseCase(crudCategoriaAdapter);
    }
}
