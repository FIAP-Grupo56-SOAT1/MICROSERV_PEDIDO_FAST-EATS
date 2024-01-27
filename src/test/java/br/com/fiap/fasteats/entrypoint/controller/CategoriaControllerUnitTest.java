package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.usecase.CategoriaInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.CategoriaMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.CategoriaRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.CategoriaResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoriaControllerUnitTest {
    @Mock
    private CategoriaInputPort categoriaInputPort;
    @Mock
    private CategoriaMapper categoriaMapper;
    @InjectMocks
    private CategoriaController categoriaController;
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
    void criarCategoria_DeveRetornarCategoriaResponse_QuandoCriacaoBemSucedida() {
        // Arrange
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        Categoria categoria = new Categoria();
        CategoriaResponse categoriaResponse = new CategoriaResponse();

        when(categoriaMapper.toCategoria(categoriaRequest)).thenReturn(categoria);
        when(categoriaInputPort.criar(categoria)).thenReturn(categoria);
        when(categoriaMapper.toCategoriaResponse(categoria)).thenReturn(categoriaResponse);

        // Act
        ResponseEntity<CategoriaResponse> resposta = categoriaController.criarCategoria(categoriaRequest);

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    void atualizarCategoria_DeveRetornarCategoriaResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        Long id = 1L;
        CategoriaRequest categoriaRequest = new CategoriaRequest();
        Categoria categoria = new Categoria();
        CategoriaResponse categoriaResponse = new CategoriaResponse();

        when(categoriaMapper.toCategoria(categoriaRequest)).thenReturn(categoria);
        when(categoriaInputPort.atualizar(categoria)).thenReturn(categoria);
        when(categoriaMapper.toCategoriaResponse(categoria)).thenReturn(categoriaResponse);

        // Act
        ResponseEntity<CategoriaResponse> resposta = categoriaController.atualizarCategoria(id, categoriaRequest);

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    void deletarCategoria_DeveRetornarNoContent_QuandoDelecaoBemSucedida() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> resposta = categoriaController.deletarCategoria(id);

        // Assert
        assertEquals(204, resposta.getStatusCodeValue());
    }

    @Test
    void consultarCategoria_DeveRetornarCategoriaResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long id = 1L;
        Categoria categoria = new Categoria();
        CategoriaResponse categoriaResponse = new CategoriaResponse();

        when(categoriaInputPort.consultar(id)).thenReturn(categoria);
        when(categoriaMapper.toCategoriaResponse(categoria)).thenReturn(categoriaResponse);

        // Act
        ResponseEntity<CategoriaResponse> resposta = categoriaController.consultarCategoria(id);

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarCategoriaPorNome_DeveRetornarCategoriaResponse_QuandoConsultaBemSucedida() {
        // Arrange
        String nome = "Categoria";
        Categoria categoria = new Categoria();
        CategoriaResponse categoriaResponse = new CategoriaResponse();

        when(categoriaInputPort.consultarPorNome(nome)).thenReturn(categoria);
        when(categoriaMapper.toCategoriaResponse(categoria)).thenReturn(categoriaResponse);

        // Act
        ResponseEntity<CategoriaResponse> resposta = categoriaController.consultarCategoriaPorNome(nome);

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
    }

    @Test
    void listarCategorias_DeveRetornarListaCategoriaResponse_QuandoListaNaoVazia() {
        // Arrange
        List<Categoria> categorias = Collections.singletonList(new Categoria());
        List<CategoriaResponse> categoriasResponse = Collections.singletonList(new CategoriaResponse());

        when(categoriaInputPort.listar()).thenReturn(categorias);
        when(categoriaMapper.toCategoriaResponseList(categorias)).thenReturn(categoriasResponse);

        // Act
        ResponseEntity<List<CategoriaResponse>> resposta = categoriaController.listarCategorias();

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void listarCategorias_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<Categoria> categorias = Collections.emptyList();

        when(categoriaInputPort.listar()).thenReturn(categorias);

        // Act
        ResponseEntity<List<CategoriaResponse>> resposta = categoriaController.listarCategorias();

        // Assert
        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }
}