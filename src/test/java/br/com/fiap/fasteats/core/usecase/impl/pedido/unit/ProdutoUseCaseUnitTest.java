package br.com.fiap.fasteats.core.usecase.impl.pedido.unit;

import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.CategoriaNotFound;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.usecase.impl.ProdutoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ProdutoUseCaseUnitTest")
class ProdutoUseCaseUnitTest {

    @Mock
    private ProdutoOutputPort produtoOutputPort;

    @Mock
    private CategoriaOutputPort categoriaOutputPort;

    private final ProdutoUseCase produtoUseCase;

    public ProdutoUseCaseUnitTest() {
        MockitoAnnotations.openMocks(this);
        produtoUseCase = new ProdutoUseCase(produtoOutputPort, categoriaOutputPort);
    }

    @Test
    @DisplayName("Deve criar um novo produto")
    void deveCriarProduto() {
        Produto produto = new Produto();
        produto.setNome("Hambúrguer");
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.of(categoria));
        when(produtoOutputPort.criar(produto)).thenReturn(produto);

        Produto resultado = produtoUseCase.criar(produto);

        assertNotNull(resultado);
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, times(1)).criar(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar um produto com categoria inexistente")
    void deveLancarExcecaoAoCriarProdutoComCategoriaInexistente() {
        Produto produto = new Produto();
        produto.setNome("Hambúrguer");
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> produtoUseCase.criar(produto));

        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, never()).criar(produto);
    }

    @Test
    @DisplayName("Deve consultar um produto pelo ID")
    void deveConsultarProdutoPorId() {
        Long idProduto = 1L;
        Produto produto = new Produto();
        produto.setId(idProduto);
        produto.setNome("Hambúrguer");
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(produtoOutputPort.consultar(idProduto)).thenReturn(Optional.of(produto));

        Produto resultado = produtoUseCase.consultar(idProduto);

        assertNotNull(resultado);
        assertEquals(idProduto, resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertEquals(produto.getAtivo(), resultado.getAtivo());
        assertEquals(produto.getImagemBase64(), resultado.getImagemBase64());
        assertEquals(produto.getImagemUrl(), resultado.getImagemUrl());

        verify(produtoOutputPort, times(1)).consultar(idProduto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um produto inexistente")
    void deveLancarExcecaoAoConsultarProdutoInexistente() {
        Long idProduto = 1L;

        when(produtoOutputPort.consultar(idProduto)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultar(idProduto));

        verify(produtoOutputPort, times(1)).consultar(idProduto);
    }

    @Test
    @DisplayName("Deve atualizar um produto")
    void deveAtualizarProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Hambúrguer");
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.of(categoria));
        when(produtoOutputPort.atualizar(produto)).thenReturn(produto);

        Produto resultado = produtoUseCase.atualizar(produto);

        assertNotNull(resultado);
        assertEquals(produto.getId(), resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, times(1)).atualizar(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar um produto com categoria inexistente")
    void deveLancarExcecaoAoAtualizarProdutoComCategoriaInexistente() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Hambúrguer");
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> produtoUseCase.atualizar(produto));

        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, never()).atualizar(produto);
    }

    @Test
    @DisplayName("Deve deletar um produto")
    void deveDeletarProduto() {
        Long idProduto = 1L;

        produtoUseCase.deletar(idProduto);

        verify(produtoOutputPort, times(1)).deletar(idProduto);
    }

    @Test
    @DisplayName("Deve listar os produtos")
    void deveListarProdutos() {
        List<Produto> produtos = List.of(
                new Produto(1L, "Hambúrguer", "Delicioso hambúrguer artesanal", 15.0,
                        new Categoria(1L, "Sanduíches", "Categoria de sanduíches", true), true, null, null),
                new Produto(2L, "Batata Frita", "Batata frita crocante", 10.0,
                        new Categoria(2L, "Acompanhamentos", "Categoria de acompanhamentos", true), true, null, null)
        );

        when(produtoOutputPort.listar()).thenReturn(Optional.of(produtos));

        List<Produto> resultado = produtoUseCase.listar();

        assertNotNull(resultado);
        assertEquals(produtos.size(), resultado.size());
        assertEquals(produtos, resultado);

        verify(produtoOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve lançar exceção ao listar produtos vazia")
    void deveLancarExcecaoAoListarProdutosVazia() {
        when(produtoOutputPort.listar()).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, produtoUseCase::listar);

        verify(produtoOutputPort, times(1)).listar();
    }

    @Test
    @DisplayName("Deve consultar um produto pelo nome")
    void deveConsultarProdutoPorNome() {
        String nomeProduto = "Hambúrguer";
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome(nomeProduto);
        produto.setDescricao("Delicioso hambúrguer artesanal");
        produto.setValor(15.0);
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        produto.setCategoria(categoria);

        when(produtoOutputPort.consultarPorNome(nomeProduto)).thenReturn(Optional.of(produto));

        Produto resultado = produtoUseCase.consultarPorNome(nomeProduto);

        assertNotNull(resultado);
        assertEquals(produto.getId(), resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertEquals(produto.getAtivo(), resultado.getAtivo());
        assertEquals(produto.getImagemBase64(), resultado.getImagemBase64());
        assertEquals(produto.getImagemUrl(), resultado.getImagemUrl());

        verify(produtoOutputPort, times(1)).consultarPorNome(nomeProduto);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar um produto pelo nome inexistente")
    void deveLancarExcecaoAoConsultarProdutoPorNomeInexistente() {
        String nomeProduto = "Hambúrguer";

        when(produtoOutputPort.consultarPorNome(nomeProduto)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultarPorNome(nomeProduto));

        verify(produtoOutputPort, times(1)).consultarPorNome(nomeProduto);
    }

    @Test
    @DisplayName("Deve consultar produtos por categoria")
    void deveConsultarProdutosPorCategoria() {
        Long idCategoria = 1L;
        List<Produto> produtos = List.of(
                new Produto(1L, "Hambúrguer", "Delicioso hambúrguer artesanal", 15.0,
                        new Categoria(1L, "Sanduíches", "Categoria de sanduíches", true), true, null, null),
                new Produto(2L, "Cheeseburger", "Hambúrguer com queijo", 12.0,
                        new Categoria(1L, "Sanduíches", "Categoria de sanduíches", true), true, null, null)
        );

        when(produtoOutputPort.consultarPorCategoria(idCategoria)).thenReturn(Optional.of(produtos));

        List<Produto> resultado = produtoUseCase.consultarPorCategoria(idCategoria);

        assertNotNull(resultado);
        assertEquals(produtos.size(), resultado.size());
        assertEquals(produtos, resultado);

        verify(produtoOutputPort, times(1)).consultarPorCategoria(idCategoria);
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar produtos por categoria inexistente")
    void deveLancarExcecaoAoConsultarProdutosPorCategoriaInexistente() {
        Long idCategoria = 1L;

        when(produtoOutputPort.consultarPorCategoria(idCategoria)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultarPorCategoria(idCategoria));

        verify(produtoOutputPort, times(1)).consultarPorCategoria(idCategoria);
    }
}
