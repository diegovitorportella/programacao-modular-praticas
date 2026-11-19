package pedidos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItemPedidoTest {

    private ItemPedido item;

    @BeforeEach
    void inicializar() {
        item = new ItemPedido("Caneta", new BigDecimal("2.50"), 4);
    }

    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[ItemPedidoTest] teste finalizado: " + info.getDisplayName());
    }

    @Test
    @DisplayName("guarda os dados informados")
    void deveGuardarDados() {
        assertAll(
                () -> assertEquals("Caneta", item.getProduto()),
                () -> assertEquals(new BigDecimal("2.50"), item.getPrecoUnitario()),
                () -> assertEquals(4, item.getQuantidade()));
    }

    @Test
    @DisplayName("subtotal = preço x quantidade")
    void subtotalDeveMultiplicar() {
        assertEquals(new BigDecimal("10.00"), item.getSubtotal());
    }

    @Test
    @DisplayName("preço zero é permitido")
    void precoZeroEPermitido() {
        assertEquals(0, new ItemPedido("Brinde", BigDecimal.ZERO, 1).getSubtotal().signum());
    }

    @ParameterizedTest(name = "quantidade {0} lança IllegalArgumentException")
    @ValueSource(ints = {0, -1})
    void quantidadeInvalidaDeveLancarExcecao(int quantidade) {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("X", BigDecimal.ONE, quantidade));
    }

    @Test
    @DisplayName("preço negativo lança IllegalArgumentException")
    void precoNegativoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido("X", new BigDecimal("-0.01"), 1));
    }

    @Test
    @DisplayName("produto em branco lança IllegalArgumentException")
    void produtoEmBrancoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new ItemPedido(" ", BigDecimal.ONE, 1));
    }

    @Test
    @DisplayName("produto ou preço nulos lançam NullPointerException")
    void nulosDevemLancarNullPointer() {
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> new ItemPedido(null, BigDecimal.ONE, 1)),
                () -> assertThrows(NullPointerException.class,
                        () -> new ItemPedido("X", null, 1)));
    }
}
