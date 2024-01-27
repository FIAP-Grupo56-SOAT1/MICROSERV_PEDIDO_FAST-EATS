package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.dataprovider.repository.ProdutoPedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.PedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoIdEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ProdutoPedidoEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProdutoPedidoAdapterUnitTest {
    @Mock
    private ProdutoPedidoRepository produtoPedidoRepository;
    @Mock
    private ProdutoPedidoEntityMapper produtoPedidoEntityMapper;
    @InjectMocks
    private ProdutoPedidoAdapter produtoPedidoAdapter;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void removerProdutoPedido() {
        // Arrange
        Long pedidoId = 1L;
        Long produtoId = 1L;
        ProdutoPedido produtoPedido = getProdutoPedido(pedidoId, produtoId, 1, 1.0);

        // Act
        produtoPedidoAdapter.removerProdutoPedido(produtoPedido);

        // Assert
        verify(produtoPedidoRepository).removeByIdPedidoAndIdProduto(pedidoId, produtoId);
    }

    @Test
    void consultarProdutoPedido() {
        // Arrange
        Long pedidoId = 1L;
        Long produtoId = 1L;
        ProdutoPedido produtoPedido = getProdutoPedido(pedidoId, produtoId, 1, 1.0);
        ProdutoPedidoEntity produtoPedidoEntity = getProdutoPedidoEntity(pedidoId, produtoId, 1, 1.0);

        when(produtoPedidoEntityMapper.toProdutoPedidoId(produtoPedido)).thenReturn(produtoPedidoEntity.getId());
        when(produtoPedidoRepository.findById(produtoPedidoEntity.getId())).thenReturn(java.util.Optional.of(produtoPedidoEntity));
        when(produtoPedidoEntityMapper.toProdutoPedido(produtoPedidoEntity)).thenReturn(produtoPedido);

        // Act
        Optional<ProdutoPedido> produtoPedidoOptional = produtoPedidoAdapter.consultarProdutoPedido(produtoPedido);

        // Assert
        assertTrue(produtoPedidoOptional.isPresent());
        assertEquals(produtoPedido.getIdPedido(), produtoPedidoOptional.get().getIdPedido());
        assertEquals(produtoPedido.getIdProduto(), produtoPedidoOptional.get().getIdProduto());
        assertEquals(produtoPedido.getNomeProduto(), produtoPedidoOptional.get().getNomeProduto());
        assertEquals(produtoPedido.getDescricaoProduto(), produtoPedidoOptional.get().getDescricaoProduto());
        assertEquals(produtoPedido.getQuantidade(), produtoPedidoOptional.get().getQuantidade());
        assertEquals(produtoPedido.getValor(), produtoPedidoOptional.get().getValor());
        verify(produtoPedidoRepository).findById(produtoPedidoEntity.getId());
        verify(produtoPedidoEntityMapper).toProdutoPedido(produtoPedidoEntity);
    }

    private ProdutoPedido getProdutoPedido(Long idPedido, Long idProduto, Integer quantidade, Double valor) {
        return new ProdutoPedido(idPedido, idProduto, "Teste", "teste", quantidade, valor);
    }

    private ProdutoPedidoEntity getProdutoPedidoEntity(Long idPedido, Long idProduto, Integer quantidade, Double valor) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        ProdutoEntity produtoEntity = new ProdutoEntity();
        ProdutoPedido produtoPedido = getProdutoPedido(idPedido, idProduto, quantidade, valor);
        ProdutoPedidoIdEntity produtoPedidoIdEntity = toProdutoPedidoId(produtoPedido);

        pedidoEntity.setId(produtoPedidoIdEntity.getIdPedido());
        produtoEntity.setId(produtoPedidoIdEntity.getIdProduto());

        ProdutoPedidoEntity produtoPedidoEntity = new ProdutoPedidoEntity();
        produtoPedidoEntity.setId(produtoPedidoIdEntity);
        produtoPedidoEntity.setPedido(pedidoEntity);
        produtoPedidoEntity.setProduto(produtoEntity);
        produtoPedidoEntity.setQuantidade(quantidade);
        produtoPedidoEntity.setValor(valor);
        return produtoPedidoEntity;
    }

    private ProdutoPedidoIdEntity toProdutoPedidoId(ProdutoPedido produtoPedido) {
        ProdutoPedidoIdEntity produtoPedidoIdEntity = new ProdutoPedidoIdEntity();
        produtoPedidoIdEntity.setIdProduto(produtoPedido.getIdProduto());
        produtoPedidoIdEntity.setIdPedido(produtoPedido.getIdPedido());
        return produtoPedidoIdEntity;
    }
}