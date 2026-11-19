package pedidos;

import java.math.BigDecimal;

/** Pedido de produto digital: sem frete e com 10% de desconto. */
public class PedidoEletronico extends Pedido {
    public static final BigDecimal DESCONTO = new BigDecimal("0.10");

    public PedidoEletronico(int id, String cliente) {
        super(id, cliente);
    }

    @Override
    protected BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.subtract(subtotal.multiply(DESCONTO));
    }
}
