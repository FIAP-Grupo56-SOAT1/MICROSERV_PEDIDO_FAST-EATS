package br.com.fiap.fasteats.dataprovider.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.fiap.fasteats.dataprovider.repository.entity.SolicitacaoUsuarioEntity;

@Repository
public interface SolicitacaoUsuarioRepository extends JpaRepository<SolicitacaoUsuarioEntity, Long> {
}
