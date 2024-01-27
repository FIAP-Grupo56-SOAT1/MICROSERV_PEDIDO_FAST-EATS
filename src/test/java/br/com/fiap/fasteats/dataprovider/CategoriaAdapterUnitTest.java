package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.dataprovider.repository.CategoriaRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.CategoriaEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.CategoriaEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaAdapterUnitTest {
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CategoriaEntityMapper categoriaEntityMapper;
    @InjectMocks
    private CategoriaAdapter categoriaAdapter;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void criar() {
        // Arrange
        Categoria categoria = getCategoria(1L, "TESTE");
        CategoriaEntity categoriaEntity = getCategoriaEntity(1L, "TESTE");

        when(categoriaEntityMapper.toCategoriaEntity(categoria)).thenReturn(categoriaEntity);
        when(categoriaRepository.save(categoriaEntity)).thenReturn(categoriaEntity);
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        // Act
        Categoria categoriaSalva = categoriaAdapter.criar(categoria);

        // Assert
        assertEquals(categoria.getId(), categoriaSalva.getId());
        assertEquals(categoria.getNome(), categoriaSalva.getNome());
        verify(categoriaEntityMapper).toCategoriaEntity(categoria);
        verify(categoriaRepository).save(categoriaEntity);
        verify(categoriaEntityMapper).toCategoria(categoriaEntity);
    }

    @Test
    void consultar() {
        // Arrange
        Categoria categoria = getCategoria(1L, "TESTE");
        CategoriaEntity categoriaEntity = getCategoriaEntity(1L, "TESTE");

        when(categoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(categoriaEntity));
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        // Act
        Categoria categoriaConsultada = categoriaAdapter.consultar(1L).get();

        // Assert
        assertEquals(categoria.getId(), categoriaConsultada.getId());
        assertEquals(categoria.getNome(), categoriaConsultada.getNome());
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void atualizar() {
        // Arrange
        Categoria categoria = getCategoria(1L, "TESTE");
        CategoriaEntity categoriaEntity = getCategoriaEntity(1L, "TESTE");

        when(categoriaEntityMapper.toCategoriaEntity(categoria)).thenReturn(categoriaEntity);
        when(categoriaRepository.save(categoriaEntity)).thenReturn(categoriaEntity);
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        // Act
        Categoria categoriaAtualizada = categoriaAdapter.atualizar(categoria);

        // Assert
        assertEquals(categoria.getId(), categoriaAtualizada.getId());
        assertEquals(categoria.getNome(), categoriaAtualizada.getNome());
        verify(categoriaEntityMapper).toCategoriaEntity(categoria);
        verify(categoriaRepository).save(categoriaEntity);
        verify(categoriaEntityMapper).toCategoria(categoriaEntity);
    }

    @Test
    void deletar() {
        // Act
        categoriaAdapter.deletar(1L);

        // Assert
        verify(categoriaRepository).deleteById(1L);
    }

    @Test
    void listar() {
        // Arrange
        List<CategoriaEntity> categoriaEntities = List.of(
                getCategoriaEntity(1L, "TESTE"),
                getCategoriaEntity(2L, "TESTE2"));

        List<Categoria> categorias = List.of(
                getCategoria(1L, "TESTE"),
                getCategoria(2L, "TESTE2"));

        when(categoriaRepository.findAll()).thenReturn(categoriaEntities);
        when(categoriaEntityMapper.toCategoria(categoriaEntities.get(0))).thenReturn(categorias.get(0));
        when(categoriaEntityMapper.toCategoria(categoriaEntities.get(1))).thenReturn(categorias.get(1));

        // Act
        Optional<List<Categoria>> categoriasListadas = categoriaAdapter.listar();

        // Assert
        assertNotNull(categoriasListadas);
        assertEquals(categoriasListadas.get().size(), 2);
        verify(categoriaRepository).findAll();
        verify(categoriaEntityMapper).toCategoria(categoriaEntities.get(0));
    }

    @Test
    void consultarPorNome() {
        // Arrange
        Categoria categoria = getCategoria(1L, "TESTE");
        CategoriaEntity categoriaEntity = getCategoriaEntity(1L, "TESTE");

        when(categoriaRepository.findByNome("TESTE")).thenReturn(List.of(categoriaEntity));
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        // Act
        Optional<Categoria> categoriaConsultada = categoriaAdapter.consultarPorNome("TESTE");

        // Assert
        assertNotNull(categoriaConsultada);
        assertEquals(categoria, toCategoria(categoriaEntity));
        assertEquals(categoriaConsultada.get().getId(), categoria.getId());
        assertEquals(categoriaConsultada.get().getNome(), categoria.getNome());
        verify(categoriaRepository).findByNome("TESTE");
        verify(categoriaEntityMapper).toCategoria(categoriaEntity);
    }

    private Categoria getCategoria(Long id, String nome) {
        return new Categoria(id, nome, "TESTE", true);
    }

    private CategoriaEntity getCategoriaEntity(Long id, String nome) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(id);
        categoriaEntity.setNome(nome);
        categoriaEntity.setDescricao("TESTE");
        categoriaEntity.setAtivo(true);
        return categoriaEntity;
    }

    private Categoria toCategoria(CategoriaEntity categoriaEntity) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaEntity.getId());
        categoria.setNome(categoriaEntity.getNome());
        categoria.setDescricao(categoriaEntity.getDescricao());
        categoria.setAtivo(categoriaEntity.getAtivo());
        return categoria;
    }
}