package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.PagamentoOutputPort;
import br.com.fiap.fasteats.core.dataprovider.PedidoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.PedidoNotFound;
import br.com.fiap.fasteats.core.domain.model.*;
import br.com.fiap.fasteats.core.usecase.ClienteInputPort;
import br.com.fiap.fasteats.core.usecase.impl.pedido.PedidoUseCase;
import br.com.fiap.fasteats.core.usecase.pedido.StatusPedidoInputPort;
import br.com.fiap.fasteats.core.validator.PedidoValidator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoSteps {
    @Mock
    private PedidoOutputPort pedidoOutputPort;
    @Mock
    private ClienteInputPort clienteInputPort;
    @Mock
    private StatusPedidoInputPort statusPedidoInputPort;
    @Mock
    private PagamentoOutputPort pagamentoOutputPort;
    @Mock
    private PedidoValidator pedidoValidator;
    @InjectMocks
    private PedidoUseCase pedidoUseCase;
    AutoCloseable openMocks;
    private final String CPF_CLIENTE = "12345678909";
    private Pedido pedido;
    private StatusPedido statusPedido;
    private List<Pedido> pedidos;
    private Long pedidoId;
    private Long pagamentoExternoId;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Dado("que um cliente não cadastrado mas identificado deseja fazer um pedido")
    public void queUmClienteDesejaCriarUmPedido() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
        pedido.setIdentificaCliente(true);
        StatusPedido statusPedido = getStatusPedido(1L, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(statusPedido.getNome())).thenReturn(statusPedido);
        when(clienteInputPort.clienteExiste(CPF_CLIENTE)).thenReturn(false);
        when(clienteInputPort.criar(pedido.getCliente())).thenReturn(pedido.getCliente());
    }

    @Dado("que um cliente cadastrado mas identificado deseja fazer um pedido")
    public void queUmClienteCadastradoDesejaCriarUmPedido() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
        pedido.setIdentificaCliente(true);
        StatusPedido statusPedido = getStatusPedido(1L, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(statusPedido.getNome())).thenReturn(statusPedido);
        when(clienteInputPort.clienteExiste(CPF_CLIENTE)).thenReturn(true);
        when(clienteInputPort.consultar(CPF_CLIENTE)).thenReturn(pedido.getCliente());
    }

    @Quando("o cliente fornece as informações do pedido e da sua identificação")
    public void oClienteForneceAsInformaçõesDoPedido() {
        pagamentoExternoId = 123456789L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setIdPagamentoExterno(pagamentoExternoId);
        pagamento.setUrlPagamento("UrlPagamento");
        pagamento.setQrCode("QrCode");

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pagamentoOutputPort.consultarPorPedidoId(pedido.getId())).thenReturn(Optional.of(pagamento));
    }

    @Dado("que um pedido não foi identicado")
    public void queUmPedidoNaoFoiIdentificado() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
        pedido.setIdentificaCliente(false);
        StatusPedido statusPedido = getStatusPedido(1L, STATUS_PEDIDO_CRIADO);

        when(statusPedidoInputPort.consultarPorNome(statusPedido.getNome())).thenReturn(statusPedido);
    }

    @Quando("as informações do pedido são fornecidas")
    public void asInformaçõesDoPedidoSãoFornecidas() {
        pagamentoExternoId = 123456789L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setIdPagamentoExterno(pagamentoExternoId);
        pagamento.setUrlPagamento("UrlPagamento");
        pagamento.setQrCode("QrCode");

        when(pedidoOutputPort.salvarPedido(pedido)).thenReturn(pedido);
        when(pagamentoOutputPort.consultarPorPedidoId(pedido.getId())).thenReturn(Optional.of(pagamento));
    }

    @Entao("o pedido não identificado é criado com sucesso")
    public void oPedidoNaoIdentificadoÉCriadoComSucesso() {
        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        assertEquals(pagamentoExternoId, resultado.getIdPagamentoExterno());
        assertEquals("UrlPagamento", resultado.getUrlPagamento());
        assertEquals("QrCode", resultado.getQrCode());
        assertEquals(STATUS_PEDIDO_CRIADO, resultado.getNomeStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
        verify(pagamentoOutputPort, times(1)).consultarPorPedidoId(pedido.getId());
        verify(clienteInputPort, times(0)).clienteExiste(CPF_CLIENTE);
        verify(clienteInputPort, times(0)).consultar(CPF_CLIENTE);
        verify(clienteInputPort, times(0)).criar(pedido.getCliente());
    }

    @Entao("o para pedido de um cliente antes não cadastrado, mas identificado é criado com sucesso")
    public void oPedidoÉCriadoComSucessoParaClienteIdentificadoNaoCadastrado() {
        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        assertEquals(pagamentoExternoId, resultado.getIdPagamentoExterno());
        assertEquals("UrlPagamento", resultado.getUrlPagamento());
        assertEquals("QrCode", resultado.getQrCode());
        assertEquals(STATUS_PEDIDO_CRIADO, resultado.getNomeStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
        verify(pagamentoOutputPort, times(1)).consultarPorPedidoId(pedido.getId());
        verify(clienteInputPort, times(1)).clienteExiste(CPF_CLIENTE);
        verify(clienteInputPort, times(0)).consultar(CPF_CLIENTE);
        verify(clienteInputPort, times(1)).criar(pedido.getCliente());
    }

    @Entao("o para pedido de um cliente antes cadastrado, mas identificado é criado com sucesso")
    public void oPedidoÉCriadoComSucessoParaClienteIdentificadoCadastrado() {
        Pedido resultado = pedidoUseCase.criar(pedido);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        assertEquals(pagamentoExternoId, resultado.getIdPagamentoExterno());
        assertEquals("UrlPagamento", resultado.getUrlPagamento());
        assertEquals("QrCode", resultado.getQrCode());
        assertEquals(STATUS_PEDIDO_CRIADO, resultado.getNomeStatusPedido());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
        verify(pagamentoOutputPort, times(1)).consultarPorPedidoId(pedido.getId());
        verify(clienteInputPort, times(1)).clienteExiste(CPF_CLIENTE);
        verify(clienteInputPort, times(1)).consultar(CPF_CLIENTE);
        verify(clienteInputPort, times(0)).criar(pedido.getCliente());
    }

    @Dado("que um cliente deseja consultar um pedido existente")
    public void queUmClienteDesejaConsultarUmPedidoExistente() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
    }

    @Quando("o cliente fornece o ID do pedido")
    public void oClienteForneceOIDDoPedido() {
        when(pedidoOutputPort.consultarPedido(pedidoId)).thenReturn(Optional.of(pedido));
        when(pagamentoOutputPort.consultarPorPedidoId(pedido.getId())).thenThrow(new RuntimeException("Erro ao consultar pagamento"));
    }

    @Entao("as informações do pedido são exibidas corretamente")
    public void asInformaçõesDoPedidoSãoExibidasCorretamente() {
        Pedido resultado = pedidoUseCase.consultar(pedidoId);
        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        verify(pedidoOutputPort, times(1)).consultarPedido(pedidoId);
    }

    @Dado("que um cliente deseja consultar um pedido não existente")
    public void queUmClienteDesejaConsultarUmPedidoNaoExistente() {
        pedidoId = 1L;
    }

    @Quando("o cliente fornece o ID do pedido que não existe")
    public void oClienteForneceOIDDoPedidoQueNaoExiste() {
        when(pedidoOutputPort.consultarPedido(pedidoId)).thenReturn(Optional.empty());
    }

    @Entao("é exibida uma excessão informando que o pedido não existe")
    public void eExibidoUmaExcessaoInformandoQueOPedidoNaoExiste() {
        assertThrows(PedidoNotFound.class, () -> pedidoUseCase.consultar(pedidoId));
        verify(pedidoOutputPort, times(1)).consultarPedido(pedidoId);
    }

    @Dado("que um cliente deseja ver a lista de todos os pedidos")
    public void queUmClienteDesejaVerAListaDeTodosOsPedidos() {
        pedidos = List.of(getPedido(1L), getPedido(2L), getPedido(3L));
    }

    @Quando("o cliente solicita a listagem de pedidos")
    public void oClienteSolicitaAListagemDePedidos() {
        when(pedidoOutputPort.listar()).thenReturn(pedidos);
    }

    @Entao("a lista de pedidos é exibida corretamente")
    public void aListaDePedidosÉExibidaCorretamente() {
        List<Pedido> resultado = pedidoUseCase.listar();
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(pedidoOutputPort, times(1)).listar();
        verify(pagamentoOutputPort, times(3)).consultarPorPedidoId(anyLong());
    }

    @Dado("que um cliente deseja atualizar um pedido existente")
    public void queUmClienteDesejaAtualizarUmPedidoExistente() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
    }

    @Quando("^o cliente fornece as novas informações do pedido$")
    public void oClienteForneceAsNovasInformaçõesDoPedido() {
        when(pedidoOutputPort.salvarPedido(any())).thenReturn(pedido);
    }

    @Entao("^o pedido é atualizado com sucesso$")
    public void oPedidoÉAtualizadoComSucesso() {
        Pedido resultado = pedidoUseCase.atualizar(pedido);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoOutputPort, times(1)).salvarPedido(pedido);
    }

    @Dado("que um cliente deseja deletar um pedido existente")
    public void queUmClienteDesejaDeletarUmPedidoExistente() {
        pedido = getPedido(1L);
    }

    @Quando("o cliente fornece o ID do pedido a ser deletado")
    public void oClienteForneceOIDDoPedidoASerDeletado() {
        pedidoId = 1L;
        pedido.setId(pedidoId);
        when(pedidoOutputPort.consultarPedido(any())).thenReturn(Optional.of(pedido));
    }

    @Entao("o pedido é removido com sucesso")
    public void oPedidoÉRemovidoComSucesso() {
        assertDoesNotThrow(() -> pedidoUseCase.deletar(pedidoId));
        verify(pedidoOutputPort, times(1)).deletar(pedidoId);
    }

    @Dado("que um cliente deseja atualizar o valor de um pedido")
    public void queUmClienteDesejaAtualizarOValorDeUmPedido() {
        pedidoId = 1L;
        pedido = getPedido(pedidoId);
    }

    @Quando("o cliente fornece as informações atualizadas do pedido")
    public void oClienteForneceAsInformaçõesAtualizadasDoPedido() {
        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(pedidoId);
        produtoPedido.setNomeProduto("Produto");
        produtoPedido.setValor(10.0);
        produtoPedido.setQuantidade(2);

        pedido.getProdutos().add(produtoPedido);
    }

    @Entao("o valor do pedido é recalculado corretamente")
    public void oValorDoPedidoÉRecalculadoCorretamente() {
        pedidoUseCase.atualizarValorPedido(pedido);
        assertEquals(20.0, pedido.getValor());
    }

    @Dado("que um cliente deseja identificar-se em um pedido")
    public void queUmClienteDesejaIdentificarSeEmUmPedido() {
        // Configurar o comportamento esperado para o mock
        when(clienteInputPort.clienteExiste(any())).thenReturn(true);
        when(clienteInputPort.consultar(any())).thenReturn(new Cliente());
    }

    private Pedido getPedido(Long pedidoId) {
        Cliente cliente = new Cliente();
        cliente.setCpf(CPF_CLIENTE);
        cliente.setPrimeiroNome("Cliente");
        cliente.setUltimoNome("Teste");
        cliente.setAtivo(true);

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setCliente(cliente);
        pedido.setProdutos(new ArrayList<>());
        pedido.setValor(0.0);
        pedido.setStatusPedido(1L);
        pedido.setNomeStatusPedido(STATUS_PEDIDO_CRIADO);
        pedido.setDataHoraCriado(LocalDateTime.now());
        return pedido;
    }

    private StatusPedido getStatusPedido(Long statusPedidoId, String statusPedidoNome) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(statusPedidoId);
        statusPedido.setNome(statusPedidoNome);
        statusPedido.setAtivo(true);
        return statusPedido;
    }
}
