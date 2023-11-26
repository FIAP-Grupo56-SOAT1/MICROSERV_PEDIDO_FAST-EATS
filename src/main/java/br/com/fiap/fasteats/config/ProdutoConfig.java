package br.com.fiap.fasteats.config;

import br.com.fiap.fasteats.dataprovider.CategoriaAdapter;
import br.com.fiap.fasteats.dataprovider.ProdutoAdapter;
import br.com.fiap.fasteats.core.usecase.impl.ProdutoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoConfig {
    @Bean
    public ProdutoUseCase crudProdutoUseCase(ProdutoAdapter crudProdutoAdapter, CategoriaAdapter crudCategoriaOutputPort) {
        return new ProdutoUseCase(crudProdutoAdapter, crudCategoriaOutputPort);
    }
}
