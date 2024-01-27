package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.CancelarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AndamentoPedidoSteps {

    @InjectMocks
    private CancelarPedidoUseCase cancelarPedidoUseCase;
    @Mock
    private PedidoInputPort pedidoInputPort;
    @Mock
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    AutoCloseable openMocks;
    final Long PEDIDO_ID = 1L;

    final Long STATUS_PEDIDO_CRIADO = 1L;
    final Long STATUS_PEDIDO_CANCELADO = 4L;

    private Pedido pedido;
    private Pedido resultado;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @Dado("que existe um pedido com ID de pedido {int} com status em andamento")
    public void que_existe_um_pedido_com_id_de_pedido_com_status_em_andamento(Long id) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("eu tentar consultar um pedido por ID de pedido {int}")
    public void eu_tentar_consultar_um_pedido_por_id_de_pedido(Long  id) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Entao("o pedido recuperado pelo ID de pedido deve ser retornado")
    public void o_pedido_recuperado_pelo_id_de_pedido_deve_ser_retornado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Dado("que o pedido exista no sistema")
    public void que_o_pedido_exista_no_sistema() {
        pedido = getPedido(PEDIDO_ID,STATUS_PEDIDO_CRIADO);
        when(pedidoInputPort.consultar(pedido.getId())).thenReturn(pedido);
    }
    @Quando("o pedido for cancelado")
    public void o_pedido_for_cancelado() {
        //Arrange
        pedido = getPedido(PEDIDO_ID, STATUS_PEDIDO_CRIADO);
        Pedido pedidoCancelado = getPedido(PEDIDO_ID, STATUS_PEDIDO_CANCELADO);
        pedidoCancelado.setDataHoraFinalizado(LocalDateTime.now());

        when(pedidoInputPort.consultar(pedido.getId())).thenReturn(pedido);
        when(alterarPedidoStatusInputPort.cancelado(pedido.getId())).thenReturn(pedidoCancelado);
        when(pedidoInputPort.atualizar(pedido)).thenReturn(pedido);

        //Act
        resultado = cancelarPedidoUseCase.cancelar(pedido.getId());
    }
    @Entao("o status do pedido deve ser alterado para cancelado")
    public void o_status_do_pedido_deve_ser_alterado_para_cancelado() {
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(STATUS_PEDIDO_CANCELADO, resultado.getStatusPedido());
        assertNotNull(resultado.getDataHoraFinalizado());
        verify(pedidoInputPort, times(1)).consultar(PEDIDO_ID);
        verify(alterarPedidoStatusInputPort, times(1)).cancelado(PEDIDO_ID);
    }
    @Entao("a data e hora de finalizacao do pedido deve ser preenchida")
    public void a_data_e_hora_de_finalizacao_do_pedido_deve_ser_preenchida() {
        assertNotNull(resultado.getDataHoraFinalizado());
    }

    private static Pedido getPedido(Long idPedido, Long idStatusPedido) {
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        return pedido;
    }
}
