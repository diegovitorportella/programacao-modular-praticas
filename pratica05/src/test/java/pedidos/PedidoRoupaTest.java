package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pedidos.excecoes.ValorInvalidoException;

class PedidoRoupaTest extends PedidoTestBase<PedidoRoupa> {

    @Override
    protected PedidoRoupa criarPedido(int id, String cliente, double valor)
            throws ValorInvalidoException {
        return new PedidoRoupa(id, cliente, valor);
    }

    @Override
    protected Categoria categoriaEsperada() {
        return Categoria.ROUPA;
    }

    @ParameterizedTest(name = "valor {0} -> final {1}")
    @CsvSource({
        "100, 100.0",
        "200, 200.0",       // exatamente no limite: sem desconto
        "200.01, 160.008",  // acima do limite: 20% de desconto
        "300, 240.0"
    })
    @DisplayName("desconto de 20% só se valor > 200")
    void valorFinal(double valor, double esperado) throws Exception {
        assertEquals(esperado, criarPedido(2, "Bia", valor).calcularValorFinal(), 1e-9);
    }

    @ParameterizedTest(name = "valor {0} -> frete {1}")
    @CsvSource({"100, 15.0", "200, 20.0", "300, 25.0"})
    @DisplayName("frete = R$ 10 + 5% do valor")
    void frete(double valor, double esperado) throws Exception {
        assertEquals(esperado, criarPedido(2, "Bia", valor).calcularFrete(), 1e-9);
    }

    @Test
    @DisplayName("é Entregavel")
    void deveSerEntregavel() {
        assertInstanceOf(Entregavel.class, pedido);
    }
}
