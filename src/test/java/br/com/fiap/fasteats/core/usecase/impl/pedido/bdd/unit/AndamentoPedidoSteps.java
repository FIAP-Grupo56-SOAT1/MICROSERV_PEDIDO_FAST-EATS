package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AndamentoPedidoUseCase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AndamentoPedidoSteps {

    @InjectMocks
    private AndamentoPedidoUseCase andamentoPedidoUseCase;
    @Mock
    private PedidoOutputPort pedidoOutputPort;

    AutoCloseable openMocks;
    Long PEDIDO_ID = 1L;

    Long PEDIDO_2_ID= 2L;

    private Pedido pedido;
    private List<Pedido> pedidos;
    private List<Pedido> resultadoPedidos;
    private Pedido resultado;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    //CONSULTAR UM PEDIDO EM ANDAMENTO
    @Dado("que existe um pedido com ID de pedido {long} em andamento")
    public void que_existe_um_pedido_com_id_1L_de_pedido_em_andamento(Long id) {
        //Arrange
        pedido = getPedido(id);
        pedidos = List.of(pedido);
        PEDIDO_ID = id;
        assertEquals(PEDIDO_ID, pedido.getId());
    }
    @Quando("eu consultar um pedido por ID {long}")
    public void eu_consultar_um_pedido_por_ID_1(Long  id) {
        //Arrange
        when(pedidoOutputPort.consultarPedidoAndamento(id)).thenReturn(pedidos);
        //Act
        resultado = andamentoPedidoUseCase.consultarAndamentoPedido(id);
    }
    @Entao("o pedido recuperado pelo ID deve ser retornado")
    public void o_pedido_recuperado_pelo_ID_deve_ser_retornado() {
        //Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }

    //CONSULTAR PEDIDOS EM ANDAMENTO
    @Dado("que existe pedidos cadastrados com status em andamento")
    public void que_existe_pedidos_cadastrados_com_status_em_andamento() {
        //Arrange
        pedido = getPedido(PEDIDO_ID);
        pedidos = List.of(pedido,getPedido(PEDIDO_2_ID));
    }
    @Quando("eu consultar os pedidos em andamento")
    public void eu_consultar_os_pedidos_em_andamento() {
        //Arrange
        when(pedidoOutputPort.listarPedidosAndamento()).thenReturn(pedidos);
        //Act
        resultadoPedidos = andamentoPedidoUseCase.consultarPedidosEmAndamento();
    }
    @Entao("uma lista de pedidos em andamento deve ser retornado")
    public void uma_lista_de_pedidos_em_andamento_deve_ser_retornado() {
        //Assert
        assertNotNull(resultadoPedidos);
        assertEquals(pedidos.size(), resultadoPedidos.size());
        verify(pedidoOutputPort, times(1)).listarPedidosAndamento();
    }

    //NAO DEVE CONSULTAR UM PEDIDO EM ANDAMENTO
    @Dado("que nao existe um pedido com ID de pedido {long} em andamento")
    public void que_nao_existe_um_pedido_com_id_de_pedido_em_andamento(Long id) {
        //Arrange
        pedido = getPedido(id);
        pedidos = List.of();
    }
    @Quando("eu tentar consultar um pedido por ID {long}")
    public void eu_tentar_consultar_um_pedido_por_id(Long id) {
        //Act
        when(pedidoOutputPort.consultarPedidoAndamento(anyLong())).thenReturn(pedidos);
    }
    @Entao("deve ser lancada uma excecao PedidoNotFound para a pesquisa por ID de pedido em andamento")
    public void deve_ser_lancada_uma_excecao_pedido_not_found_para_a_pesquisa_por_id_de_pedido_em_andamento() {
        assertThrows(PedidoNotFound.class, () -> andamentoPedidoUseCase.consultarAndamentoPedido(anyLong()));
        verify(pedidoOutputPort, times(1)).consultarPedidoAndamento(anyLong());
    }


    private static Pedido getPedido(Long idPedido) {
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setDataHoraRecebimento(LocalDateTime.now());
        return pedido;
    }
}
