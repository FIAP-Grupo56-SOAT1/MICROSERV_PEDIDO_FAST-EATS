package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.CategoriaNotFound;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.domain.model.Produto;
import br.com.fiap.fasteats.core.usecase.impl.CategoriaUseCase;
import br.com.fiap.fasteats.core.usecase.impl.ProdutoUseCase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProdutoSteps {
    @Mock
    private CategoriaOutputPort categoriaOutputPort;
    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    @Mock
    private ProdutoOutputPort produtoOutputPort;

    AutoCloseable openMocks;
    private Categoria categoria;

    private Produto produto;

    private Long idProduto = 1L;

    private Long idCategoria = 1L;

    private Produto resultado;
    private List<Produto> produtos;
    private List<Produto> produtosResultado;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Dado("que um novo produto {string} deve ser criado")
    public void que_um_novo_produto_deve_ser_criado(String nome) {
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, nome);
    }

    @Quando("eu chamar o método de criação do produto")
    public void eu_chamar_o_método_de_criação_do_produto() {
        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.of(categoria));
        when(produtoOutputPort.criar(produto)).thenReturn(produto);

        resultado = produtoUseCase.criar(produto);
    }

    @Entao("a produto criado deve estar ativo")
    public void a_produto_criado_deve_estar_ativo() {
        assertNotNull(resultado);
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, times(1)).criar(produto);
    }

    @Dado("que um novo produto {string} deve ser criado com categoria inexistente")
    public void que_um_novo_produto_deve_ser_criado_com_categoria_inexistente(String nome) {
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, nome);
    }

    @Quando("eu chamar o método de criação do produto deve lancar uma excecao")
    public void eu_chamar_o_método_de_criação_do_produto_deve_lancar_uma_excecao() {
        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFound.class, () -> produtoUseCase.criar(produto));
    }

    @Entao("deve receber uma excecao CategoriaNotFound na criacao do produto")
    public void deve_receber_uma_excecao_categoria_not_found_na_criacao_do_produto() {
        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, never()).criar(produto);
    }

    @Dado("que um produto com ID {long} existe")
    public void que_um_produto_com_id_existe(Long id) {
        idProduto = id;
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, "X-SALADA");
    }

    @Quando("eu consultar a produto pelo ID")
    public void eu_consultar_a_produto_pelo_id() {
        when(produtoOutputPort.consultar(idProduto)).thenReturn(Optional.of(produto));
        resultado = produtoUseCase.consultar(idProduto);
    }

    @Entao("devo receber a produto esperado")
    public void devo_receber_a_produto_esperado() {
        assertNotNull(resultado);
        assertEquals(idProduto, resultado.getId());
        assertEquals(produto.getNome(), resultado.getNome());
        assertEquals(produto.getDescricao(), resultado.getDescricao());
        assertEquals(produto.getValor(), resultado.getValor());
        assertEquals(produto.getCategoria(), resultado.getCategoria());
        assertEquals(produto.getAtivo(), resultado.getAtivo());
        assertEquals(produto.getImagemBase64(), resultado.getImagemBase64());
        assertEquals(produto.getImagemUrl(), resultado.getImagemUrl());
    }

    @Dado("que um produto com ID {long} não existe")
    public void que_um_produto_com_id_não_existe(Long id) {
        idProduto = id;
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, "X-SALADA");
    }

    @Quando("eu tentar consultar a produto pelo ID {long}")
    public void eu_tentar_consultar_a_produto_pelo_id(Long id) {
        when(produtoOutputPort.consultar(id)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultar(idProduto));
    }

    @Entao("deve lancar uma exceção ProdutoNotCound para produto não encontrado")
    public void deve_lancar_uma_exceção_produto_not_cound_para_produto_não_encontrado() {
        verify(produtoOutputPort, times(1)).consultar(idProduto);
    }

    @Quando("eu atualizar a produto")
    public void eu_atualizar_a_produto() {
        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.of(categoria));
        when(produtoOutputPort.atualizar(produto)).thenReturn(produto);

        resultado = produtoUseCase.atualizar(produto);
    }

    @Entao("a produto deve estar ativo")
    public void a_produto_deve_estar_ativo() {
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

    @Dado("que um produto com ID {long} existe e com categoria inexistente no sistema")
    public void que_um_produto_com_id_existe_e_com_categoria_inexistente_no_sistema(long id) {
        idProduto = id;
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, "X-SALADA");
    }

    @Quando("eu tentar atualizar a produto com categoria inexistente")
    public void eu_tentar_atualizar_a_produto_com_categoria_inexistente() {
        when(categoriaOutputPort.consultar(categoria.getId())).thenReturn(Optional.empty());
        assertThrows(CategoriaNotFound.class, () -> produtoUseCase.atualizar(produto));
    }

    @Entao("devo receber um excecao de CategoriaNotFound")
    public void devo_receber_um_excecao_de_categoria_not_found() {
        verify(categoriaOutputPort, times(1)).consultar(categoria.getId());
        verify(produtoOutputPort, never()).atualizar(produto);
    }

    @Quando("eu deletar a produto")
    public void eu_deletar_a_produto() {
        produtoUseCase.deletar(idProduto);
    }

    @Entao("a produto deve ser removida")
    public void a_produto_deve_ser_removida() {
        verify(produtoOutputPort, times(1)).deletar(idProduto);
    }

    @Dado("que existem produtos cadastradas")
    public void que_existem_produtos_cadastradas() {
        produtos = List.of(getProduto(idProduto, "Hambúrguer"),getProduto(2L, "Hambúrguer X"));
    }

    @Quando("eu listar todas as produtos")
    public void eu_listar_todas_as_produtos() {
        when(produtoOutputPort.listar()).thenReturn(Optional.of(produtos));

        produtosResultado = produtoUseCase.listar();
    }

    @Entao("devo receber a lista de produtos")
    public void devo_receber_a_lista_de_produtos() {
        assertNotNull(produtosResultado);
        assertEquals(produtos.size(), produtosResultado.size());
        assertEquals(produtos, produtosResultado);
        verify(produtoOutputPort, times(1)).listar();
    }

    @Dado("que não existem produtos cadastradas")
    public void que_não_existem_produtos_cadastradas() {
        produtos = List.of();
    }
    @Quando("eu tentar listar todas as produtos")
    public void eu_tentar_listar_todas_as_produtos() {
        assertThrows(ProdutoNotFound.class, produtoUseCase::listar);
    }
    @Entao("deve lançar uma exceção de ProdutoNotFound")
    public void deve_lançar_uma_exceção_de_produto_not_found() {
        verify(produtoOutputPort, times(1)).listar();
    }

    @Dado("que um produto com nome {string} existe")
    public void que_um_produto_com_nome_existe(String nomeProduto) {
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, nomeProduto);
    }
    @Quando("eu consultar a produto pelo nome {string}")
    public void eu_consultar_a_produto_pelo_nome(String nomeProduto) {
        when(produtoOutputPort.consultarPorNome(nomeProduto)).thenReturn(Optional.of(produto));

        resultado = produtoUseCase.consultarPorNome(nomeProduto);
    }
    @Entao("devo receber a produto {string} como resultado")
    public void devo_receber_a_produto_como_resultado(String nomeProduto) {
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


    @Dado("que um produto com nome {string} não existe")
    public void que_um_produto_com_nome_não_existe(String nomeProduto) {
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produto = getProduto(idProduto, nomeProduto);
    }
    @Quando("eu consultar a produto pelo nome {string} deve lancar uma excecao")
    public void eu_consultar_a_produto_pelo_nome_deve_lancar_uma_excecao(String nomeProduto) {
        when(produtoOutputPort.consultarPorNome(nomeProduto)).thenReturn(Optional.empty());
        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultarPorNome(nomeProduto));
    }
    @Entao("deve lançar uma exceção de ProdutoNotFound para nome {string} consultado")
    public void deve_lançar_uma_exceção_de_produto_not_found_para_nome_consultado(String nomeProduto) {
        verify(produtoOutputPort, times(1)).consultarPorNome(nomeProduto);
    }

    @Dado("que um produto com ID {long} de categoria existe")
    public void que_um_produto_com_id_de_categoria_existe(long idCategoria) {
        this.idCategoria = idCategoria;
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produtos = List.of(getProduto(idProduto, "Hambúrguer"),getProduto(2L, "Hambúrguer X"));
    }
    @Quando("eu consultar a produto pelo ID {long} da categoria")
    public void eu_consultar_a_produto_pelo_id_da_categoria(long idCategoria) {
        when(produtoOutputPort.consultarPorCategoria(idCategoria)).thenReturn(Optional.of(produtos));
        produtosResultado = produtoUseCase.consultarPorCategoria(idCategoria);
    }
    @Entao("devo receber uma lista a produto como resultado")
    public void devo_receber_uma_lista_a_produto_como_resultado() {
        assertNotNull(produtosResultado);
        assertEquals(produtos.size(), produtosResultado.size());
        assertEquals(produtos, produtosResultado);

        verify(produtoOutputPort, times(1)).consultarPorCategoria(idCategoria);
    }

    @Dado("que um produto com ID {long} de categoria nao existe")
    public void que_um_produto_com_id_de_categoria_nao_existe(long idCategoria) {
        this.idCategoria = idCategoria;
        categoria = getCategoria(idCategoria, "LANCHE", true);
        produtos = List.of(getProduto(idProduto, "Hambúrguer"),getProduto(2L, "Hambúrguer X"));
    }
    @Quando("eu consultar o produto pelo ID {long} da categoria inexistente")
    public void eu_consultar_o_produto_pelo_id_da_categoria_inexistente(long idCategoria) {
        when(produtoOutputPort.consultarPorCategoria(idCategoria)).thenReturn(Optional.empty());
        assertThrows(ProdutoNotFound.class, () -> produtoUseCase.consultarPorCategoria(idCategoria));
    }
    @Entao("deve lançar uma exceção de ProdutoNotFound para consulta com categoria inexistente")
    public void deve_lançar_uma_exceção_de_produto_not_found_para_consulta_com_categoria_inexistente() {
        verify(produtoOutputPort, times(1)).consultarPorCategoria(idCategoria);
    }

    private Produto getProduto(Long id, String nome) {

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(nome);
        produto.setValor(15.0);
        ;
        produto.setCategoria(categoria);
        return produto;
    }


    private Categoria getCategoria(Long id, String nome, Boolean ativo) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        categoria.setDescricao(nome);
        categoria.setAtivo(ativo);
        return categoria;
    }
}
