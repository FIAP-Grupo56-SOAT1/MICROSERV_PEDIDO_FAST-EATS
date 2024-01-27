package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import br.com.fiap.fasteats.core.dataprovider.CategoriaOutputPort;
import br.com.fiap.fasteats.core.domain.exception.CategoriaNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.model.Categoria;
import br.com.fiap.fasteats.core.usecase.impl.CategoriaUseCase;
import br.com.fiap.fasteats.core.usecase.impl.CategoriaUseCase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CategoriaSteps {
    @Mock
    private CategoriaOutputPort categoriaOutputPort;
    @InjectMocks
    private CategoriaUseCase categoriaUseCase;
    AutoCloseable openMocks;
    private Categoria categoria;

    private Categoria resultado;
    List<Categoria> categorias;
    List<Categoria> categoriasResultado;
    private Categoria categoriaResult;
    private Exception exception;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }


    //CRIAR UMA CATEGORIA
    @Dado("que uma nova categoria {string} deve ser criada")
    public void que_uma_nova_categoria_deve_ser_criada(String nomeCategoria) {
        categoria = getCategoria(1L, nomeCategoria, true);
    }

    @Quando("eu chamar o método de criação da categoria")
    public void eu_chamar_o_método_de_criação_da_categoria() {
        when(categoriaOutputPort.criar(categoria)).thenReturn(categoria);
        resultado = categoriaUseCase.criar(categoria);
    }

    @Entao("a categoria criada deve estar ativa e com o nome em maiúsculas")
    public void a_categoria_criada_deve_estar_ativa_e_com_o_nome_em_maiúsculas() {
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).criar(categoria);
    }

    //CONSULTAR UMA CATEGORIA
    @Dado("que uma categoria com ID {long} existe")
    public void que_uma_categoria_com_id_existe(Long id) {
        categoria = getCategoria(id, "LANCHE", true);
    }

    @Quando("eu consultar a categoria pelo ID")
    public void eu_consultar_a_categoria_pelo_id() {
        when(categoriaOutputPort.consultar(anyLong())).thenReturn(Optional.of(categoria));

        resultado = categoriaUseCase.consultar(anyLong());
    }

    @Entao("devo receber a categoria esperada")
    public void devo_receber_a_categoria_esperada() {
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).consultar(anyLong());
    }

    //TENTAR CONSULTAR UMA CATEGORIA INEXISTENTE
    @Dado("que uma categoria com ID {long} não existe")
    public void que_uma_categoria_com_id_não_existe(Long id) {
        when(categoriaOutputPort.consultar(id)).thenReturn(Optional.empty());
    }

    @Quando("eu tentar consultar a categoria pelo ID {long}")
    public void eu_tentar_consultar_a_categoria_pelo_id(Long id) {
        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.consultar(id));
    }

    @Entao("deve lançar uma exceção de categoria não encontrada")
    public void deve_lançar_uma_exceção_de_categoria_não_encontrada() {
        verify(categoriaOutputPort, times(1)).consultar(anyLong());
    }

    //ATUALIZAR UMA CATEGORIA
    @Quando("eu atualizar a categoria")
    public void eu_atualizar_a_categoria() {
        when(categoriaOutputPort.atualizar(categoria)).thenReturn(categoria);

        resultado = categoriaUseCase.atualizar(categoria);
    }

    @Entao("a categoria deve estar ativa e com o nome em maiúsculas")
    public void a_categoria_deve_estar_ativa_e_com_o_nome_em_maiúsculas() {
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).atualizar(categoria);
    }

    //tentar atualizar uma categoria com ativo nulo
    @Dado("que uma categoria com ID {long} existe e com campo ativo nulo")
    public void que_uma_categoria_com_id_existe_e_com_campo_ativo_nulo(Long id) {
        categoria = getCategoria(id, "LANCHE", null);
    }

    @Quando("eu tentar atualizar a categoria o campo ativo deve ser preenchido")
    public void eu_tentar_atualizar_a_categoria_o_campo_ativo_deve_ser_preenchido() {
        when(categoriaOutputPort.atualizar(categoria)).thenReturn(categoria);
        resultado = categoriaUseCase.atualizar(categoria);
    }

    @Entao("a categoria antes com ativo nulo deve estar ativa e com o nome em maiúsculo")
    public void a_categoria_antes_com_ativo_nulo_deve_estar_ativa_e_com_o_nome_em_maiúsculo() {
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome().toUpperCase(), resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(categoriaOutputPort, times(1)).atualizar(categoria);
    }

    //deletar uma categoria
    @Quando("eu deletar a categoria")
    public void eu_deletar_a_categoria() {
        categoriaUseCase.deletar(categoria.getId());
    }

    @Entao("a categoria deve ser removida")
    public void a_categoria_deve_ser_removida() {
        verify(categoriaOutputPort, times(1)).deletar(categoria.getId());
    }

    @Dado("que existem categorias cadastradas")
    public void que_existem_categorias_cadastradas() {
        categorias = List.of(getCategoria(1L, "Categoria 1", true),
                getCategoria(2L, "Categoria 2", true));

    }

    @Quando("eu listar todas as categorias")
    public void eu_listar_todas_as_categorias() {
        when(categoriaOutputPort.listar()).thenReturn(Optional.of(categorias));

        categoriasResultado = categoriaUseCase.listar();
    }

    @Entao("devo receber a lista de categorias")
    public void devo_receber_a_lista_de_categorias() {
        assertNotNull(categoriasResultado);
        assertEquals(categorias.size(), categoriasResultado.size());
        assertEquals(categorias.get(0).getId(), categoriasResultado.get(0).getId());
        assertEquals(categorias.get(0).getNome(), categoriasResultado.get(0).getNome());
        assertEquals(categorias.get(1).getId(), categoriasResultado.get(1).getId());
        assertEquals(categorias.get(1).getNome(), categoriasResultado.get(1).getNome());
        verify(categoriaOutputPort, times(1)).listar();
    }

    @Dado("que não existem categorias cadastradas")
    public void que_não_existem_categorias_cadastradas() {
        when(categoriaOutputPort.listar()).thenReturn(Optional.empty());
    }
    @Quando("eu tentar listar todas as categorias")
    public void eu_tentar_listar_todas_as_categorias() {
        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.listar());
    }
    @Entao("deve lançar uma exceção de CategoriaNotFound")
    public void deve_lançar_uma_exceção_de_categoria_not_found() {
        verify(categoriaOutputPort, times(1)).listar();
    }

    @Dado("que uma categoria com nome {string} existe")
    public void que_uma_categoria_com_nome_existe(String nomeCategoria) {
        categoria = getCategoria(1L, nomeCategoria, true);
    }
    @Quando("eu consultar a categoria pelo nome {string}")
    public void eu_consultar_a_categoria_pelo_nome(String nome) {
        when(categoriaOutputPort.consultarPorNome(nome)).thenReturn(Optional.of(categoria));

        resultado = categoriaUseCase.consultarPorNome(nome);
    }
    @Entao("devo receber a categoria {string} como resultado")
    public void devo_receber_a_categoria_como_resultado(String nome) {
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getNome());
        verify(categoriaOutputPort, times(1)).consultarPorNome(nome);
    }

    @Dado("que uma categoria com nome {string} não existe")
    public void que_uma_categoria_com_nome_não_existe(String nome) {
        when(categoriaOutputPort.consultarPorNome(nome)).thenReturn(Optional.empty());
    }
    @Quando("eu consultar a categoria pelo nome {string} deve lancar uma excecao")
    public void eu_consultar_a_categoria_pelo_nome_deve_lancar_uma_excecao(String nome) {
        assertThrows(CategoriaNotFound.class, () -> categoriaUseCase.consultarPorNome(nome));
    }
    @Entao("deve lançar uma exceção de CategoriaNotFound para nome {string} consultado")
    public void deve_lançar_uma_exceção_de_categoria_not_found_para_nome_consultado(String nome) {
        verify(categoriaOutputPort, times(1)).consultarPorNome(nome);
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
