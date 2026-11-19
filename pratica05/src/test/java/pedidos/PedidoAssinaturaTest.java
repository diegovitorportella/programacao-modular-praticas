package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PedidoAssinaturaTest extends PedidoTestBase<PedidoAssinatura> {

    @Override
    protected PedidoAssinatura criarPedido(String cliente) {
        return new PedidoAssinatura(1, cliente, 6);
    }

    @Test
    @DisplayName("menos de 12 meses: sem desconto (120 x 6 = 720)")
    void semDescontoAbaixoDeDozeMeses() {
        assertEquals(new BigDecimal("720.00"), pedido.calcularValorFinal());
    }

    @ParameterizedTest(name = "{0} meses -> {1}")
    @CsvSource({"1, 120.00", "11, 1320.00", "12, 1152.00", "24, 2304.00"})
    @DisplayName("desconto de 20% só a partir de 12 meses")
    void descontoAnual(int meses, String esperado) throws Exception {
        PedidoAssinatura p = new PedidoAssinatura(2, "Caio", meses);
        p.adicionarItem(new ItemPedido("Plano", new BigDecimal("120.00"), 1));
        assertEquals(new BigDecimal(esperado), p.calcularValorFinal());
    }

    @ParameterizedTest(name = "meses = {0} lança IllegalArgumentException")
    @ValueSource(ints = {0, -1, -12})
    void mesesInvalidosDevemLancarExcecao(int meses) {
        assertThrows(IllegalArgumentException.class,
                () -> new PedidoAssinatura(3, "Dani", meses));
    }
}
