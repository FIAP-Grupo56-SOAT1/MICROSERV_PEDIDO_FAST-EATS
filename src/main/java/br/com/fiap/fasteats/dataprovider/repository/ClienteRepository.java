package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
}