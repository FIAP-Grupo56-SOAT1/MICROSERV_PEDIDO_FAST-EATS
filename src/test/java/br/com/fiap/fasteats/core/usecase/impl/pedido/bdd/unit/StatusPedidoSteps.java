package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.StatusPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.StatusPedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.StatusPedidoUseCase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class StatusPedidoSteps {
    @Mock
    private StatusPedidoOutputPort statusPedidoOutputPort;
    @InjectMocks
    private StatusPedidoUseCase statusPedidoUseCase;
    AutoCloseable openMocks;
    private StatusPedido statusPedido;

    private StatusPedido resultado;
    List<StatusPedido> statusPedidos;
    List<StatusPedido> statusPedidosResultado;
    private StatusPedido statusPedidoResult;
    private Exception exception;

    private static final Long ID_EXISTENTE = 1L;
    private static Long ID_INEXISTENTE = 2L;
    private static String NOME_EXISTENTE = "APROVADO";
    private static String NOME_INEXISTENTE = "RECEBIDO";

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }
    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Dado("que um nov status pedido {string} deve ser criada")
    public void que_um_nov_status_pedido_deve_ser_criada(String nomeStatusPedido) {
        statusPedido = getStatusPedido(1L, nomeStatusPedido, true);
    }
    @Quando("eu chamar o método de criação de status pedido")
    public void eu_chamar_o_método_de_criação_de_status_pedido() {
        when(statusPedidoOutputPort.criar(statusPedido)).thenReturn(statusPedido);
        resultado = statusPedidoUseCase.criar(statusPedido);
    }
    @Entao("um status pedido criado deve estar ativa e com o nome em maiúsculas")
    public void um_status_pedido_criado_deve_estar_ativa_e_com_o_nome_em_maiúsculas() {
        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).criar(statusPedido);
    }

    @Dado("que um status pedido com ID {long} existe no sistema")
    public void que_um_status_pedido_com_id_existe_no_sistema(long id) {
        statusPedido = getStatusPedido(id, NOME_EXISTENTE, true);
    }
    @Quando("eu consultar um status pedido pelo ID")
    public void eu_consultar_um_status_pedido_pelo_id() {
        when(statusPedidoOutputPort.criar(statusPedido)).thenReturn(statusPedido);
        resultado = statusPedidoUseCase.criar(statusPedido);
    }
    @Entao("devo receber um status pedido")
    public void devo_receber_um_status_pedido() {
        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).criar(statusPedido);
    }

    @Dado("que um status pedido com ID {long} nao existe no sistema")
    public void que_um_status_pedido_com_id_nao_existe_no_sistema(long id) {
        ID_INEXISTENTE = id;
        statusPedido = getStatusPedido(id, NOME_INEXISTENTE, true);
    }
    @Quando("eu consultar um status pedido pelo ID que nao existe")
    public void eu_consultar_um_status_pedido_pelo_id_que_nao_existe() {
        when(statusPedidoOutputPort.consultar(ID_INEXISTENTE)).thenReturn(Optional.empty());
    }
    @Entao("deve lancar uma exceção de StatusPedidoNotFound")
    public void deve_lancar_uma_exceção_de_status_pedido_not_found() {
        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.consultar(ID_INEXISTENTE));
        verify(statusPedidoOutputPort, times(1)).consultar(ID_INEXISTENTE);
    }

    @Quando("eu atualizar o status pedido")
    public void eu_atualizar_o_status_pedido() {
        when(statusPedidoOutputPort.atualizar(statusPedido)).thenReturn(statusPedido);
        resultado = statusPedidoUseCase.atualizar(statusPedido);
    }
    @Entao("o status pedido deve estar ativa e com o nome em maiúsculas")
    public void o_status_pedido_deve_estar_ativa_e_com_o_nome_em_maiúsculas() {
        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals("APROVADO", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).atualizar(statusPedido);
    }

    @Quando("eu deletar o status pedido")
    public void eu_deletar_o_status_pedido() {
        statusPedidoUseCase.deletar(ID_EXISTENTE);
    }
    @Entao("o status pedido deve ser removido")
    public void o_status_pedido_deve_ser_removido() {
        verify(statusPedidoOutputPort, times(1)).deletar(ID_EXISTENTE);
    }

    @Dado("que existem status pedido cadastrados")
    public void que_existem_status_pedido_cadastrados() {
        statusPedidos = List.of(getStatusPedido(ID_EXISTENTE,NOME_EXISTENTE,true));
    }
    @Quando("eu listar todos os status pedido")
    public void eu_listar_todos_os_status_pedido() {
        when(statusPedidoOutputPort.listar()).thenReturn(Optional.of(statusPedidos));
        statusPedidosResultado = statusPedidoUseCase.listar();
    }
    @Entao("devo receber a lista de status pedido")
    public void devo_receber_a_lista_de_status_pedido() {
        assertNotNull(statusPedidosResultado);
        assertEquals(statusPedidos.size(), statusPedidosResultado.size());
        assertEquals(statusPedidos, statusPedidosResultado);
        verify(statusPedidoOutputPort, times(1)).listar();
    }

    @Dado("que nao existem status pedido cadastrados")
    public void que_nao_existem_status_pedido_cadastrados() {
        statusPedidos = List.of();
    }
    @Quando("eu listar todos os status pedido do sistema")
    public void eu_listar_todos_os_status_pedido_do_sistema() {
        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.listar());
    }
    @Entao("devo receber uma exception StatusPedidoNotFound")
    public void devo_receber_uma_exception_status_pedido_not_found() {
        verify(statusPedidoOutputPort, times(1)).listar();
    }

    @Dado("que um status pedido com nome {string} existe")
    public void que_um_status_pedido_com_nome_existe(String nomeStatusPedido) {
        NOME_EXISTENTE = nomeStatusPedido;
        statusPedido = getStatusPedido(ID_EXISTENTE, NOME_EXISTENTE, true);
    }
    @Quando("eu consultar o status pedido pelo nome {string}")
    public void eu_consultar_o_status_pedido_pelo_nome(String string) {
        when(statusPedidoOutputPort.consultarPorNome(NOME_EXISTENTE)).thenReturn(Optional.of(statusPedido));
        resultado = statusPedidoUseCase.consultarPorNome(NOME_EXISTENTE);
    }
    @Entao("devo receber o status pedido {string} como resultado")
    public void devo_receber_o_status_pedido_como_resultado(String string) {
        assertNotNull(resultado);
        assertEquals(statusPedido, resultado);
        assertEquals(NOME_EXISTENTE.toUpperCase(), resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(statusPedidoOutputPort, times(1)).consultarPorNome(NOME_EXISTENTE);
    }

    @Dado("que um status pedido com nome {string} não existe")
    public void que_um_status_pedido_com_nome_não_existe(String nomeStatusPedido) {
        NOME_INEXISTENTE = nomeStatusPedido;
        statusPedido = getStatusPedido(ID_INEXISTENTE, NOME_INEXISTENTE, true);
    }
    @Quando("eu consultar o status pedido pelo nome {string} deve lancar uma excecao")
    public void eu_consultar_o_status_pedido_pelo_nome_deve_lancar_uma_excecao(String string) {
        when(statusPedidoOutputPort.consultarPorNome(NOME_INEXISTENTE)).thenReturn(Optional.empty());
    }
    @Entao("deve lançar uma exceção de StatusPedidoNotFound para nome {string} consultado")
    public void deve_lançar_uma_exceção_de_status_pedido_not_found_para_nome_consultado(String string) {
        assertThrows(StatusPedidoNotFound.class, () -> statusPedidoUseCase.consultarPorNome(NOME_INEXISTENTE));
        verify(statusPedidoOutputPort, times(1)).consultarPorNome(NOME_INEXISTENTE);
    }


    private StatusPedido getStatusPedido(Long id, String nome, Boolean ativo) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(id);
        statusPedido.setNome(nome);
        statusPedido.setAtivo(ativo);
        return statusPedido;
    }
}
