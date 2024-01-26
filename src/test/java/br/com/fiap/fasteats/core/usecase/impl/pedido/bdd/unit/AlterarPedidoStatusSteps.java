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

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_AGUARDANDO_PAGAMENTO;
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

    @Dado("que o pedido exista no sistema com status criado")
    public void que_o_pedido_exista_no_sistema_com_status_criado() {
        pedido = getPedido(PEDIDO_ID,STATUS_PEDIDO_CRIADO);

    }
    @Quando("o status do pedido for alterado para aguardando pagamento")
    public void o_status_do_pedido_for_alterado_para_aguardando_pagamento() {
        //Arrange
        StatusPedido statusPedido = criarStatusPedido(ID_STATUS_AGUARDAMDO_PAGAMENTO, STATUS_PEDIDO_AGUARDANDO_PAGAMENTO);

        Pagamento pagamento = getPagamento();

        pedido = getPedido(PEDIDO_ID, ID_STATUS_AGUARDAMDO_PAGAMENTO);

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pedidoOutputPort.consultarPedido(anyLong())).thenReturn(Optional.of(pedido));
        when(statusPedidoInputPort.consultarPorNome(anyString())).thenReturn(statusPedido);
        when(pagamentoOutputPort.consultarPorPedidoId(anyLong())).thenReturn(Optional.of(pagamento));

        //Act
        resultado = alterarPedidoStatusInputPort.aguardandoPagamento(anyLong());
    }
    @Entao("o status do pedido deve ser alterado para aguardando pagamento")
    public void o_status_do_pedido_deve_ser_alterado_para_aguardando_pagamento() {
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
