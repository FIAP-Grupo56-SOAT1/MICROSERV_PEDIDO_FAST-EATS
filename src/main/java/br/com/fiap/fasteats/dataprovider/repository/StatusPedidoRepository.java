package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.StatusPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusPedidoRepository extends JpaRepository<StatusPedidoEntity, Long> {
    @Query("select s from StatusPedido s where s.nome = upper(:nome)")
    List<StatusPedidoEntity> findByNome (@Param("nome") String nome);
}