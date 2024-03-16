package br.com.fiap.fasteats.config;


import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import br.com.fiap.fasteats.core.usecase.impl.SolicitacaoUsuarioUseCase;
import br.com.fiap.fasteats.dataprovider.CriptografiaAdapter;
import br.com.fiap.fasteats.dataprovider.SolicitacaoUsuarioAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitacaoUsuarioConfig {
    @Bean
    public SolicitacaoUsuarioUseCase crudsolicitacaoUsuarioUseCase(SolicitacaoUsuarioAdapter solicitacaoUsuarioAdapter,
                                                                   ClienteUseCase clienteUseCase,
                                                                   CriptografiaAdapter criptografiaAdapter) {
        return new SolicitacaoUsuarioUseCase(solicitacaoUsuarioAdapter, clienteUseCase, criptografiaAdapter);
    }
}
