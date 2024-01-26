package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AlterarPedidoStatusSteps {

    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    @Mock
    private PedidoOutputPort pedidoOutputPort;

    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;

    @Mock
    private PagamentoOutputPort pagamentoOutputPort;

    @Mock
    private AlterarPedidoStatusValidator alterarPedidoStatusValidator;

    AutoCloseable openMocks;
    final Long PEDIDO_ID = 1L;

    final Long STATUS_PEDIDO_CRIADO = 1L;
    final Long ID_STATUS_AGUARDAMDO_PAGAMENTO = 2L;
    final Long ID_STATUS_PAGO = 3L;
    final Long ID_STATUS_RECEBIDO = 4L;

    private Pedido pedido;
    private Pedido resultado;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        alterarPedidoStatusInputPort = new AlterarPedidoStatusUseCase(alterarPedidoStatusValidator,
                statusPedidoInputPort, pedidoOutputPort, pagamentoOutputPort);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    //ALTERAR STATUS DO PEDIDO PARA AGUARDANDO PAGAMENTO
    @Dado("que o pedido exista no sistema com status criado")
    public void que_o_pedido_exista_no_sistema_com_status_criado() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,STATUS_PEDIDO_CRIADO);
        assertEquals(STATUS_PEDIDO_CRIADO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para aguardando pagamento")
    public void o_status_do_pedido_for_alterado_para_aguardando_pagamento() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(ID_STATUS_AGUARDAMDO_PAGAMENTO, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);

        Pagamento pagamento = getPagamento();

        resultado = getPedido(PEDIDO_ID, ID_STATUS_AGUARDAMDO_PAGAMENTO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        //Act
        resultado = alterarPedidoStatusInputPort.aguardandoPagamento(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para aguardando pagamento")
    public void o_status_do_pedido_deve_ser_alterado_para_aguardando_pagamento() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_AGUARDAMDO_PAGAMENTO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA PAGO
    @Dado("que o pedido exista no sistema com status aguardando pagamento")
    public void que_o_pedido_exista_no_sistema_com_status_aguardando_pagamento() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_AGUARDAMDO_PAGAMENTO);
        assertEquals(ID_STATUS_AGUARDAMDO_PAGAMENTO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para pago")
    public void o_status_do_pedido_for_alterado_para_pago() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_PAGO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_PAGO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarPago(anyLong());
        //Act
        resultado = alterarPedidoStatusInputPort.pago(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para pago")
    public void o_status_do_pedido_deve_ser_alterado_para_pago() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_PAGO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA RECEBIDO
    @Dado("que o pedido exista no sistema com status pago")
    public void que_o_pedido_exista_no_sistema_com_status_pago() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_PAGO);
        assertEquals(ID_STATUS_PAGO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para recebido")
    public void o_status_do_pedido_for_alterado_para_recebido() {
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_RECEBIDO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_RECEBIDO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarRecebido(anyLong());
        //Act
        resultado = alterarPedidoStatusInputPort.recebido(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para recebido")
    public void o_status_do_pedido_deve_ser_alterado_para_recebido() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_RECEBIDO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA EM PREPARO
    @Dado("que o pedido exista no sistema com status recebido")
    public void que_o_pedido_exista_no_sistema_com_status_recebido() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("o status do pedido for alterado para em preparo")
    public void o_status_do_pedido_for_alterado_para_em_preparo() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Entao("o status do pedido deve ser alterado para em preparo")
    public void o_status_do_pedido_deve_ser_alterado_para_em_preparo() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    //ALTERAR STATUS DO PEDIDO PARA PRONTO
    @Dado("que o pedido exista no sistema com status em preparo")
    public void que_o_pedido_exista_no_sistema_com_status_em_preparo() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("o status do pedido for alterado para pronto")
    public void o_status_do_pedido_for_alterado_para_pronto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Entao("o status do pedido deve ser alterado para pronto")
    public void o_status_do_pedido_deve_ser_alterado_para_pronto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    //ALTERAR STATUS DO PEDIDO PARA FINALIZADO
    @Dado("que o pedido exista no sistema com status pronto")
    public void que_o_pedido_exista_no_sistema_com_status_pronto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("o status do pedido for alterado para finalizado")
    public void o_status_do_pedido_for_alterado_para_finalizado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Entao("o status do pedido deve ser alterado finalizado")
    public void o_status_do_pedido_deve_ser_alterado_finalizado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    //ALTERAR STATUS DO PEDIDO PARA CANCELADO
    @Dado("que o pedido exista no sistema com status diferente de cancelado")
    public void que_o_pedido_exista_no_sistema_com_status_diferente_de_cancelado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("o status do pedido for alterado para cancelado")
    public void o_status_do_pedido_for_alterado_para_cancelado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Entao("o status do pedido deve ser alterado cancelado")
    public void o_status_do_pedido_deve_ser_alterado_cancelado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


    private static Pedido getPedido(Long idPedido, Long idStatusPedido) {
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatusPedido(idStatusPedido);
        return pedido;
    }

    private static Pagamento getPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setUrlPagamento("xxxx-url.com");
        pagamento.setQrCode("xxxx");
        return pagamento;
    }

    private StatusPedido criarStatusPedido(Long id, String nome) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(id);
        statusPedido.setNome(nome);
        return statusPedido;
    }
}
