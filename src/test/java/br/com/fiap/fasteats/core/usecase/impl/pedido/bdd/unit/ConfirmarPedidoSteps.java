package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ProdutoNotFound;
import br.com.fiap.fasteats.core.domain.model.Pagamento;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.usecase.impl.pedido.ConfirmarPedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.AlterarPedidoStatusInputPort;
import br.com.fiap.fasteats.core.usecase.pedido.PedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConfirmarPedidoSteps {

    @InjectMocks
    private ConfirmarPedidoUseCase confirmarPedidoUseCase;

    @Mock
    private PedidoInputPort pedidoInputPort;

    @Mock
    private PagamentoOutputPort pagamentoOutputPort;

    @Mock
    private PedidoValidator pedidoValidator;

    @Mock
    private AlterarPedidoStatusInputPort alterarPedidoStatusInputPort;

    AutoCloseable openMocks;
    final Long PEDIDO_ID = 1L;

    final Long STATUS_PEDIDO_CRIADO = 1L;
    final Long ID_STATUS_AGUARDAMDO_PAGAMENTO = 2L;
    private Pedido pedido;
    private Pedido resultado;

    private Pagamento pagamento;

    private Pagamento pagamentoAtualizado;

    private Pedido pedidoAguardandoPagamento;

    private final Long tipoPagamentoId = 2L;



    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }


    @Dado("que o pedido exista no sistema com status criado e com produtos adicionados")
    public void que_o_pedido_exista_no_sistema_com_status_criado_e_com_produtos_adicionados() {
        //Arrange
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(PEDIDO_ID);
        produtoPedido.setQuantidade(1);
        produtoPedido.setValor(10.0);

        pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setStatusPedido(STATUS_PEDIDO_CRIADO);
        pedido.getProdutos().add(produtoPedido);

        pedidoAguardandoPagamento = new Pedido();
        pedidoAguardandoPagamento.setId(PEDIDO_ID);
        pedidoAguardandoPagamento.setStatusPedido(ID_STATUS_AGUARDAMDO_PAGAMENTO);
        pedidoAguardandoPagamento.getProdutos().add(produtoPedido);
        pedidoAguardandoPagamento.setQrCode("qrCode");
        pedidoAguardandoPagamento.setUrlPagamento("urlPagamento");

        pagamento = new Pagamento();
        pagamento.setId(1L);

        pagamentoAtualizado = new Pagamento();
        pagamentoAtualizado.setId(1L);
        pagamentoAtualizado.setQrCode("qrCode");
        pagamentoAtualizado.setUrlPagamento("urlPagamento");
    }

    @Quando("o pedido for confirmado deve ser alterado para novo status aguardando pagamento")
    public void o_pedido_for_confirmado_deve_ser_alterado_para_novo_status_aguardando_pagamento() {
        when(pedidoInputPort.consultar(PEDIDO_ID)).thenReturn(pedido);
        doNothing().when(pedidoValidator).validarAlterarPedido(pedido);
        //when(pagamentoOutputPort.gerarPagamento(pedido.getId(), tipoPagamentoId)).thenReturn(pagamento);
        when(alterarPedidoStatusInputPort.aguardandoPagamento(PEDIDO_ID)).thenReturn(pedidoAguardandoPagamento);
        //Act
        resultado = confirmarPedidoUseCase.confirmar(PEDIDO_ID, tipoPagamentoId);
    }

    @Entao("o pedido confirmado deve estar com status de aguardando pagamento")
    public void o_pedido_confirmado_deve_estar_com_status_de_aguardando_pagamento() {
        //Assert
        assertNotNull(resultado);
        assertEquals(PEDIDO_ID, resultado.getId());
        assertEquals(pagamentoAtualizado.getQrCode(), resultado.getQrCode());
        assertEquals(pagamentoAtualizado.getUrlPagamento(), resultado.getUrlPagamento());
        assertEquals(ID_STATUS_AGUARDAMDO_PAGAMENTO, resultado.getStatusPedido());
        verify(pedidoInputPort, times(1)).consultar(PEDIDO_ID);
        verify(alterarPedidoStatusInputPort, times(1)).aguardandoPagamento(PEDIDO_ID);
    }

    //NAO DEVE CONFIRMAR O PRODUTO SEM PEDIDO
    @Dado("que o pedido exista no sistema com status criado e sem produtos adicionados")
    public void que_o_pedido_exista_no_sistema_com_status_criado_e_sem_produtos_adicionados() {
        //ArrangE
        pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setStatusPedido(STATUS_PEDIDO_CRIADO);
        pedido.setProdutos(new ArrayList<>());
    }

    @Quando("eu tentar confirmar o pedido deve ser lancada uma exceção ProdutoNotFound")
    public void eu_tentar_confirmar_o_pedido_deve_ser_lancada_uma_exceção_produto_not_found() {
        //Act
        when(pedidoInputPort.consultar(PEDIDO_ID)).thenReturn(pedido);
        assertThrows(ProdutoNotFound.class, () -> confirmarPedidoUseCase.confirmar(PEDIDO_ID, tipoPagamentoId));
    }

    @Entao("o pedido nao deve ser confirmado")
    public void o_pedido_nao_deve_ser_confirmado() {
        //Assert
        verify(pedidoInputPort, times(1)).consultar(PEDIDO_ID);
        verify(pedidoInputPort, never()).atualizar(any(Pedido.class));
    }

}
