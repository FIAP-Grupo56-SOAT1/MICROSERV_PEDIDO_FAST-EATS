package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.AlterarPedidoStatusUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.AlterarPedidoStatusValidator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlterarPedidoStatusSteps {

    @InjectMocks
    private AlterarPedidoStatusUseCase pedidoStatusUseCase;

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
    final Long ID_STATUS_EM_PREPARO = 5L;
    final Long ID_STATUS_PRONTO = 6L;
    final Long ID_STATUS_FINALIZADO = 7L;

    final Long ID_STATUS_CANCELADO = 8L;

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
        resultado = pedidoStatusUseCase.aguardandoPagamento(anyLong());
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
        resultado = pedidoStatusUseCase.pago(anyLong());
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
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_RECEBIDO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_RECEBIDO);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));
        doNothing().when(alterarPedidoStatusValidator).validarRecebido(anyLong());
        //Act
        resultado = pedidoStatusUseCase.recebido(anyLong());
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
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_RECEBIDO);
        assertEquals(ID_STATUS_RECEBIDO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para em preparo")
    public void o_status_do_pedido_for_alterado_para_em_preparo() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_EM_PREPARO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_EM_PREPARO);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));
        doNothing().when(alterarPedidoStatusValidator).validarEmPreparo(anyLong());
        //Act
        resultado = pedidoStatusUseCase.emPreparo(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para em preparo")
    public void o_status_do_pedido_deve_ser_alterado_para_em_preparo() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_EM_PREPARO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA PRONTO
    @Dado("que o pedido exista no sistema com status em preparo")
    public void que_o_pedido_exista_no_sistema_com_status_em_preparo() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_EM_PREPARO);
        assertEquals(ID_STATUS_EM_PREPARO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para pronto")
    public void o_status_do_pedido_for_alterado_para_pronto() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_PRONTO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_PRONTO);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));
        doNothing().when(alterarPedidoStatusValidator).validarPronto(anyLong());
        //Act
        resultado = pedidoStatusUseCase.pronto(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para pronto")
    public void o_status_do_pedido_deve_ser_alterado_para_pronto() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_PRONTO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA FINALIZADO
    @Dado("que o pedido exista no sistema com status pronto")
    public void que_o_pedido_exista_no_sistema_com_status_pronto() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_PRONTO);
        assertEquals(ID_STATUS_PRONTO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para finalizado")
    public void o_status_do_pedido_for_alterado_para_finalizado() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_FINALIZADO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_FINALIZADO);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));
        doNothing().when(alterarPedidoStatusValidator).validarFinalizado(anyLong());
        //Act
        resultado = pedidoStatusUseCase.finalizado(anyLong());
    }
    @Entao("o status do pedido deve ser alterado finalizado")
    public void o_status_do_pedido_deve_ser_alterado_finalizado() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_FINALIZADO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    //ALTERAR STATUS DO PEDIDO PARA CANCELADO
    @Dado("que o pedido exista no sistema com status diferente de cancelado")
    public void que_o_pedido_exista_no_sistema_com_status_diferente_de_cancelado() {
        //Arrange
        pedido = getPedido(PEDIDO_ID,ID_STATUS_AGUARDAMDO_PAGAMENTO);
        assertNotEquals(ID_STATUS_CANCELADO, pedido.getStatusPedido());
    }
    @Quando("o status do pedido for alterado para cancelado")
    public void o_status_do_pedido_for_alterado_para_cancelado() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(PEDIDO_ID, STATUS_PEDIDO_CANCELADO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_CANCELADO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        doNothing().when(alterarPedidoStatusValidator).validarCancelado(anyLong());
        //Act
        resultado = pedidoStatusUseCase.cancelado(anyLong());
    }
    @Entao("o status do pedido deve ser alterado cancelado")
    public void o_status_do_pedido_deve_ser_alterado_cancelado() {
        // Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_CANCELADO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Quando("o status do pedido for atualizado para aguardando pagamento")
    public void o_status_do_pedido_for_atualizado_para_aguardando_pagamento() {
        StatusPedido statusPedido = criarStatusPedido(ID_STATUS_AGUARDAMDO_PAGAMENTO, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);
        Pagamento pagamento = getPagamento();
        resultado = getPedido(PEDIDO_ID, ID_STATUS_AGUARDAMDO_PAGAMENTO);
        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(resultado);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultar(ID_STATUS_AGUARDAMDO_PAGAMENTO)).thenReturn(statusPedido);
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));
        //Act
        resultado = pedidoStatusUseCase.atualizarStatusPedido(PEDIDO_ID,ID_STATUS_AGUARDAMDO_PAGAMENTO);
    }

    @Entao("o status do pedido deve ser atualizado para aguardando pagamento")
    public void o_status_do_pedido_deve_ser_atualizado_para_aguardando_pagamento() {
        //Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(ID_STATUS_AGUARDAMDO_PAGAMENTO, resultado.getStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
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
