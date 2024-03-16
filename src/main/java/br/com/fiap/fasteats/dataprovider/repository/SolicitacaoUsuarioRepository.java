package br.com.fiap.fasteats.dataprovider.repository;


import br.com.fiap.fasteats.dataprovider.repository.entity.SolicitacaoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoUsuarioRepository extends JpaRepository<SolicitacaoUsuarioEntity, Long> {
}
