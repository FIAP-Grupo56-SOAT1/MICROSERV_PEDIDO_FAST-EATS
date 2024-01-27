package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.dataprovider.repository.ProdutoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.CategoriaEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ProdutoEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoAdapterUnitTest {
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private ProdutoEntityMapper produtoEntityMapper;
    @InjectMocks
    private ProdutoAdapter produtoAdapter;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void criar() {
        // Arrange
        Produto produto = getProduto(1L, "nome");
        ProdutoEntity produtoEntity = getProdutoEntity(1L, "nome");

        when(produtoEntityMapper.toProdutoEntity(produto)).thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity)).thenReturn(produtoEntity);
        when(produtoEntityMapper.toProduto(produtoEntity)).thenReturn(produto);

        // Act
        Produto produtoCriado = produtoAdapter.criar(produto);

        // Assert
        assertEquals(produto.getId(), produtoCriado.getId());
        assertEquals(produto.getNome(), produtoCriado.getNome());
        assertEquals(produto.getValor(), produtoCriado.getValor());
        verify(produtoRepository).save(produtoEntity);
        verify(produtoEntityMapper).toProdutoEntity(produto);
        verify(produtoEntityMapper).toProduto(produtoEntity);
    }

    @Test
    void consultar() {
        // Arrange
        Produto produto = getProduto(1L, "nome");
        ProdutoEntity produtoEntity = getProdutoEntity(1L, "nome");

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produtoEntity));
        when(produtoEntityMapper.toProduto(produtoEntity)).thenReturn(produto);

        // Act
        Optional<Produto> produtoConsultado = produtoAdapter.consultar(produto.getId());

        // Assert
        assertTrue(produtoConsultado.isPresent());
        assertEquals(produto, toProduto(produtoEntity));
        assertEquals(produto.getId(), produtoConsultado.get().getId());
        assertEquals(produto.getNome(), produtoConsultado.get().getNome());
        assertEquals(produto.getValor(), produtoConsultado.get().getValor());
        verify(produtoRepository).findById(produto.getId());
        verify(produtoEntityMapper).toProduto(produtoEntity);
    }

    @Test
    void listar() {
        // Arrange
        List<Produto> produtoList = List.of(getProduto(1L, "nome"));
        List<ProdutoEntity> produtoEntityList = List.of(getProdutoEntity(1L, "nome"));

        when(produtoRepository.findAll()).thenReturn(produtoEntityList);
        when(produtoEntityMapper.toProduto(produtoEntityList.get(0))).thenReturn(produtoList.get(0));

        // Act
        Optional<List<Produto>> produtoListConsultado = produtoAdapter.listar();

        // Assert
        assertTrue(produtoListConsultado.isPresent());
        assertEquals(produtoList.get(0).getId(), produtoListConsultado.get().get(0).getId());
        verify(produtoRepository).findAll();
        verify(produtoEntityMapper).toProduto(produtoEntityList.get(0));
    }

    @Test
    void atualizar() {
        // Arrange
        Produto produto = getProduto(1L, "nome");
        ProdutoEntity produtoEntity = getProdutoEntity(1L, "nome");

        when(produtoEntityMapper.toProdutoEntity(produto)).thenReturn(produtoEntity);
        when(produtoRepository.save(produtoEntity)).thenReturn(produtoEntity);
        when(produtoEntityMapper.toProduto(produtoEntity)).thenReturn(produto);

        // Act
        Produto produtoCriado = produtoAdapter.atualizar(produto);

        // Assert
        assertEquals(produto.getId(), produtoCriado.getId());
        assertEquals(produto.getNome(), produtoCriado.getNome());
        assertEquals(produto.getValor(), produtoCriado.getValor());
        verify(produtoRepository).save(produtoEntity);
        verify(produtoEntityMapper).toProdutoEntity(produto);
        verify(produtoEntityMapper).toProduto(produtoEntity);
    }

    @Test
    void deletar() {
        // Arrange
        Long produtoId = 1L;

        // Act
        produtoAdapter.deletar(produtoId);

        // Assert
        verify(produtoRepository).deleteById(produtoId);
    }

    @Test
    void consultarPorNome() {
        // Arrange
        Produto produto = getProduto(1L, "teste");
        ProdutoEntity produtoEntity = getProdutoEntity(1L, "teste");

        when(produtoRepository.findByNome(produto.getNome())).thenReturn(List.of(produtoEntity));
        when(produtoEntityMapper.toProduto(produtoEntity)).thenReturn(produto);

        // Act
        Optional<Produto> produtoConsultado = produtoAdapter.consultarPorNome(produto.getNome());

        // Assert
        assertTrue(produtoConsultado.isPresent());
        assertEquals(produto.getId(), produtoConsultado.get().getId());
        assertEquals(produto.getNome(), produtoConsultado.get().getNome());
        verify(produtoRepository).findByNome(produto.getNome());
        verify(produtoEntityMapper).toProduto(produtoEntity);
    }

    @Test
    void consultarPorCategoria() {
        // Arrange
        Long categoriaId = 1L;
        Produto produto = getProduto(1L, "teste");
        ProdutoEntity produtoEntity = getProdutoEntity(1L, "teste");

        when(produtoRepository.findByCategoria(categoriaId)).thenReturn(List.of(produtoEntity));
        when(produtoEntityMapper.toProduto(produtoEntity)).thenReturn(produto);

        // Act
        Optional<List<Produto>> produtoConsultado = produtoAdapter.consultarPorCategoria(categoriaId);

        // Assert
        assertTrue(produtoConsultado.isPresent());
        assertEquals(produto.getId(), produtoConsultado.get().get(0).getId());
        assertEquals(produto.getNome(), produtoConsultado.get().get(0).getNome());
        assertEquals(produto.getCategoria(), produtoConsultado.get().get(0).getCategoria());
        verify(produtoRepository).findByCategoria(produto.getCategoria().getId());
        verify(produtoEntityMapper).toProduto(produtoEntity);
    }

    private Produto getProduto(Long id, String nome) {
        return new Produto(id,
                nome,
                "descricao",
                10.0,
                new Categoria(1L, "nome", "descricao", true),
                true,
                "imagemBase64",
                "imagemUrl");
    }

    private ProdutoEntity getProdutoEntity(Long id, String nome) {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1L);
        categoriaEntity.setNome("nome");
        categoriaEntity.setDescricao("descricao");
        categoriaEntity.setAtivo(true);

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(id);
        produtoEntity.setNome(nome);
        produtoEntity.setDescricao("descricao");
        produtoEntity.setValor(10.0);
        produtoEntity.setCategoriaId(categoriaEntity.getId());
        produtoEntity.setCategoriaEntity(categoriaEntity);
        produtoEntity.setAtivo(true);
        produtoEntity.setImagemBase64("imagemBase64");
        produtoEntity.setImagemUrl("imagemUrl");
        return produtoEntity;
    }

    private Produto toProduto(ProdutoEntity produtoEntity) {
        Categoria categoria = new Categoria();
        categoria.setId(produtoEntity.getCategoriaId());
        categoria.setNome(produtoEntity.getCategoriaEntity().getNome());
        categoria.setDescricao(produtoEntity.getCategoriaEntity().getDescricao());
        categoria.setAtivo(produtoEntity.getCategoriaEntity().getAtivo());

        Produto produto = new Produto();
        produto.setId(produtoEntity.getId());
        produto.setNome(produtoEntity.getNome());
        produto.setCategoria(categoria);
        produto.setDescricao(produtoEntity.getDescricao());
        produto.setValor(produtoEntity.getValor());
        produto.setAtivo(produtoEntity.getAtivo());
        produto.setImagemBase64(produtoEntity.getImagemBase64());
        produto.setImagemUrl(produtoEntity.getImagemUrl());
        return produto;
    }
}