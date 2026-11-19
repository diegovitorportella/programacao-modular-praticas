package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedidos.excecoes.ValorInvalidoException;

/**
 * Testes da classe base Pedido. Por ser abstrata, usa uma subclasse mínima sem desconto
 * nem frete. Os testes de construção, processamento, status e exceções vêm de PedidoTestBase.
 */
class PedidoTest extends PedidoTestBase<Pedido> {

    @Override
    protected Pedido criarPedido(int id, String cliente, double valor) throws ValorInvalidoException {
        return new Pedido(id, cliente, valor, Categoria.ALIMENTO) {
            @Override
            public double calcularValorFinal() {
                return getValor();
            }
        };
    }

    @Override
    protected Categoria categoriaEsperada() {
        return Categoria.ALIMENTO;
    }

    @Test
    @DisplayName("implementa Processavel")
    void deveSerProcessavel() {
        assertEquals(VALOR_PADRAO, ((Processavel) pedido).calcularValorFinal());
    }
}
