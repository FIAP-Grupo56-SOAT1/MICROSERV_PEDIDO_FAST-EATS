package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    @Query("select s from Produto s where upper(s.nome) = upper(:nome)")
    List<ProdutoEntity> findByNome (@Param("nome") String nome);
    @Query("select s from Produto s where s.categoriaEntity.id = :categoriaId")
    List<ProdutoEntity> findByCategoria (@Param("categoriaId") Long categoriaId);
}
