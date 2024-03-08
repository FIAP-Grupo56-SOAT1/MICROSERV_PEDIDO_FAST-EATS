package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.SolicitacaoUsuarioOutputPort;
import br.com.fiap.fasteats.core.domain.model.SolicitacaoUsuario;
import br.com.fiap.fasteats.dataprovider.repository.SolicitacaoUsuarioRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.SolicitacaoUsuarioEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.SolicitacaoUsuarioEntityMapper;

public class SolicitacaoUsuarioAdapter implements SolicitacaoUsuarioOutputPort {

    private final SolicitacaoUsuarioRepository pedidoRepository;
    private final SolicitacaoUsuarioEntityMapper pedidoEntityMapper;

    public SolicitacaoUsuarioAdapter(SolicitacaoUsuarioRepository pedidoRepository, SolicitacaoUsuarioEntityMapper pedidoEntityMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoEntityMapper = pedidoEntityMapper;
    }

    @Override
    public SolicitacaoUsuario salvar(SolicitacaoUsuario solicitacaoUsuario) {
        SolicitacaoUsuarioEntity solicitacaoUsuarioEntity = pedidoEntityMapper.toSolicitacaoUsuarioEntity(solicitacaoUsuario);
        SolicitacaoUsuarioEntity solicitacaoUsuarioEntitySalvo = pedidoRepository.saveAndFlush(solicitacaoUsuarioEntity);
        pedidoRepository.flush();
        return pedidoEntityMapper.toSolicitacaoUsuario(solicitacaoUsuarioEntitySalvo);
    }
}
