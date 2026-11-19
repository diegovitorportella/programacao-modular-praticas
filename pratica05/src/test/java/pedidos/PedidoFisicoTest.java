package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PedidoFisicoTest extends PedidoTestBase<PedidoFisico> {

    private static final BigDecimal FRETE = new BigDecimal("15.00");

    @Override
    protected PedidoFisico criarPedido(String cliente) {
        return new PedidoFisico(1, cliente, FRETE);
    }

    @Test
    @DisplayName("valor final = subtotal + frete")
    void valorFinalDeveSomarFrete() {
        assertEquals(new BigDecimal("135.00"), pedido.calcularValorFinal());
    }

    @Test
    @DisplayName("frete zero não altera o subtotal")
    void freteZeroNaoAlteraSubtotal() {
        PedidoFisico semFrete = new PedidoFisico(2, "Caio", BigDecimal.ZERO);
        assertEquals(new BigDecimal("0.00"), semFrete.calcularValorFinal());
    }

    @Test
    @DisplayName("frete negativo lança IllegalArgumentException")
    void freteNegativoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new PedidoFisico(3, "Dani", new BigDecimal("-1")));
    }

    @Test
    @DisplayName("frete nulo lança IllegalArgumentException")
    void freteNuloDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new PedidoFisico(3, "Dani", null));
    }
}
