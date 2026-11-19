package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testes da classe base Pedido. Como ela é abstrata, usa-se uma subclasse mínima
 * que não aplica desconto nem acréscimo (valor final = subtotal). Os testes de
 * processamento, status, regra do ENTREGUE e exceções vêm de PedidoTestBase.
 */
class PedidoTest extends PedidoTestBase<Pedido> {

    @Override
    protected Pedido criarPedido(String cliente) {
        return new Pedido(1, cliente) {
            @Override
            protected BigDecimal calcularTotal(BigDecimal subtotal) {
                return subtotal;
            }
        };
    }

    @Test
    @DisplayName("valor final da base = subtotal arredondado para 2 casas")
    void valorFinalDaBaseDeveSerOSubtotal() {
        assertEquals(new BigDecimal("120.00"), pedido.calcularValorFinal());
    }

    @Test
    @DisplayName("guarda id, cliente e itens")
    void deveGuardarDados() {
        assertEquals(1, pedido.getId());
        assertEquals("Ana", pedido.getCliente());
        assertEquals(2, pedido.getItens().size());
    }
}
