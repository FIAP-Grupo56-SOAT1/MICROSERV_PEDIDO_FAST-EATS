package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.domain.model.Pedido;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.dataprovider.repository.PedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.*;
import br.com.fiap.fasteats.dataprovider.repository.mapper.PedidoEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_CRIADO;
import static br.com.fiap.fasteats.core.constants.StatusPedidoConstants.STATUS_PEDIDO_RECEBIDO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoAdapterUnitTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PedidoEntityMapper pedidoEntityMapper;
    @InjectMocks
    private PedidoAdapter pedidoAdapter;
    AutoCloseable openMocks;
    private final LocalDateTime DATA_HORA_TESTE = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void salvarPedido() {
        // Arrange
        Pedido pedido = getPedido(1L);
        PedidoEntity pedidoEntity = getPedidoEntity(1L);

        when(pedidoEntityMapper.toPedidoEntity(pedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.saveAndFlush(pedidoEntity)).thenReturn(pedidoEntity);
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(pedido);

        // Act
        Pedido result = pedidoAdapter.salvarPedido(pedido);

        // Assert
        assertEquals(pedido.getId(), result.getId());
        verify(pedidoEntityMapper).toPedidoEntity(pedido);
        verify(pedidoRepository).saveAndFlush(pedidoEntity);
        verify(pedidoEntityMapper).toPedido(pedidoEntity);
    }

    @Test
    void consultarPedido() {
        // Arrange
        long id = 1L;
        Pedido pedido = getPedido(id);
        PedidoEntity pedidoEntity = getPedidoEntity(id);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(pedido);

        // Act
        Optional<Pedido> result = pedidoAdapter.consultarPedido(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pedido, toPedido(pedidoEntity));
        assertEquals(pedido.getId(), result.get().getId());
        verify(pedidoRepository).findById(id);
        verify(pedidoEntityMapper).toPedido(pedidoEntity);
    }

    @Test
    void listar() {
        // Arrange
        long id = 1L;
        Pedido pedido = getPedido(id);
        PedidoEntity pedidoEntity = getPedidoEntity(id);

        when(pedidoRepository.findAll()).thenReturn(List.of(pedidoEntity));
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(pedido);

        // Act
        List<Pedido> result = pedidoAdapter.listar();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(pedido.getId(), result.get(0).getId());
        verify(pedidoRepository).findAll();
        verify(pedidoEntityMapper).toPedido(pedidoEntity);
    }

    @Test
    void consultarPedidoAndamento() {
        // Arrange
        Pedido pedido = getPedido(1L);
        PedidoEntity pedidoEntity = getPedidoEntity(1L);
        pedido.setNomeStatusPedido(STATUS_PEDIDO_RECEBIDO);
        pedido.setDataHoraFinalizado(null);
        pedidoEntity.setDataHoraFinalizado(null);

        when(pedidoRepository.consultarPedidoAndamento(1L)).thenReturn(List.of(pedidoEntity));
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(pedido);

        // Act
        List<Pedido> result = pedidoAdapter.consultarPedidoAndamento(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(pedido.getId(), result.get(0).getId());
        assertNull(result.get(0).getDataHoraFinalizado());
        verify(pedidoRepository).consultarPedidoAndamento(1L);
        verify(pedidoEntityMapper).toPedido(pedidoEntity);
    }

    @Test
    void listarPedidosAndamento() {
        // Arrange
        Pedido pedido = getPedido(1L);
        PedidoEntity pedidoEntity = getPedidoEntity(1L);
        pedido.setNomeStatusPedido(STATUS_PEDIDO_RECEBIDO);
        pedido.setDataHoraFinalizado(null);
        pedidoEntity.setDataHoraFinalizado(null);

        when(pedidoRepository.listarPedidosAndamento()).thenReturn(List.of(pedidoEntity));
        when(pedidoEntityMapper.toPedido(pedidoEntity)).thenReturn(pedido);

        // Act
        List<Pedido> result = pedidoAdapter.listarPedidosAndamento();

        // Assert
        assertEquals(1, result.size());
        assertEquals(pedido.getId(), result.get(0).getId());
        assertNull(result.get(0).getDataHoraFinalizado());
        verify(pedidoRepository).listarPedidosAndamento();
        verify(pedidoEntityMapper).toPedido(pedidoEntity);
    }

    @Test
    void deletar() {
        // Arrange
        long id = 1L;

        // Act
        pedidoAdapter.deletar(id);

        // Assert
        verify(pedidoRepository).deleteById(id);
    }

    private Pedido getPedido(Long id) {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678909");
        cliente.setPrimeiroNome("Cliente");
        cliente.setUltimoNome("teste");
        cliente.setEmail("teste@teste.com");
        cliente.setAtivo(true);

        StatusPedido statusPedido = getStatusPedido(1L);

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(id);
        produtoPedido.setIdProduto(1L);
        produtoPedido.setQuantidade(1);
        produtoPedido.setValor(20.0);
        produtoPedido.setNomeProduto("Produto teste");

        return new Pedido(id,
                cliente,
                false,
                statusPedido.getId(),
                statusPedido.getNome(),
                true,
                DATA_HORA_TESTE,
                DATA_HORA_TESTE,
                DATA_HORA_TESTE,
                "0:30",
                20.0,
                List.of(produtoPedido),
                123456789L,
                "QRCODE",
                "URL");
    }

    private PedidoEntity getPedidoEntity(Long id) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setCpf("12345678909");
        cliente.setPrimeiroNome("Cliente");
        cliente.setUltimoNome("teste");
        cliente.setEmail("teste@teste.com");
        cliente.setAtivo(true);

        StatusPedido statusPedido = getStatusPedido(1L);

        StatusPedidoEntity statusPedidoEntity = new StatusPedidoEntity();
        statusPedidoEntity.setId(statusPedido.getId());
        statusPedidoEntity.setNome(statusPedido.getNome());


        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(id);
        pedidoEntity.setCliente(cliente);
        pedidoEntity.setAtivo(true);
        pedidoEntity.setStatusEntity(statusPedidoEntity);
        pedidoEntity.setDataHoraCriado(DATA_HORA_TESTE);
        pedidoEntity.setDataHoraRecebimento(DATA_HORA_TESTE);
        pedidoEntity.setDataHoraFinalizado(DATA_HORA_TESTE);
        pedidoEntity.setAtivo(true);
        pedidoEntity.setValor(20.0);
        pedidoEntity.setListaProdutos(null);

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setNome("Produto teste");

        ProdutoPedidoEntity produtoPedidoEntity = new ProdutoPedidoEntity();
        produtoPedidoEntity.setPedido(pedidoEntity);
        produtoPedidoEntity.setProduto(produtoEntity);
        produtoPedidoEntity.setQuantidade(1);
        produtoPedidoEntity.setValor(20.0);

        List<ProdutoPedidoEntity> listaProdutos = new ArrayList<>();
        listaProdutos.add(produtoPedidoEntity);
        pedidoEntity.setListaProdutos(listaProdutos);

        return pedidoEntity;
    }

    private Pedido toPedido(PedidoEntity pedidoEntity) {
        Cliente cliente = new Cliente();
        cliente.setCpf(pedidoEntity.getCliente().getCpf());
        cliente.setPrimeiroNome(pedidoEntity.getCliente().getPrimeiroNome());
        cliente.setUltimoNome(pedidoEntity.getCliente().getUltimoNome());
        cliente.setEmail(pedidoEntity.getCliente().getEmail());
        cliente.setAtivo(pedidoEntity.getCliente().getAtivo());

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setIdPedido(pedidoEntity.getListaProdutos().get(0).getPedido().getId());
        produtoPedido.setIdProduto(pedidoEntity.getListaProdutos().get(0).getProduto().getId());
        produtoPedido.setQuantidade(pedidoEntity.getListaProdutos().get(0).getQuantidade());
        produtoPedido.setValor(pedidoEntity.getListaProdutos().get(0).getValor());
        produtoPedido.setNomeProduto(pedidoEntity.getListaProdutos().get(0).getNome());

        Pedido pedido = new Pedido();
        pedido.setId(pedidoEntity.getId());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(pedidoEntity.getStatusEntity().getId());
        pedido.setNomeStatusPedido(pedidoEntity.getStatusEntity().getNome());
        pedido.setAtivo(pedidoEntity.getAtivo());
        pedido.setDataHoraCriado(pedidoEntity.getDataHoraCriado());
        pedido.setDataHoraRecebimento(pedidoEntity.getDataHoraRecebimento());
        pedido.setDataHoraFinalizado(pedidoEntity.getDataHoraFinalizado());
        pedido.setValor(pedidoEntity.getValor());
        pedido.setProdutos(List.of(produtoPedido));
        pedido.setTempoEspera("0:30");
        pedido.setIdPagamentoExterno(123456789L);
        pedido.setQrCode("QRCODE");
        pedido.setUrlPagamento("URL");
        return pedido;
    }

    private StatusPedido getStatusPedido(Long id) {
        return new StatusPedido(id, STATUS_PEDIDO_CRIADO, true);
    }
}