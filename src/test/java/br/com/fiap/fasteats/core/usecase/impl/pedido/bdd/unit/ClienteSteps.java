package br.com.fiap.fasteats.core.usecase.impl.pedido.bdd.unit;

import br.com.fiap.fasteats.core.dataprovider.ClienteOutputPort;
import br.com.fiap.fasteats.core.dataprovider.ProdutoOutputPort;
import br.com.fiap.fasteats.core.domain.exception.ClienteNotFound;
import br.com.fiap.fasteats.core.domain.exception.RegraNegocioException;
import br.com.fiap.fasteats.core.domain.exception.ValidarClienteException;
import br.com.fiap.fasteats.core.domain.model.Cliente;
import br.com.fiap.fasteats.core.usecase.impl.ClienteUseCase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteSteps {
    @Mock
    private ClienteOutputPort clienteOutputPort;
    @InjectMocks
    private ClienteUseCase clienteUseCase;

    @Mock
    private ProdutoOutputPort produtoOutputPort;

    private String CPF_VALIDO = "84000655493";
    private final String CPF_VALIDO_2 = "26367999078";
    private String CPF_INVALIDO = "817334340";

    private String NOME_CLIENTE = "Fiap";

    AutoCloseable openMocks;

    private Cliente cliente;

    private Cliente resultado;
    private List<Cliente> clientes;
    private List<Cliente> clientesResultado;
    private Boolean resultadoClienteExiste;

    @Before
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Dado("que um novo cliente {string} com CPF valido deve ser criado")
    public void que_um_novo_cliente_com_cpf_valido_deve_ser_criado(String nomeCliente) {
        NOME_CLIENTE = nomeCliente;
        cliente = getCliente(CPF_VALIDO, nomeCliente);
    }

    @Quando("eu chamar o método de criação de cliente")
    public void eu_chamar_o_método_de_criação_de_cliente() {
        when(clienteOutputPort.salvarCliente(cliente)).thenReturn(cliente);
        resultado = clienteUseCase.criar(cliente);
    }

    @Entao("o novo cliente deve criado")
    public void o_novo_cliente_deve_criado() {
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).salvarCliente(cliente);
    }

    @Dado("que um novo cliente {string} com CPF valido ja cadastrado no sistema")
    public void que_um_novo_cliente_com_cpf_valido_ja_cadastrado_no_sistema(String nomeCliente) {
        NOME_CLIENTE = nomeCliente;
        cliente = getCliente(CPF_VALIDO, nomeCliente);
    }

    @Quando("eu chamar o método de criação de cliente que ja existe")
    public void eu_chamar_o_método_de_criação_de_cliente_que_ja_existe() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));
        assertThrows(RegraNegocioException.class, () -> clienteUseCase.criar(cliente));
    }

    @Entao("devo receber uma exception RegraNegocioException para o cliente existente")
    public void devo_receber_uma_exception_cliente_not_found_para_o_cliente_existente() {
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que um cliente com CPF valido existe no sistema")
    public void que_um_cliente_com_cpf_valido_existe_no_sistema() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }
    @Quando("eu consultar o cliente pelo CPF")
    public void eu_consultar_o_cliente_pelo_cpf() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));

        resultado = clienteUseCase.consultar(CPF_VALIDO);
    }
    @Entao("devo receber o cliente esperado")
    public void devo_receber_o_cliente_esperado() {
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que eu tenho CPF invalido para consulta do cliente")
    public void que_eu_tenho_cpf_invalido_para_consulta_do_cliente() {
        cliente = getCliente(CPF_INVALIDO, NOME_CLIENTE);
    }
    @Quando("eu consultar o cliente pelo CPF com CPF invalido")
    public void eu_consultar_o_cliente_pelo_cpf_com_cpf_invalido() {
        assertThrows(RegraNegocioException.class, () -> clienteUseCase.consultar(CPF_INVALIDO));
    }
    @Entao("devo receber uma exception RegraNegocioException com CPF invalido")
    public void devo_receber_uma_exception_regra_negocio_exception_com_cpf_invalido() {
        verify(clienteOutputPort, times(0)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que um cliente com CPF valido que nao existe no sistema")
    public void que_um_cliente_com_cpf_valido_que_nao_existe_no_sistema() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }
    @Quando("eu consultar o cliente pelo CPF inexistente no sistema")
    public void eu_consultar_o_cliente_pelo_cpf_inexistente_no_sistema() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.empty());

    }
    @Entao("devo receber uma exception ClienteNotFound para o cliente inexistente")
    public void devo_receber_uma_exception_cliente_not_found_para_o_cliente_inexistente() {
        assertThrows(ClienteNotFound.class, () -> clienteUseCase.consultar(CPF_VALIDO));
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que existem clientes cadastrados")
    public void que_existem_clientes_cadastrados() {
        clientes = List.of(
        getCliente(CPF_VALIDO,NOME_CLIENTE),
        getCliente(CPF_VALIDO_2,NOME_CLIENTE+"SOAT")
        );
    }
    @Quando("eu listar todos as clientes")
    public void eu_listar_todos_as_clientes() {
        when(clienteOutputPort.listar()).thenReturn(Optional.of(clientes));
        clientesResultado = clienteUseCase.listar();
    }
    @Entao("devo receber a lista de clientes")
    public void devo_receber_a_lista_de_clientes() {
        assertNotNull(clientesResultado);
        assertEquals(clientes.size(), clientesResultado.size());
        assertEquals(clientes, clientesResultado);
        verify(clienteOutputPort, times(1)).listar();
    }

    @Dado("que um cliente existe no sistema")
    public void que_um_cliente_existe_no_sistema() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }
    @Quando("eu atualizar o cliente")
    public void eu_atualizar_o_cliente() {
        when(clienteOutputPort.salvarCliente(cliente)).thenReturn(cliente);
        resultado = clienteUseCase.atualizar(cliente);
    }
    @Entao("a cliente deve ser atualizado no sistema")
    public void a_cliente_deve_ser_atualizado_no_sistema() {
        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteOutputPort, times(1)).salvarCliente(cliente);
    }

    @Dado("que um cliente com existe no sistema")
    public void que_um_cliente_com_existe_no_sistema() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }
    @Quando("eu deletar a cliente")
    public void eu_deletar_a_cliente() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));
        clienteUseCase.deletar(CPF_VALIDO);
    }
    @Entao("a cliente deve ser removido")
    public void a_cliente_deve_ser_removido() {
        verify(clienteOutputPort, times(1)).deletar(CPF_VALIDO);
    }

    @Quando("eu consultar se existe com CPF valido no sistema")
    public void eu_consultar_se_existe_com_cpf_valido_no_sistema() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.of(cliente));
        resultadoClienteExiste = clienteUseCase.clienteExiste(CPF_VALIDO);
    }
    @Entao("devo receber um resposta que existe")
    public void devo_receber_um_resposta_que_existe() {
        assertTrue(resultadoClienteExiste);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que um cliente com CPF valido nao existe no sistema")
    public void que_um_cliente_com_cpf_valido_nao_existe_no_sistema() {
        when(clienteOutputPort.consultarCliente(CPF_VALIDO)).thenReturn(Optional.empty());
    }
    @Quando("eu consultar se existe com CPF no sistema")
    public void eu_consultar_se_existe_com_cpf_no_sistema() {
        resultadoClienteExiste = clienteUseCase.clienteExiste(CPF_VALIDO);
    }
    @Entao("devo receber um resposta que nao existe")
    public void devo_receber_um_resposta_que_nao_existe() {
        assertFalse(resultadoClienteExiste);
        verify(clienteOutputPort, times(1)).consultarCliente(CPF_VALIDO);
    }

    @Dado("que um CPF vazio de um cliente")
    public void que_um_cpf_vazio_de_um_cliente() {
        CPF_VALIDO = "";

    }
    @Quando("eu consultar se existe com CPF vazio no sistema")
    public void eu_consultar_se_existe_com_cpf_vazio_no_sistema() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }
    @Entao("devo receber uma exception IllegalArgumentException para a consulta")
    public void devo_receber_uma_exception_illegal_argument_exception_para_a_consulta() {
        assertThrows(IllegalArgumentException.class, () -> clienteUseCase.validarCliente(cliente));
    }

    @Dado("que um CPF invalido de um cliente")
    public void que_um_cpf_invalido_de_um_cliente() {
        CPF_INVALIDO = "2923265845";
    }
    @Quando("eu consultar se existe com CPF invalido no sistema")
    public void eu_consultar_se_existe_com_cpf_invalido_no_sistema() {
        cliente = getCliente(CPF_INVALIDO, NOME_CLIENTE);
    }
    @Entao("devo receber uma exception RegraNegocioException para a consulta")
    public void devo_receber_uma_exception_regra_negocio_exception_para_a_consulta() {
        assertThrows(RegraNegocioException.class, () -> clienteUseCase.validarCliente(cliente));
    }

    @Dado("que um cliente com cpf valido")
    public void que_um_cliente_com_cpf_valido() {
        cliente = getCliente(CPF_VALIDO, NOME_CLIENTE);
    }

    @Quando("eu consultar se e valido com email vazio")
    public void eu_consultar_se_e_valido_com_email_vazio() {
        cliente.setEmail("@email.com");
    }

    @Entao("devo receber uma exception ValidarClienteException para a consulta")
    public void devo_receber_uma_exception_validar_cliente_exception_para_a_consulta() {
        assertThrows(ValidarClienteException.class, () -> clienteUseCase.validarCliente(cliente));
    }

    private Cliente getCliente(String cpf, String nome) {

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setEmail("aluno@fiap.com");
        cliente.setPrimeiroNome(nome);
        cliente.setUltimoNome("Soat1");
        cliente.setAtivo(true);
        return cliente;
    }

}
