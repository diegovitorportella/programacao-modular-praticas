package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PedidoEletronicoTest extends PedidoTestBase<PedidoEletronico> {

    @Override
    protected PedidoEletronico criarPedido(String cliente) {
        return new PedidoEletronico(1, cliente);
    }

    @Test
    @DisplayName("valor final = subtotal com 10% de desconto")
    void valorFinalDeveAplicarDesconto() {
        assertEquals(new BigDecimal("108.00"), pedido.calcularValorFinal());
    }

    @Test
    @DisplayName("valor final é arredondado para 2 casas")
    void valorFinalDeveSerArredondado() throws Exception {
        PedidoEletronico p = new PedidoEletronico(2, "Caio");
        p.adicionarItem(new ItemPedido("Item", new BigDecimal("0.55"), 1));
        // 0,55 - 10% = 0,495 -> 0,50
        assertEquals(new BigDecimal("0.50"), p.calcularValorFinal());
    }
}
