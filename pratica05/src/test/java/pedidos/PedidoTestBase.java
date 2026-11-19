package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pedidos.excecoes.PedidoInvalidoException;
import pedidos.excecoes.ValorInvalidoException;

/**
 * Testes comuns a TODOS os tipos de pedido: validação do construtor, processamento,
 * mudança de status, regra do ENTREGUE e exceções. As classes concretas só informam
 * como criar o seu pedido e a categoria esperada, então tudo roda para cada tipo.
 */
abstract class PedidoTestBase<T extends Pedido> {

    protected static final double VALOR_PADRAO = 150.0;

    protected T pedido;

    protected abstract T criarPedido(int id, String cliente, double valor) throws ValorInvalidoException;

    protected abstract Categoria categoriaEsperada();

    /** Instancia o pedido e inicializa os valores antes de cada teste. */
    @BeforeEach
    void inicializar() throws Exception {
        pedido = criarPedido(1, "Ana", VALOR_PADRAO);
    }

    /** Informa que o teste foi finalizado. */
    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[" + getClass().getSimpleName() + "] teste finalizado: "
                + info.getDisplayName());
    }

    /** Leva o pedido até ENTREGUE pelo fluxo normal. */
    private void entregar() throws PedidoInvalidoException {
        while (pedido.getStatus() != StatusPedido.ENTREGUE) {
            pedido.processarPedido();
        }
    }

    // ---------- construção ----------

    @Test
    @DisplayName("pedido novo guarda os dados e começa PENDENTE")
    void pedidoNovoDeveGuardarDadosEEstarPendente() {
        assertEquals(1, pedido.getId());
        assertEquals("Ana", pedido.getNomeCliente());
        assertEquals(VALOR_PADRAO, pedido.getValor());
        assertEquals(StatusPedido.PENDENTE, pedido.getStatus());
        assertEquals(categoriaEsperada(), pedido.getCategoria());
        assertFalse(pedido.isCancelado());
    }

    // ---------- exceção personalizada: ValorInvalidoException ----------

    @ParameterizedTest(name = "valor {0} lança ValorInvalidoException")
    @ValueSource(doubles = {0, -0.01, -100, Double.NaN})
    void valorMenorOuIgualAZeroDeveLancarExcecao(double valor) {
        ValorInvalidoException e = assertThrows(ValorInvalidoException.class,
                () -> criarPedido(2, "Bia", valor));
        assertTrue(e.getMessage().contains("maior que zero"));
    }

    @Test
    @DisplayName("menor valor positivo é aceito")
    void valorMinimoPositivoDeveSerAceito() throws Exception {
        assertEquals(0.01, criarPedido(2, "Bia", 0.01).getValor());
    }

    // ---------- exceções do Java ----------

    @Test
    @DisplayName("cliente nulo lança IllegalArgumentException")
    void clienteNuloDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> criarPedido(2, null, 10));
    }

    @Test
    @DisplayName("cliente em branco lança IllegalArgumentException")
    void clienteEmBrancoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> criarPedido(2, "  ", 10));
    }

    @Test
    @DisplayName("alterar para status nulo lança NullPointerException")
    void statusNuloDeveLancarNullPointer() {
        assertThrows(NullPointerException.class, () -> pedido.alterarStatus(null));
    }

    // ---------- processamento: alteração do status ----------

    @Test
    @DisplayName("processar percorre PENDENTE → PROCESSANDO → ENVIADO → ENTREGUE")
    void processarDeveAvancarStatus() throws Exception {
        StatusPedido[] esperado = {
            StatusPedido.PROCESSANDO, StatusPedido.ENVIADO, StatusPedido.ENTREGUE
        };
        for (StatusPedido proximo : esperado) {
            pedido.processarPedido();
            assertEquals(proximo, pedido.getStatus());
        }
    }

    @ParameterizedTest(name = "alterarStatus({0}) em pedido PENDENTE")
    @EnumSource(StatusPedido.class)
    void alterarStatusDevePermitirQualquerStatusEmPedidoNaoEntregue(StatusPedido novo)
            throws Exception {
        pedido.alterarStatus(novo);
        assertEquals(novo, pedido.getStatus());
    }

    // ---------- exceção personalizada: PedidoInvalidoException ----------

    @Test
    @DisplayName("processar pedido cancelado lança PedidoInvalidoException")
    void processarCanceladoDeveLancarExcecao() throws Exception {
        pedido.alterarStatus(StatusPedido.CANCELADO);
        PedidoInvalidoException e = assertThrows(PedidoInvalidoException.class,
                () -> pedido.processarPedido());
        assertTrue(e.getMessage().contains("cancelado"));
        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
        assertTrue(pedido.isCancelado());
    }

    // ---------- regra do enum: ENTREGUE não pode ser alterado ----------

    @ParameterizedTest(name = "pedido ENTREGUE não pode virar {0}")
    @EnumSource(StatusPedido.class)
    void pedidoEntregueNaoPodeSerAlterado(StatusPedido destino) throws Exception {
        entregar();
        PedidoInvalidoException e = assertThrows(PedidoInvalidoException.class,
                () -> pedido.alterarStatus(destino));
        assertTrue(e.getMessage().contains("entregue"));
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    @DisplayName("pedido ENTREGUE não pode ser processado de novo")
    void pedidoEntregueNaoPodeSerProcessado() throws Exception {
        entregar();
        assertThrows(PedidoInvalidoException.class, () -> pedido.processarPedido());
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    @DisplayName("pedido cancelado pode ser reaberto manualmente (só ENTREGUE é final)")
    void pedidoCanceladoPodeSerAlterado() throws Exception {
        pedido.alterarStatus(StatusPedido.CANCELADO);
        pedido.alterarStatus(StatusPedido.PENDENTE);
        assertEquals(StatusPedido.PENDENTE, pedido.getStatus());
    }

    // ---------- apresentação ----------

    @Test
    @DisplayName("toString traz id, cliente, categoria e status")
    void toStringDeveTrazerDadosPrincipais() {
        String texto = pedido.toString();
        assertTrue(texto.contains("#1"));
        assertTrue(texto.contains("Ana"));
        assertTrue(texto.contains(categoriaEsperada().name()));
        assertTrue(texto.contains("PENDENTE"));
    }
}
