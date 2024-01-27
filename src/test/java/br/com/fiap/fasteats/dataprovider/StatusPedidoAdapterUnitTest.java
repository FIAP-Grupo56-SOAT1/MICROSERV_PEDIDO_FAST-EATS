package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.domain.model.StatusPedido;
import br.com.fiap.fasteats.dataprovider.repository.StatusPedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.StatusPedidoEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.StatusPedidoEntityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StatusPedidoAdapterUnitTest {
    @Mock
    private StatusPedidoRepository statusPedidoRepository;
    @Mock
    private StatusPedidoEntityMapper statusPedidoEntityMapper;
    @InjectMocks
    private StatusPedidoAdapter statusPedidoAdapter;
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
    void criar() {
        // Arrange
        StatusPedido statusPedido = getStatusPedido(1L, "nome");
        StatusPedidoEntity statusPedidoEntity = getStatusPedidoEntity(1L, "nome");

        when(statusPedidoEntityMapper.toStatusPedidoEntity(statusPedido)).thenReturn(statusPedidoEntity);
        when(statusPedidoRepository.save(statusPedidoEntity)).thenReturn(statusPedidoEntity);
        when(statusPedidoEntityMapper.toStatusPedido(statusPedidoEntity)).thenReturn(statusPedido);

        // Act
        StatusPedido statusPedidoCriado = statusPedidoAdapter.criar(statusPedido);

        // Assert
        assertEquals(statusPedido.getId(), statusPedidoCriado.getId());
        assertEquals(statusPedido.getNome(), statusPedidoCriado.getNome());
        verify(statusPedidoRepository).save(statusPedidoEntity);
        verify(statusPedidoEntityMapper).toStatusPedidoEntity(statusPedido);
        verify(statusPedidoEntityMapper).toStatusPedido(statusPedidoEntity);
    }

    @Test
    void consultar() {
        // Arrange
        StatusPedido statusPedido = getStatusPedido(1L, "nome");
        StatusPedidoEntity statusPedidoEntity = getStatusPedidoEntity(1L, "nome");

        when(statusPedidoRepository.findById(1L)).thenReturn(java.util.Optional.of(statusPedidoEntity));
        when(statusPedidoEntityMapper.toStatusPedido(statusPedidoEntity)).thenReturn(statusPedido);

        // Act
        Optional<StatusPedido> statusPedidoConsultado = statusPedidoAdapter.consultar(1L);

        // Assert
        assertTrue(statusPedidoConsultado.isPresent());
        assertEquals(statusPedido.getId(), statusPedidoConsultado.get().getId());
        assertEquals(statusPedido.getNome(), statusPedidoConsultado.get().getNome());
        verify(statusPedidoRepository).findById(1L);
        verify(statusPedidoEntityMapper).toStatusPedido(statusPedidoEntity);
    }

    @Test
    void atualizar() {
        // Arrange
        StatusPedido statusPedido = getStatusPedido(1L, "nome");
        StatusPedidoEntity statusPedidoEntity = getStatusPedidoEntity(1L, "nome");

        when(statusPedidoEntityMapper.toStatusPedidoEntity(statusPedido)).thenReturn(statusPedidoEntity);
        when(statusPedidoRepository.save(statusPedidoEntity)).thenReturn(statusPedidoEntity);
        when(statusPedidoEntityMapper.toStatusPedido(statusPedidoEntity)).thenReturn(statusPedido);

        // Act
        StatusPedido statusPedidoCriado = statusPedidoAdapter.atualizar(statusPedido);

        // Assert
        assertEquals(statusPedido.getId(), statusPedidoCriado.getId());
        assertEquals(statusPedido.getNome(), statusPedidoCriado.getNome());
        verify(statusPedidoRepository).save(statusPedidoEntity);
        verify(statusPedidoEntityMapper).toStatusPedidoEntity(statusPedido);
        verify(statusPedidoEntityMapper).toStatusPedido(statusPedidoEntity);
    }

    @Test
    void deletar() {
        // Arrange
        Long statusPedidoId = 1L;

        // Act
        statusPedidoAdapter.deletar(statusPedidoId);

        // Assert
        verify(statusPedidoRepository).deleteById(statusPedidoId);
    }

    @Test
    void listar() {
        // Arrange
        List<StatusPedido> statusPedidoList = List.of(getStatusPedido(1L, "nome"));
        List<StatusPedidoEntity> statusPedidoEntityList = List.of(getStatusPedidoEntity(1L, "nome"));

        when(statusPedidoRepository.findAll()).thenReturn(statusPedidoEntityList);
        when(statusPedidoEntityMapper.toStatusPedido(statusPedidoEntityList.get(0))).thenReturn(statusPedidoList.get(0));

        // Act
        Optional<List<StatusPedido>> statusPedidoListConsultado = statusPedidoAdapter.listar();

        // Assert
        assertTrue(statusPedidoListConsultado.isPresent());
        assertEquals(statusPedidoList.get(0).getId(), statusPedidoListConsultado.get().get(0).getId());
        verify(statusPedidoRepository).findAll();
        verify(statusPedidoEntityMapper).toStatusPedido(statusPedidoEntityList.get(0));
    }

    @Test
    void consultarPorNome() {
        // Arrange
        String statusPedidoNome = "TESTE";
        StatusPedido statusPedido = getStatusPedido(1L, statusPedidoNome);
        StatusPedidoEntity statusPedidoEntity = getStatusPedidoEntity(1L, statusPedidoNome);

        when(statusPedidoRepository.findByNome(statusPedidoNome)).thenReturn(List.of(statusPedidoEntity));
        when(statusPedidoEntityMapper.toStatusPedido(statusPedidoEntity)).thenReturn(statusPedido);

        // Act
        Optional<StatusPedido> statusPedidoConsultado = statusPedidoAdapter.consultarPorNome(statusPedidoNome);

        // Assert
        assertTrue(statusPedidoConsultado.isPresent());
        assertEquals(statusPedido, toStatusPedido(statusPedidoEntity));
        assertEquals(statusPedido.getId(), statusPedidoConsultado.get().getId());
        assertEquals(statusPedido.getNome(), statusPedidoConsultado.get().getNome());
        verify(statusPedidoRepository).findByNome(statusPedidoNome);
        verify(statusPedidoEntityMapper).toStatusPedido(statusPedidoEntity);
    }

    private StatusPedido getStatusPedido(Long id, String nome) {
        return new StatusPedido(id, nome, true);
    }

    private StatusPedidoEntity getStatusPedidoEntity(Long id, String nome) {
        StatusPedidoEntity statusPedidoEntity = new StatusPedidoEntity();
        statusPedidoEntity.setId(id);
        statusPedidoEntity.setNome(nome);
        statusPedidoEntity.setAtivo(true);
        return statusPedidoEntity;
    }

    private StatusPedido toStatusPedido(StatusPedidoEntity statusPedidoEntity) {
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setId(statusPedidoEntity.getId());
        statusPedido.setNome(statusPedidoEntity.getNome());
        statusPedido.setAtivo(statusPedidoEntity.getAtivo());
        return statusPedido;
    }
}