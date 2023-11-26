package br.com.fiap.fasteats.core.usecase.impl.unit;

import br.com.fiap.fasteats.core.domain.exception.CategoriaNotFound;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.usecase.impl.CategoriaUseCase;
import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CrudCategoriaUseCaseTest")
class CategoriaUseCaseUnitTest {
    private CategoriaUseCase categoriaUseCase;

    @Mock
    private CategoriaOutputPort categoriaOutputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoriaUseCase = new CategoriaUseCase(categoriaOutputPort);
    }

    @Test
    @DisplayName("Criar Categoria")
    void testCriar() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria 1");

        when(categoriaOutputPort.criar(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaUseCase.criar(categoria);

        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).criar(categoria);
    }

    @Test
    @DisplayName("Consultar Categoria")
    void testeConsultar() {
        long id = 1L;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Categoria 1");

        when(categoriaOutputPort.consultar(id)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaUseCase.consultar(id);

        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).consultar(id);
    }

    @Test
    @DisplayName("Consultar Categoria - Categoria Não Encontrada")
    void testeConsultar_CategoriaNaoEncontrada() {
        long id = 1L;

        when(categoriaOutputPort.consultar(id)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.consultar(id));
        verify(categoriaOutputPort, times(1)).consultar(id);
    }

    @Test
    @DisplayName("Atualizar Categoria")
    void testeAtualizar() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria 1");

        when(categoriaOutputPort.atualizar(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaUseCase.atualizar(categoria);

        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).atualizar(categoria);
    }

    @Test
    @DisplayName("Atualizar Categoria - Ativo Nulo")
    void testeAtualizar_AtivoNulo() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria 1");
        categoria.setAtivo(null);

        when(categoriaOutputPort.atualizar(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaUseCase.atualizar(categoria);

        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(categoriaOutputPort, times(1)).atualizar(categoria);
    }

    @Test
    @DisplayName("Deletar Categoria")
    void testeDeletar() {
        long id = 1L;

        categoriaUseCase.deletar(id);

        verify(categoriaOutputPort, times(1)).deletar(id);
    }

    @Test
    @DisplayName("Listar Categorias")
    void testeListar() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1L, "Categoria 1", "Descrição 1", true));
        categorias.add(new Categoria(2L, "Categoria 2", "Descrição 2", true));

        when(categoriaOutputPort.listar()).thenReturn(Optional.of(categorias));

        List<Categoria> resultado = categoriaUseCase.listar();

        assertNotNull(resultado);
        assertEquals(categorias.size(), resultado.size());
        assertEquals(categorias.get(0).getId(), resultado.get(0).getId());
        assertEquals(categorias.get(0).getNome(), resultado.get(0).getNome());
        assertEquals(categorias.get(1).getId(), resultado.get(1).getId());
        assertEquals(categorias.get(1).getNome(), resultado.get(1).getNome());
        verify(categoriaOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Listar Categorias - Nenhum Registro Encontrado")
    void testeListar_NenhumRegistroEncontrado() {
        when(categoriaOutputPort.listar()).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.listar());
        verify(categoriaOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Consultar Categoria por Nome")
    void testeConsultarPorNome() {
        String nome = "Categoria 1";
        Categoria categoria = new Categoria(1L, nome, "Descrição 1", true);

        when(categoriaOutputPort.consultarPorNome(nome)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaUseCase.consultarPorNome(nome);

        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).consultarPorNome(nome);
    }

    @Test
    @DisplayName("Consultar Categoria por Nome - Categoria Não Encontrada")
    void testeConsultarPorNome_CategoriaNaoEncontrada() {
        String nome = "Categoria 1";

        when(categoriaOutputPort.consultarPorNome(nome)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.consultarPorNome(nome));
        verify(categoriaOutputPort, times(1)).consultarPorNome(nome);
    }
}
