package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("SELECT p FROM PedidoEntity p JOIN FETCH p.statusEntity s " +
            "WHERE p.dataHoraRecebimento IS NOT NULL " +
            "AND   p.dataHoraFinalizado IS NULL " +
            "ORDER BY CASE s.nome " +
            "    WHEN 'PRONTO'THEN 1 " +
            "    WHEN 'EM_PREPARO' THEN 2 " +
            "    WHEN 'RECEBIDO' THEN 3 " +
            "    ELSE 4 " +
            "END, p.id")
    List<PedidoEntity> listarPedidosAndamento();

    @Query("SELECT p FROM PedidoEntity p " +
            "WHERE p.id = :id " +
            "AND p.dataHoraRecebimento IS NOT NULL " +
            "AND p.dataHoraFinalizado IS NULL")
    List<PedidoEntity> consultarPedidoAndamento(@Param("id") Long id);
}
