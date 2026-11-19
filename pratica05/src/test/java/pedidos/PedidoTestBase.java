package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pedidos.excecoes.PedidoVazioException;
import pedidos.excecoes.StatusInvalidoException;

/**
 * Testes comuns a TODOS os tipos de pedido (processamento, regra do ENTREGUE,
 * exceções). Cada classe de teste concreta só informa como criar o seu pedido,
 * então estes testes rodam automaticamente para cada tipo.
 *
 * Todo pedido de teste começa com subtotal de 120,00 (2 x 50,00 + 1 x 20,00).
 */
abstract class PedidoTestBase<T extends Pedido> {

    protected T pedido;

    protected abstract T criarPedido(String cliente);

    /** Instancia o pedido e inicializa os itens antes de cada teste. */
    @BeforeEach
    void inicializar() throws Exception {
        pedido = criarPedido("Ana");
        pedido.adicionarItem(new ItemPedido("Produto A", new BigDecimal("50.00"), 2));
        pedido.adicionarItem(new ItemPedido("Produto B", new BigDecimal("20.00"), 1));
    }

    /** Informa que o teste foi finalizado. */
    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[" + getClass().getSimpleName() + "] teste finalizado: "
                + info.getDisplayName());
    }

    // ---------- valor final ----------

    @Test
    @DisplayName("subtotal soma os itens")
    void subtotalDeveSomarItens() {
        assertEquals(new BigDecimal("120.00"), pedido.getSubtotal());
    }

    @Test
    @DisplayName("valor final de pedido vazio considera só a regra do tipo")
    void valorFinalDePedidoVazioNaoDeveFalhar() {
        T vazio = criarPedido("Bia");
        assertEquals(0, vazio.getSubtotal().signum());
        assertTrue(vazio.calcularValorFinal().signum() >= 0);
    }

    // ---------- processamento e status ----------

    @Test
    @DisplayName("pedido novo começa como CRIADO")
    void pedidoNovoDeveEstarCriado() {
        assertEquals(StatusPedido.CRIADO, pedido.getStatus());
    }

    @Test
    @DisplayName("processar percorre todo o fluxo até ENTREGUE")
    void processarDeveAvancarStatusAteEntregue() throws Exception {
        StatusPedido[] esperado = {
            StatusPedido.PAGO, StatusPedido.EM_PREPARACAO,
            StatusPedido.ENVIADO, StatusPedido.ENTREGUE
        };
        for (StatusPedido proximo : esperado) {
            pedido.processar();
            assertEquals(proximo, pedido.getStatus());
        }
    }

    @Test
    @DisplayName("alterarStatus permite cancelar um pedido em andamento")
    void deveCancelarPedidoEmAndamento() throws Exception {
        pedido.processar();
        pedido.alterarStatus(StatusPedido.CANCELADO);
        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    @Test
    @DisplayName("alterarStatus não permite pular etapas")
    void naoDevePularEtapas() {
        StatusInvalidoException e = assertThrows(StatusInvalidoException.class,
                () -> pedido.alterarStatus(StatusPedido.ENVIADO));
        assertEquals(StatusPedido.CRIADO, e.getAtual());
        assertEquals(StatusPedido.ENVIADO, e.getDestino());
        assertEquals(StatusPedido.CRIADO, pedido.getStatus());
    }

    // ---------- regra do enum: ENTREGUE não pode ser alterado ----------

    private void entregar() throws Exception {
        while (pedido.getStatus() != StatusPedido.ENTREGUE) {
            pedido.processar();
        }
    }

    @ParameterizedTest(name = "pedido ENTREGUE não pode virar {0}")
    @EnumSource(StatusPedido.class)
    void pedidoEntregueNaoPodeSerAlterado(StatusPedido destino) throws Exception {
        entregar();
        assertThrows(StatusInvalidoException.class, () -> pedido.alterarStatus(destino));
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    @DisplayName("pedido ENTREGUE não pode ser processado de novo")
    void pedidoEntregueNaoPodeSerProcessado() throws Exception {
        entregar();
        StatusInvalidoException e = assertThrows(StatusInvalidoException.class,
                () -> pedido.processar());
        assertEquals(StatusPedido.ENTREGUE, e.getAtual());
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    @DisplayName("pedido ENTREGUE não aceita novos itens")
    void pedidoEntregueNaoAceitaItens() throws Exception {
        entregar();
        int antes = pedido.getItens().size();
        assertThrows(StatusInvalidoException.class,
                () -> pedido.adicionarItem(new ItemPedido("Extra", BigDecimal.TEN, 1)));
        assertEquals(antes, pedido.getItens().size());
    }

    // ---------- exceções personalizadas ----------

    @Test
    @DisplayName("processar pedido sem itens lança PedidoVazioException")
    void processarPedidoVazioDeveLancarExcecao() {
        T vazio = criarPedido("Bia");
        PedidoVazioException e = assertThrows(PedidoVazioException.class, vazio::processar);
        assertTrue(e.getMessage().contains(String.valueOf(vazio.getId())));
        assertEquals(StatusPedido.CRIADO, vazio.getStatus());
    }

    // ---------- exceções do Java ----------

    @Test
    @DisplayName("cliente nulo lança NullPointerException")
    void clienteNuloDeveLancarNullPointer() {
        assertThrows(NullPointerException.class, () -> criarPedido(null));
    }

    @Test
    @DisplayName("cliente em branco lança IllegalArgumentException")
    void clienteEmBrancoDeveLancarIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> criarPedido("  "));
    }

    @Test
    @DisplayName("adicionar item nulo lança NullPointerException")
    void itemNuloDeveLancarNullPointer() {
        assertThrows(NullPointerException.class, () -> pedido.adicionarItem(null));
    }

    @Test
    @DisplayName("lista de itens é somente leitura (UnsupportedOperationException)")
    void listaDeItensNaoPodeSerModificadaPorFora() {
        assertThrows(UnsupportedOperationException.class, () -> pedido.getItens().clear());
    }
}
