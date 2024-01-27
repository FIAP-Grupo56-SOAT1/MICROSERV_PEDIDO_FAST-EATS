package br.com.fiap.fasteats.entrypoint.controller;

import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.usecase.ProdutoInputPort;
import br.com.fiap.fasteats.entrypoint.controller.mapper.ProdutoMapper;
import br.com.fiap.fasteats.entrypoint.controller.request.ProdutoRequest;
import br.com.fiap.fasteats.entrypoint.controller.response.ProdutoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerUnitTest {
    @Mock
    private ProdutoInputPort produtoInputPort;
    @Mock
    private ProdutoMapper produtoMapper;
    @InjectMocks
    private ProdutoController produtoController;
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
    void criarProduto_DeveRetornarProdutoResponse_QuandoCriacaoBemSucedida() {
        // Arrange
        ProdutoRequest produtoRequest = new ProdutoRequest();
        Produto produto = new Produto();
        ProdutoResponse produtoResponse = new ProdutoResponse();

        when(produtoMapper.toProduto(produtoRequest)).thenReturn(produto);
        when(produtoInputPort.criar(produto)).thenReturn(produto);
        when(produtoMapper.toProdutoResponse(produto)).thenReturn(produtoResponse);

        // Act
        ResponseEntity<ProdutoResponse> resposta = produtoController.criarProduto(produtoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void atualizarProduto_DeveRetornarProdutoResponse_QuandoAtualizacaoBemSucedida() {
        // Arrange
        Long idProduto = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequest();
        Produto produto = new Produto();
        ProdutoResponse produtoResponse = new ProdutoResponse();

        when(produtoMapper.toProduto(produtoRequest)).thenReturn(produto);
        when(produtoInputPort.atualizar(produto)).thenReturn(produto);
        when(produtoMapper.toProdutoResponse(produto)).thenReturn(produtoResponse);

        // Act
        ResponseEntity<ProdutoResponse> resposta = produtoController.atualizarProduto(idProduto, produtoRequest);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void deletarProduto_DeveRetornarNoContent_QuandoDelecaoBemSucedida() {
        // Arrange
        Long idProduto = 1L;

        // Act
        ResponseEntity<Void> resposta = produtoController.deletarProduto(idProduto);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(produtoInputPort, times(1)).deletar(idProduto);
    }

    @Test
    void consultarProduto_DeveRetornarProdutoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long idProduto = 1L;
        Produto produto = new Produto();
        ProdutoResponse produtoResponse = new ProdutoResponse();

        when(produtoInputPort.consultar(idProduto)).thenReturn(produto);
        when(produtoMapper.toProdutoResponse(produto)).thenReturn(produtoResponse);

        // Act
        ResponseEntity<ProdutoResponse> resposta = produtoController.consultarproduto(idProduto);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void listarProdutos_DeveRetornarListaProdutoResponse_QuandoListaNaoVazia() {
        // Arrange
        List<Produto> produtos = Collections.singletonList(new Produto());
        List<ProdutoResponse> produtosResponse = Collections.singletonList(new ProdutoResponse());

        when(produtoInputPort.listar()).thenReturn(produtos);
        when(produtoMapper.toProdutoResponseList(produtos)).thenReturn(produtosResponse);

        // Act
        ResponseEntity<List<ProdutoResponse>> resposta = produtoController.listarProdutos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void listarProdutos_DeveRetornarListaVazia_QuandoListaVazia() {
        // Arrange
        List<Produto> produtos = Collections.emptyList();

        when(produtoInputPort.listar()).thenReturn(produtos);

        // Act
        ResponseEntity<List<ProdutoResponse>> resposta = produtoController.listarProdutos();

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void consultarProdutoPorNome_DeveRetornarProdutoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        String nomeProduto = "Produto1";
        Produto produto = new Produto();
        ProdutoResponse produtoResponse = new ProdutoResponse();

        when(produtoInputPort.consultarPorNome(nomeProduto)).thenReturn(produto);
        when(produtoMapper.toProdutoResponse(produto)).thenReturn(produtoResponse);

        // Act
        ResponseEntity<ProdutoResponse> resposta = produtoController.consultarProdutoPorNome(nomeProduto);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void consultarProdutoPorCategoria_DeveRetornarListaProdutoResponse_QuandoConsultaBemSucedida() {
        // Arrange
        Long categoriaId = 1L;
        List<Produto> produtos = Collections.singletonList(new Produto());
        List<ProdutoResponse> produtosResponse = Collections.singletonList(new ProdutoResponse());

        when(produtoInputPort.consultarPorCategoria(categoriaId)).thenReturn(produtos);
        when(produtoMapper.toProdutoResponseList(produtos)).thenReturn(produtosResponse);

        // Act
        ResponseEntity<List<ProdutoResponse>> resposta = produtoController.consultarProdutoPorCategoria(categoriaId);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertFalse(resposta.getBody().isEmpty());
    }
}
