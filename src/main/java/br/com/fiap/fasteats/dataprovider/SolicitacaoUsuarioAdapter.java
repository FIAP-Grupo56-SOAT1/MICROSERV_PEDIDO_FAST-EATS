package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.dataprovider.repository.SolicitacaoUsuarioRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.SolicitacaoUsuarioEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.SolicitacaoUsuarioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoUsuarioAdapter implements SolicitacaoUsuarioOutputPort {
    private final SolicitacaoUsuarioRepository solicitacaoUsuarioRepository;
    private final SolicitacaoUsuarioEntityMapper solicitacaoUsuarioEntityMapper;

    @Override
    public SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario) {
        SolicitacaoUsuarioEntity solicitacaoUsuarioEntity = solicitacaoUsuarioEntityMapper.toSolicitacaoUsuarioEntity(solicitacaoUsuario);
        SolicitacaoUsuarioEntity solicitacaoUsuarioEntitySalvo = solicitacaoUsuarioRepository.save(solicitacaoUsuarioEntity);
        return solicitacaoUsuarioEntityMapper.toSolicitacaoUsuario(solicitacaoUsuarioEntitySalvo);
    }
}
