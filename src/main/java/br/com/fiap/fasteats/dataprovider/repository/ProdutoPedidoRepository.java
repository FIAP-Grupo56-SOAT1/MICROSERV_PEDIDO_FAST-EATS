package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedidoEntity, ProdutoPedidoIdEntity> {

    @Modifying
    @Query("delete from ProdutoDeUmPedido p where p.id.idPedido=:idPedido and p.id.idProduto=:idProduto")
    void removeByIdPedidoAndIdProduto(@Param("idPedido") Long idPedido, @Param("idProduto") Long idProduto);
}
