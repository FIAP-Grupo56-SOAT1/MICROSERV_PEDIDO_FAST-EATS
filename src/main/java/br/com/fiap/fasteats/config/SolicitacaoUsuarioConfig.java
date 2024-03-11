package br.com.fiap.fasteats.config;


import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import br.com.fiap.fasteats.core.usecase.impl.SolicitacaoUsuarioUseCase;
import br.com.fiap.fasteats.dataprovider.ClienteAdapter;
import br.com.fiap.fasteats.dataprovider.SolicitacaoUsuarioAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitacaoUsuarioConfig {

    @Bean
    public SolicitacaoUsuarioUseCase crudsolicitacaoUsuarioUseCase(SolicitacaoUsuarioAdapter solicitacaoUsuarioAdapter, ClienteAdapter crudClienteAdapter , ClienteUseCase clienteUseCase) {
        return new SolicitacaoUsuarioUseCase(solicitacaoUsuarioAdapter,crudClienteAdapter ,clienteUseCase);
    }
}
