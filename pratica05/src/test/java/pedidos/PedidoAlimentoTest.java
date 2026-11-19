package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pedidos.excecoes.ValorInvalidoException;

class PedidoAlimentoTest extends PedidoTestBase<PedidoAlimento> {

    @Override
    protected PedidoAlimento criarPedido(int id, String cliente, double valor)
            throws ValorInvalidoException {
        return new PedidoAlimento(id, cliente, valor);
    }

    @Override
    protected Categoria categoriaEsperada() {
        return Categoria.ALIMENTO;
    }

    @ParameterizedTest(name = "valor {0} -> final igual ao valor")
    @ValueSource(doubles = {1, 100, 100.01, 200.01, 5000})
    @DisplayName("nunca tem desconto")
    void valorFinalNaoTemDesconto(double valor) throws Exception {
        assertEquals(valor, criarPedido(2, "Bia", valor).calcularValorFinal());
    }

    @Test
    @DisplayName("não é Entregavel")
    void naoDeveSerEntregavel() {
        assertFalse(pedido instanceof Entregavel);
    }

    @Test
    @DisplayName("taxa fixa é R$ 5")
    void taxaFixa() {
        assertEquals(5.0, pedido.getTaxaFixa());
    }
}
