package br.com.fiap.fasteats.dataprovider.repository;

import br.com.fiap.fasteats.dataprovider.repository.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    @Query("select s from Categoria s where s.nome = upper(:nome)")
    List<CategoriaEntity> findByNome (@Param("nome") String nome);
}
