package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pedidos.excecoes.ValorInvalidoException;

class PedidoEletronicoTest extends PedidoTestBase<PedidoEletronico> {

    @Override
    protected PedidoEletronico criarPedido(int id, String cliente, double valor)
            throws ValorInvalidoException {
        return new PedidoEletronico(id, cliente, valor);
    }

    @Override
    protected Categoria categoriaEsperada() {
        return Categoria.ELETRONICO;
    }

    @ParameterizedTest(name = "valor {0} -> final {1}")
    @CsvSource({
        "50, 50.0",         // abaixo do limite
        "100, 100.0",       // exatamente no limite: sem desconto
        "100.01, 90.009",   // acima do limite: 10% de desconto
        "150, 135.0",
        "1000, 900.0"
    })
    @DisplayName("desconto de 10% só se valor > 100")
    void valorFinal(double valor, double esperado) throws Exception {
        assertEquals(esperado, criarPedido(2, "Bia", valor).calcularValorFinal(), 1e-9);
    }

    @ParameterizedTest(name = "valor {0} -> frete {1}")
    @CsvSource({"100, 30.0", "150, 35.0", "10, 21.0"})
    @DisplayName("frete = R$ 20 + 10% do valor")
    void frete(double valor, double esperado) throws Exception {
        assertEquals(esperado, criarPedido(2, "Bia", valor).calcularFrete(), 1e-9);
    }

    @Test
    @DisplayName("é Entregavel")
    void deveSerEntregavel() {
        assertInstanceOf(Entregavel.class, pedido);
    }
}
